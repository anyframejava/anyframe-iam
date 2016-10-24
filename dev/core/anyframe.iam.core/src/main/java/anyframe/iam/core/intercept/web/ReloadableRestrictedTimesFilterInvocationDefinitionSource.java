/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package anyframe.iam.core.intercept.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.SecurityConfig;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;

import anyframe.common.exception.BaseException;
import anyframe.common.util.DateUtil;
import anyframe.iam.core.securedobject.ISecuredObjectService;

/**
 * Spring Security 의objectDefinitionSource 를 확장하였으며, 등록된 restricted times 관련
 * 리소스의 권한 맵핑 처리를 제공한다.
 * 
 * @author Byunghun Woo
 * 
 */
public class ReloadableRestrictedTimesFilterInvocationDefinitionSource implements FilterInvocationDefinitionSource,
		InitializingBean, ApplicationContextAware {

	protected final Log logger = LogFactory.getLog(getClass());

	private MessageSource messageSource;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.messageSource = (MessageSource) applicationContext.getBean("messageSource");
	}

	protected MessageSource getMessageSource() {
		return messageSource;
	}

	private UrlMatcher urlMatcher;

	private boolean stripQueryStringFromUrls;

	public boolean isStripQueryStringFromUrls() {
		return stripQueryStringFromUrls;
	}

	public void setStripQueryStringFromUrls(boolean stripQueryStringFromUrls) {
		this.stripQueryStringFromUrls = stripQueryStringFromUrls;
	}

	public UrlMatcher getUrlMatcher() {
		return urlMatcher;
	}

	public void setUrlMatcher(UrlMatcher urlMatcher) {
		this.urlMatcher = urlMatcher;
		setStripQueryStringFromUrls(urlMatcher instanceof AntUrlPathMatcher);
	}

	private ISecuredObjectService securedObjectService;

	public void setSecuredObjectService(ISecuredObjectService securedObjectService) {
		this.securedObjectService = securedObjectService;
	}

	private List alwaysTimeRoleCheck;

	private List alwaysTimeResourceCheck;

	private Map dailyFilteredTimeRoleCheck;

	private Map dailyFilteredTimeResourceCheck;

	public List getAlwaysTimeRoleCheck() {
		return Collections.unmodifiableList(alwaysTimeRoleCheck);
	}

	public List getAlwaysTimeResourceCheck() {
		return Collections.unmodifiableList(alwaysTimeResourceCheck);
	}

	public Map getDailyFilteredTimeRoleCheck() {
		return Collections.unmodifiableMap(dailyFilteredTimeRoleCheck);
	}

	public Map getDailyFilteredTimeResourceCheck() {
		return Collections.unmodifiableMap(dailyFilteredTimeResourceCheck);
	}

	/**
	 * InitializingBean 이며 최초 기동 시에 수행된다. 여기서는 reloadRestrictedTimes 를 호출하는 것이
	 * 전부이다.
	 */
	public void afterPropertiesSet() throws Exception {
		reloadRestrictedTimes();
	}

	/**
	 * 시간에 따른 Role 제한 정보를 얻어와 이를 돌려준다. crash/daily 유형의 경우 alwaysTimeRoleCheck 로,
	 * 기타 유형의 경우 dailyFilteredTimeRoleCheck 로 설정한다.
	 * 
	 * @return restricted time - role 맵핑정보 Object 배열 (alwaysTimeRoleCheck,
	 * dailyFilteredTimeRoleCheck)
	 * @throws Exception
	 */
	public Object[] getRestrictedTimesRoles() throws Exception {
		List alwaysTimeRoleCheck = new ArrayList();
		Map dailyFilteredTimeRoleCheck = new LinkedHashMap();

		List resultList = securedObjectService.getRestrictedTimesRoles();

		Iterator itr = resultList.iterator();
		Map tempMap = null;
		Map beforeMap = null;

		String beforeTimeId = null;
		String presentTimeType = null;
		String presentTimeId = null;
		boolean sameFlag = false;

		while (itr.hasNext()) {
			tempMap = (Map) itr.next();

			presentTimeId = (String) tempMap.get("time_id");
			sameFlag = (presentTimeId.equals(beforeTimeId) ? true : false);
			presentTimeType = (String) tempMap.get("time_type");

			if ("crash".equals(presentTimeType) || "daily".equals(presentTimeType)) {
				// time_id 가 같은 경우 role_id 만 기존 데이터에 합침
				if (sameFlag) {
					beforeMap = (Map) alwaysTimeRoleCheck.get(alwaysTimeRoleCheck.size() - 1);
					((List) beforeMap.get("restrictedRoleIds"))
							.add(new SecurityConfig((String) tempMap.get("role_id")));
				}
				else {
					List configList = new LinkedList();
					configList.add(new SecurityConfig((String) tempMap.get("role_id")));
					tempMap.put("restrictedRoleIds", configList);
					alwaysTimeRoleCheck.add(tempMap);
				}
			}
			else {
				String startDate = (String) tempMap.get("start_date");
				String endDate = (String) tempMap.get("end_date");
				String[] betweenDays = DateUtil.getDates(startDate, endDate, "yyyyMMdd");
				// 성능상 time 비교를 현재 일자에 한정하여 처리하기 위해 각 일자별 filtered 데이터로 구분해
				// 놓음
				for (int i = 0; i < betweenDays.length; i++) {
					// 이미 filtered 날짜로 해당 날짜에 대한 이전 데이터가 저장되어 있는 경우
					if (dailyFilteredTimeRoleCheck.containsKey(betweenDays[i])) {
						// 이전 time_id 와 같은 경우 - Role 만 add 함
						if (sameFlag) {
							List beforeFilteredList = (List) dailyFilteredTimeRoleCheck.get(betweenDays[i]);
							beforeMap = (Map) beforeFilteredList.get(beforeFilteredList.size() - 1);
							((List) beforeMap.get("restrictedRoleIds")).add(new SecurityConfig((String) tempMap
									.get("role_id")));
						}
						else { // 기존 filtered 날짜 데이터 리스트에 현재 처리중인 tempMap 을
							// 추가함
							List beforeFilteredList = (List) dailyFilteredTimeRoleCheck.get(betweenDays[i]);

							List configList = new LinkedList();
							configList.add(new SecurityConfig((String) tempMap.get("role_id")));
							tempMap.put("restrictedRoleIds", configList);
							beforeFilteredList.add(tempMap);
						}
					}
					else { // 현재 처리중인 날짜로 filtered 용 List 최초 생성
						List filteredList = new ArrayList();

						List configList = new LinkedList();
						configList.add(new SecurityConfig((String) tempMap.get("role_id")));
						tempMap.put("restrictedRoleIds", configList);
						filteredList.add(tempMap);

						dailyFilteredTimeRoleCheck.put(betweenDays[i], filteredList);
					}
				}
			}

			beforeTimeId = presentTimeId;
		}

		return new Object[] { alwaysTimeRoleCheck, dailyFilteredTimeRoleCheck };
	}

	/**
	 * 시간에 따른 Resource 제한 정보를 얻어와 이를 돌려준다. crash/daily 유형의 경우
	 * alwaysTimeResourceCheck 로, 기타 유형의 경우 dailyFilteredTimeResourceCheck 로
	 * 설정한다.
	 * 
	 * @return restricted time - resource 맵핑정보 Object 배열 (alwaysTimeRoleCheck,
	 * dailyFilteredTimeRoleCheck)
	 * @throws Exception
	 */
	public Object[] getRestrictedTimesResources() throws Exception {
		List alwaysTimeResourceCheck = new ArrayList();
		Map dailyFilteredTimeResourceCheck = new LinkedHashMap();

		List resultList = securedObjectService.getRestrictedTimesResources();

		Iterator itr = resultList.iterator();
		Map tempMap = null;
		Map beforeMap = null;

		String beforeTimeResourceId = null;
		String presentTimeType = null;
		String presentTimeResourceId = null;
		boolean sameFlag = false;

		while (itr.hasNext()) {
			tempMap = (Map) itr.next();

			presentTimeResourceId = (String) tempMap.get("time_id") + (String) tempMap.get("resource_id");
			sameFlag = (presentTimeResourceId.equals(beforeTimeResourceId) ? true : false);
			presentTimeType = (String) tempMap.get("time_type");

			if ("crash".equals(presentTimeType) || "daily".equals(presentTimeType)) {
				// 이전 id 와 같은 경우 role_id 만 기존 데이터에 합침
				if (sameFlag) {
					if (tempMap.get("exclusion_role_id") != null) {
						beforeMap = (Map) alwaysTimeResourceCheck.get(alwaysTimeResourceCheck.size() - 1);
						((List) beforeMap.get("unrestrictedRoleIds")).add(new SecurityConfig((String) tempMap
								.get("exclusion_role_id")));
					}
				}
				else {
					List configList = new LinkedList();

					// 기본적으로 예약된 Restricted Times 방어용 ROLE 은 무조건 등록 - 사용자에게는 아래
					// ROLE 이 없는 것을 가정함.
					configList.add(new SecurityConfig(RestrictedResourceHolder.RESTRICTED_TIMES_RESERVED_ROLE_NAME));

					// 예외 Role 로 지정한 ROLE 에 대해서는 함께 넣어주어 해당 예외 Role 에 대해서는 접근
					// 가능토록 함
					if (tempMap.get("exclusion_role_id") != null) {
						configList.add(new SecurityConfig((String) tempMap.get("exclusion_role_id")));
					}
					tempMap.put("unrestrictedRoleIds", configList);
					tempMap
							.put("compiledResourcePattern", urlMatcher
									.compile((String) tempMap.get("resource_pattern")));
					alwaysTimeResourceCheck.add(tempMap);
				}
			}
			else {
				String startDate = (String) tempMap.get("start_date");
				String endDate = (String) tempMap.get("end_date");
				String[] betweenDays = DateUtil.getDates(startDate, endDate, "yyyyMMdd");
				// 성능상 time 비교를 현재 일자에 한정하여 처리하기 위해 각 일자별 filtered 데이터로 구분해
				// 놓음
				for (int i = 0; i < betweenDays.length; i++) {
					// 이미 filtered 날짜로 해당 날짜에 대한 이전 데이터가 저장되어 있는 경우
					if (dailyFilteredTimeResourceCheck.containsKey(betweenDays[i])) {
						// 이전 id 와 같은 경우 - Role 만 add 함
						if (sameFlag) {
							if (tempMap.get("exclusion_role_id") != null) {
								List beforeFilteredList = (List) dailyFilteredTimeResourceCheck.get(betweenDays[i]);
								beforeMap = (Map) beforeFilteredList.get(beforeFilteredList.size() - 1);
								((List) beforeMap.get("unrestrictedRoleIds")).add(new SecurityConfig((String) tempMap
										.get("exclusion_role_id")));
							}
						}
						else { // 기존 filtered 날짜 데이터 리스트에 현재 처리중인 tempMap 을
							// 추가함
							List beforeFilteredList = (List) dailyFilteredTimeResourceCheck.get(betweenDays[i]);

							List configList = new LinkedList();

							// 기본적으로 예약된 Restricted Times 방어용 ROLE 은 무조건 등록 -
							// 사용자에게는 아래 ROLE 이 없는 것을 가정함.
							configList.add(new SecurityConfig(
									RestrictedResourceHolder.RESTRICTED_TIMES_RESERVED_ROLE_NAME));
							// 예외 Role 로 지정한 ROLE 에 대해서는 함께 넣어주어 해당 예외 Role 에
							// 대해서는 접근 가능토록 함
							if (tempMap.get("exclusion_role_id") != null) {
								configList.add(new SecurityConfig((String) tempMap.get("exclusion_role_id")));
							}
							tempMap.put("unrestrictedRoleIds", configList);
							tempMap.put("compiledResourcePattern", urlMatcher.compile((String) tempMap
									.get("resource_pattern")));
							beforeFilteredList.add(tempMap);
						}
					}
					else { // 현재 처리중인 날짜로 filtered 용 List 최초 생성
						List filteredList = new ArrayList();

						List configList = new LinkedList();
						// 기본적으로 예약된 Restricted Times 방어용 ROLE 은 무조건 등록 -
						// 사용자에게는 아래 ROLE 이 없는 것을 가정함.
						configList
								.add(new SecurityConfig(RestrictedResourceHolder.RESTRICTED_TIMES_RESERVED_ROLE_NAME));
						// 예외 Role 로 지정한 ROLE 에 대해서는 함께 넣어주어 해당 예외 Role 에
						// 대해서는 접근 가능토록 함
						if (tempMap.get("exclusion_role_id") != null) {
							configList.add(new SecurityConfig((String) tempMap.get("exclusion_role_id")));
						}
						tempMap.put("unrestrictedRoleIds", configList);
						tempMap.put("compiledResourcePattern", urlMatcher.compile((String) tempMap
								.get("resource_pattern")));
						filteredList.add(tempMap);

						dailyFilteredTimeResourceCheck.put(betweenDays[i], filteredList);
					}
				}
			}

			beforeTimeResourceId = presentTimeResourceId;
		}

		return new Object[] { alwaysTimeResourceCheck, dailyFilteredTimeResourceCheck };
	}

	/**
	 * Spring Security 의 ConfigAttributeDefinition 형태로 데이터를 재설정한다.
	 * 
	 * @param timeCheckList always time Check 정보를 포함하고 있는 Map 의 List
	 * @param configKey ConfigAttributeDefinition 로 변환될 role 에 대한 Map key
	 */
	public void listToConfig(List timeCheckList, String configKey) {
		Iterator iter = timeCheckList.iterator();
		while (iter.hasNext()) {
			Map tempMap = (Map) iter.next();
			// 동일한 Map 이 betweendays 의 각 day key 에 대해 참조되고 있음. 이미
			// ConfigAttributeDefinition 로 처리된 경우는 skip 함
			if (!(tempMap.get(configKey) instanceof ConfigAttributeDefinition)) {
				tempMap.put(configKey, new ConfigAttributeDefinition((List) tempMap.get(configKey)));
			}
		}
	}

	/**
	 * Spring Security 의 ConfigAttributeDefinition 형태로 데이터를 재설정한다.
	 * 
	 * @param dailyFilteredMap daily filtered time Check 정보를 포함하고 있는 Map
	 * @param configKey ConfigAttributeDefinition 로 변환될 role 에 대한 Map key
	 */
	public void listToConfig(Map dailyFilteredMap, String configKey) {
		Iterator iter = dailyFilteredMap.entrySet().iterator();

		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			List filteredList = (List) entry.getValue();
			listToConfig(filteredList, configKey);
		}
	}

	/**
	 * restricted times 맵핑 정보를 reload 한다.
	 * 
	 * @throws BaseException
	 */
	public void reloadRestrictedTimes() throws BaseException {
		try {
			Object[] restrictedTimesRoles = getRestrictedTimesRoles();
			Object[] restrictedTimesResources = getRestrictedTimesResources();
			alwaysTimeRoleCheck = (List) restrictedTimesRoles[0];
			dailyFilteredTimeRoleCheck = (Map) restrictedTimesRoles[1];
			alwaysTimeResourceCheck = (List) restrictedTimesResources[0];
			dailyFilteredTimeResourceCheck = (Map) restrictedTimesResources[1];

			// ConfigAttributeDefinition 으로 변환 - Collections.unmodifiableList
			// (변경 불가) 로 처리되기 때문에 마지막에 한번에 변환토록 함.
			listToConfig(alwaysTimeRoleCheck, "restrictedRoleIds");
			listToConfig(dailyFilteredTimeRoleCheck, "restrictedRoleIds");
			listToConfig(alwaysTimeResourceCheck, "unrestrictedRoleIds");
			listToConfig(dailyFilteredTimeResourceCheck, "unrestrictedRoleIds");

			if (logger.isInfoEnabled())
				logger.info("reloadRestrictedTimes completed.");

			if (logger.isDebugEnabled()) {
				logger.debug("alwaysTimeRoleCheck : " + alwaysTimeRoleCheck);
				logger.debug("dailyFilteredTimeRoleCheck : " + dailyFilteredTimeRoleCheck);
				logger.debug("alwaysTimeResourceCheck : " + alwaysTimeResourceCheck);
				logger.debug("dailyFilteredTimeResourceCheck : " + dailyFilteredTimeResourceCheck);
			}

		}
		catch (Exception e) {
			logger.error(getMessageSource().getMessage("error.security.runtime.error",
					new Object[] { "Reload RestrictedTimes" }, Locale.getDefault()), e);
			if (e instanceof BaseException) {
				throw (BaseException) e;
			}
			else {
				throw new BaseException(getMessageSource(), "error.security.runtime.error", new Object[] { e
						.getMessage() }, e);
			}
		}

	}

	/**
	 * restricted times resource 맵핑정보 중 현재 request url 과 매치(설정된 urlMatcher 에 따른
	 * ) 된 경우 해당 맵핑 Role 을 돌려준다.
	 * 
	 * @param map restricted times resource 맵핑정보를 담고 있는 Map
	 * @param url 현재 request url
	 * @return 매치되지 않은 경우 null, 매치된 경우 해당 resource 에 대해 exclusion Role 에 대한
	 * ConfigAttributeDefinition
	 */
	private ConfigAttributeDefinition checkUrlMatching(Map map, String url) {
		Object p = map.get("compiledResourcePattern");
		boolean matched = urlMatcher.pathMatchesUrl(p, url);

		if (logger.isDebugEnabled()) {
			logger.debug("Restricted Times Candidate is: '" + url + "'; pattern is " + p + "; matched=" + matched);
		}

		if (matched) {
			return (ConfigAttributeDefinition) map.get("unrestrictedRoleIds");
		}

		return null;
	}

	/**
	 * restricted times 관련 Role/Resource 맵핑 정보에 대하여 현재 날짜/시각에 해당하는 제한/허용 role
	 * 리스트에 따른 ConfigAttributeDefinition 를 구한다.
	 * 
	 * @param compareCandidateList restricted time 맵핑 정보 - always 체크인 경우 전체 리스트,
	 * dailyfiltered 인 경우 오늘날짜에 해당하는 mapping List
	 * @param url request url
	 * @param currentDateTime 현재 날짜시각
	 * @param currentTime 현재 시각
	 * @param isTimeOnlyCheck 시각 만 체크 여부 (always check 인 경우에 미리 정의된 최소/최대 날짜를
	 * 더해서 현재시각과 비교토록함)
	 * @param isRoleCheck role 체크 여부 (role or resource)
	 * @return 제한/허용 role 리스트에 따른 ConfigAttributeDefinition
	 */
	public ConfigAttributeDefinition lookupRoleOrUrlInCandidateListCheck(List compareCandidateList, String url,
			Date currentDateTime, Date currentTime, boolean isTimeOnlyCheck, boolean isRoleCheck) {

		List candidateFoundCadList = new ArrayList();

		ConfigAttributeDefinition foundCad = null;
		if (compareCandidateList == null) {
			if (logger.isDebugEnabled())
				logger.debug("compareCandidateList is null");
			return foundCad;
		}

		String beforeResourceId = null;
		String presentResourceId = null;

		Iterator iter = compareCandidateList.iterator();

		while (iter.hasNext()) {
			Map map = (Map) iter.next();

			String startDate = (String) map.get("start_date");
			String endDate = (String) map.get("end_date");

			String startTime = (String) map.get("start_time");
			String endTime = (String) map.get("end_time");

			Date startDateTime = null;
			Date endDateTime = null;

			boolean dailyExceed = false;
			boolean dailyExceedMatched = false;

			if (isTimeOnlyCheck) {
				if ("daily".equals((String) map.get("time_type"))) {
					// daily 인 경우 익일로 넘어가는 경우
					if (DateUtil.greaterThan(DateUtil.string2Date(startTime, "HHmmss"), DateUtil.string2Date(endTime,
							"HHmmss"))) {
						// // daily 인 경우 startTime > endTime 인 경우는 오늘 startTime
						// ~ 익일 endTime 으로 계산함
						// // 내일 구하기
						// Calendar cal = new
						// GregorianCalendar(TimeZone.getTimeZone("GMT+09:00"),
						// Locale.KOREA);
						// cal.setTime(new Date());
						// cal.roll(Calendar.DATE, 1);
						// Date date = cal.getTime();
						// String tomorrow = DateUtil.date2String(date,
						// "yyyyMMdd");
						//
						// startDateTime =
						// DateUtil.string2Date(DateUtil.getCurrentTime("yyyyMMdd")
						// + startTime,
						// "yyyyMMddHHmmss");
						// endDateTime = DateUtil.string2Date(tomorrow +
						// endTime, "yyyyMMddHHmmss");
						dailyExceed = true;
						// dailyExceed 인 경우 시간만 비교해서 종료 <= currentTime <= 시작 이
						// 아닌 경우로 match 비교
						if (!(DateUtil.string2Date(endTime, "HHmmss").getTime() <= currentTime.getTime() && currentTime
								.getTime() <= DateUtil.string2Date(startTime, "HHmmss").getTime())) {
							dailyExceedMatched = true;
						}
					}
					else {
						startDateTime = DateUtil.string2Date(DateUtil.getCurrentTime("yyyyMMdd") + startTime,
								"yyyyMMddHHmmss");
						endDateTime = DateUtil.string2Date(DateUtil.getCurrentTime("yyyyMMdd") + endTime,
								"yyyyMMddHHmmss");
					}
				}
				else {
					// 편의상 기타 always(crash) check 인 경우에 미리 정의된 최소/최대 날짜를 더해서
					// 현재시각과 비교토록함.
					startDateTime = DateUtil.string2Date(RestrictedResourceHolder.RESTRICTED_MIN_DATE + startTime,
							"yyyyMMddHHmmss");
					endDateTime = DateUtil.string2Date(RestrictedResourceHolder.RESTRICTED_MAX_DATE + endTime,
							"yyyyMMddHHmmss");
				}
			}
			else { // daily filtered check 인 경우는 해당 날짜+시각 으로 직접 비교함
				startDateTime = DateUtil.string2Date(startDate + startTime, "yyyyMMddHHmmss");
				endDateTime = DateUtil.string2Date(endDate + endTime, "yyyyMMddHHmmss");
			}

			// 현재 처리중인 time_id 에 해당하는 시각이면
			// dailyExceed 인 경우 위에서 시간의 비교만으로 계산하였음.
			if ((dailyExceed && dailyExceedMatched)
					|| (!dailyExceed && DateUtil.greaterThan(currentDateTime, startDateTime) && DateUtil.greaterThan(
							endDateTime, currentDateTime))) {

				presentResourceId = (String) map.get("resource_id");
				// 다른 resource 로 변경된 경우에는 기존에 찾은 resource 에 대한 foundCad 가 존재한다면
				// resource 는 최우선 매칭된 하나만 고려하면 되므로 이전 foundCad 를 리턴하고 끝냄
				if (!isRoleCheck && foundCad != null && beforeResourceId != null
						&& !presentResourceId.equals(beforeResourceId)) {

					// 재처리가 필요하면 아래
					if (candidateFoundCadList.size() > 1) {
						return recalculateCandidate(isRoleCheck, candidateFoundCadList);
					}
					else {
						return foundCad;
					}
				}

				foundCad = (isRoleCheck ? (ConfigAttributeDefinition) map.get("restrictedRoleIds") : checkUrlMatching(
						map, url));
				if (foundCad != null) {
					// role check 인 경우 현재 해당하는 시각에 대한 role 제한을 전부 더해야 함.

					// resource check 인 경우 같은 resource 에 대해 서로 다른 time_id 에 대한
					// 서로 다른 접근 권한을 지정한
					// 경우 우선 순위를 따져 재처리가 필요함

					if (isRoleCheck || (beforeResourceId == null || presentResourceId.equals(beforeResourceId))) {
						candidateFoundCadList.add(foundCad);
					}
				}
			}
			else {
				// 다음 restricted data 비교로 넘어감
				continue;
			}
		} // end while

		// best 매칭되는 url 패턴에 대해 중복의 time_id 에 대해 서로 다른 접근권한 제한이 걸려 있는 경우 - 접근 권한
		// 우선순위를 따져 재처리 필요
		if (candidateFoundCadList.size() > 0) {
			foundCad = recalculateCandidate(isRoleCheck, candidateFoundCadList);
		}

		if (logger.isDebugEnabled())
			logger.debug((isRoleCheck ? "Time Role Check" : "Time Resource Check")
					+ " found ConfigAttributeDefinition : " + foundCad);

		return foundCad;

	}

	/**
	 * roleCheck 인 경우 제한 Role 의 Sum, resourceCheck 인 경우 허용 Role 의 Intersection 에
	 * 대한 재처리 결과를 돌려준다.
	 * 
	 * @param isRoleCheck roleCheck 여부
	 * @param candidateFoundCadList 현재까지 time 매칭이 된 상태에서 모아진 후보 맵핑 권한들
	 * @return 재처리된 권한에 따른 ConfigAttributeDefinition
	 */
	private ConfigAttributeDefinition recalculateCandidate(boolean isRoleCheck, List candidateFoundCadList) {
		ConfigAttributeDefinition foundCad;
		List configList = null;
		List presentList = null;
		List nextList = null;
		for (int i = 0; i < candidateFoundCadList.size(); i++) {
			presentList = (List) ((ConfigAttributeDefinition) candidateFoundCadList.get(i)).getConfigAttributes();
			if (i == 0) {
				configList = presentList;
			}
			if (i + 1 < candidateFoundCadList.size()) {
				nextList = (List) ((ConfigAttributeDefinition) candidateFoundCadList.get(i + 1)).getConfigAttributes();
				// Role Check 인 경우 restricted Role 에 add 해야함.
				if (isRoleCheck) {
					configList = ListUtils.sum(configList, nextList);
				}
				else { // Resource Check 인 경우 unrestricted Role 은 우선 순위를
					// 고려하여 intersection 필요
					configList = ListUtils.intersection(configList, nextList);
				}
			}
		}
		foundCad = new ConfigAttributeDefinition(configList);
		if (logger.isDebugEnabled())
			logger.debug("candidateFoundCadList : " + candidateFoundCadList
					+ (isRoleCheck ? ", summed List : " : ", intersected List : ") + foundCad);
		return foundCad;
	}

	/**
	 * FilterInvocation 인 경우 처리 가능하며 stripQueryStringFromUrls 설정, urlMatcher 의
	 * lowercase 설정에 따라 url 의 가공이 발생하며 현재시각을 구해 restricted times 관련 맵핑 정보와 비교하여
	 * 접근 권한을 처리한다. RestrictedTimesFilterSecurityInterceptor 와 쌍으로 설정되어 하나의
	 * filter 처리 내에서 alwaysTimeRoleCheck, dailyFilteredTimeRoleCheck,
	 * alwaysTimeResourceCheck, dailyFilteredTimeResourceCheck 비교가 순서대로 호출된다.
	 * (ThreadLocal 에 순번 저장)
	 * 
	 * @see anyframe.iam.core.intercept.web.RestrictedTimesFilterSecurityInterceptor#invoke(FilterInvocation)
	 * @see org.springframework.security.intercept.ObjectDefinitionSource#getAttributes(java.lang.Object)
	 */
	public ConfigAttributeDefinition getAttributes(Object object) throws IllegalArgumentException {

		if ((object == null) || !this.supports(object.getClass())) {
			throw new IllegalArgumentException("Object must be a FilterInvocation");
		}

		String url = ((FilterInvocation) object).getRequestUrl();

		// urlMatcher 에 따른 queryString 제거 필요시
		if (stripQueryStringFromUrls) {
			int firstQuestionMarkIndex = url.indexOf("?");
			if (firstQuestionMarkIndex != -1) {
				url = url.substring(0, firstQuestionMarkIndex);
			}
		}

		// lowercase 비교로 설정된 경우
		if (urlMatcher.requiresLowerCaseUrl()) {
			url = url.toLowerCase();
		}

		// 현재 시각
		Date currentDateTime = new Date();
		Date currentTime = DateUtil.string2Date(DateUtil.date2String(currentDateTime, "HHmmss"), "HHmmss");

		// alwaysTimeRoleCheck
		if (RestrictedResourceHolder.RESTRICTED_RESOURCE_TYPE[0].equals(RestrictedResourceHolder.getPresentResource())) {
			return lookupRoleOrUrlInCandidateListCheck(alwaysTimeRoleCheck, url, currentDateTime, currentTime, true,
					true);
		}

		// dailyFilteredTimeRoleCheck
		if (RestrictedResourceHolder.RESTRICTED_RESOURCE_TYPE[1].equals(RestrictedResourceHolder.getPresentResource())) {
			List todayRoleList = (List) dailyFilteredTimeRoleCheck.get(DateUtil.getCurrentTime("yyyyMMdd"));
			return lookupRoleOrUrlInCandidateListCheck(todayRoleList, url, currentDateTime, currentTime, false, true);
		}

		// alwaysTimeResourceCheck
		if (RestrictedResourceHolder.RESTRICTED_RESOURCE_TYPE[2].equals(RestrictedResourceHolder.getPresentResource())) {
			return lookupRoleOrUrlInCandidateListCheck(alwaysTimeResourceCheck, url, currentDateTime, currentTime,
					true, false);
		}

		// dailyFilteredTimeResourceCheck
		if (RestrictedResourceHolder.RESTRICTED_RESOURCE_TYPE[3].equals(RestrictedResourceHolder.getPresentResource())) {
			List todayUrlList = (List) dailyFilteredTimeResourceCheck.get(DateUtil.getCurrentTime("yyyyMMdd"));
			return lookupRoleOrUrlInCandidateListCheck(todayUrlList, url, currentDateTime, currentTime, false, false);
		}

		return null;
	}

	/**
	 * validateConfigAttributes 지원 않음
	 */
	public Collection getConfigAttributeDefinitions() {
		// validateConfigAttributes 지원 않음
		return null;
	}

	/**
	 * 
	 */
	public boolean supports(Class clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
