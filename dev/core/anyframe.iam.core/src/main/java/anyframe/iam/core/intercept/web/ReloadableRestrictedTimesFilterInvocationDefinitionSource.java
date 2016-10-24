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
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
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
import anyframe.iam.core.securedobject.ISecuredObjectService;

/**
 * This class extends objectDefinitionSource of Spring Security. It offers a
 * function to treat role mapping of resources related with registered
 * Restricted Times
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
	 * This method is InitializingBean and executed as soon as this class runs.
	 * This method only calls reloadRestrictedTimes.
	 */
	public void afterPropertiesSet() throws Exception {
		reloadRestrictedTimes();
	}

	/**
	 * Get restriction information of roles related time. crash/daily types will
	 * be set alwaysTimeRoleCheck, other types will be
	 * dailyFilteredTimeRoleCheck.
	 * 
	 * @return Object[] Array of mapping information about restricted time and
	 * role
	 * @throws Exception fail to make list
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
				String[] betweenDays = getBetweenDays(startDate, endDate);
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
	 * Get restriction information of resources related with time. crash/daily
	 * types will be set alwayTimeResourceCheck, other types will be set
	 * dailyFilteredTimeResourceCheck
	 * 
	 * @return Object[] Array of mapping information about restricted time and
	 * role
	 * @throws Exception fail to make list
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
				String[] betweenDays = getBetweenDays(startDate, endDate);
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
	 * Reset data into ConfigAttributeDefinition of Spring security
	 * 
	 * @param timeCheckList List of Map includes information of always time
	 * Check
	 * @param configKey Map key about role that will be changed
	 * ConfigAttributeDefinition
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
	 * Reset data into ConfigAttributeDefinition of Spring security
	 * 
	 * @param dailyFilteredMap Map object that contains information of daily
	 * filtered time Check
	 * @param configKey Map key about role that will be changed
	 * ConfigAttributeDefinition
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
	 * Reload mapping information of restricted times
	 * 
	 * @throws BaseException fail to reload data
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
	 * Return matched Role if current request URL matches mapping information of
	 * restricted times resource
	 * 
	 * @param map Map object that contains mapping information of restricted
	 * times resource
	 * @param url current request URL
	 * @return null if url does not matches, ConfigAttributeDefinition about
	 * exclusion Role if URL matches
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
	 * Get ConfigAttributeDefinition about Restricted/Allowed role list that
	 * matches current date/time from Role/Resource mapping information of
	 * restricted times
	 * 
	 * @param compareCandidateList mapping information of restricted time - all
	 * list if always checking mapping list about today date if dailyfiltered
	 * checking
	 * @param url request url
	 * @param currentDateTime current date
	 * @param currentTime current time
	 * @param isTimeOnlyCheck true if only time checking
	 * @param isRoleCheck (role or resource) true if role checking
	 * @return ConfigAttributeDefinition ConfigAttributeDefinition object
	 */
	public ConfigAttributeDefinition lookupRoleOrUrlInCandidateListCheck(List compareCandidateList, String url,
			DateTime currentDateTime, DateTime currentTime, boolean isTimeOnlyCheck, boolean isRoleCheck) {

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

			DateTime startJodaTime = DateTimeFormat.forPattern("HHmmss").parseDateTime(startTime);
			DateTime endJodaTime = DateTimeFormat.forPattern("HHmmss").parseDateTime(endTime);

			DateTime startDateTime = null;
			DateTime endDateTime = null;

			boolean dailyExceed = false;
			boolean dailyExceedMatched = false;

			if (isTimeOnlyCheck) {
				if ("daily".equals((String) map.get("time_type"))) {
					// daily 인 경우 익일로 넘어가는 경우
					if (startJodaTime.isAfter(endJodaTime.getMillis())) {
						dailyExceed = true;
						// dailyExceed 인 경우 시간만 비교해서 종료 <= currentTime <= 시작 이
						// 아닌 경우로 match 비교
						if (!((endJodaTime.isBefore(currentTime.getMillis()) || endJodaTime.isEqual(currentTime
								.getMillis())) && (currentTime.isBefore(startJodaTime.getMillis()) || currentTime
								.isEqual(startJodaTime.getMillis())))) {
							dailyExceedMatched = true;
						}
					}
					else {
						startDateTime = DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(
								currentDateTime.toString("yyyyMMdd") + startTime);
						endDateTime = DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(
								currentDateTime.toString("yyyyMMdd") + endTime);
					}
				}
				else {
					// 편의상 기타 always(crash) check 인 경우에 미리 정의된 최소/최대 날짜를 더해서
					// 현재시각과 비교토록함.
					startDateTime = DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(
							RestrictedResourceHolder.RESTRICTED_MIN_DATE + startTime);
					endDateTime = DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(
							RestrictedResourceHolder.RESTRICTED_MAX_DATE + endTime);
				}
			}
			else { // daily filtered check 인 경우는 해당 날짜+시각 으로 직접 비교함
				startDateTime = DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(startDate + startTime);
				endDateTime = DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(endDate + endTime);
			}

			// 현재 처리중인 time_id 에 해당하는 시각이면
			// dailyExceed 인 경우 위에서 시간의 비교만으로 계산하였음.
			if ((dailyExceed && dailyExceedMatched)
					|| (!dailyExceed && currentDateTime.isAfter(startDateTime.getMillis()) && endDateTime
							.isAfter(currentDateTime.getMillis()))) {

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
	 * in case of roleCheck, return sum of restricted roles in case of
	 * resourceCheck, return result of re-operation with Intersection of allowed
	 * roles
	 * 
	 * @param isRoleCheck true if roleCheck
	 * @param candidateFoundCadList list of candidate permissions that matches
	 * the given time
	 * @return ConfigAttributeDefinition ConfigAttributeDefinition object
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
	 * In case of FilterInvocation, compare current time with mapping
	 * information of restricted times. in pair of
	 * RestrictedTimesFilterSecurityInterceptor, it calls alwaysTimeRoleCheck,
	 * dailyFilteredTimeRoleCheck, alwaysTimeResourceCheck and
	 * dailyFilteredTimeResourceCheck sequentially.
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
		DateTime currentDateTime = new DateTime();
		DateTime currentTime = DateTimeFormat.forPattern("HHmmss").parseDateTime(currentDateTime.toString("HHmmss"));
		String currentDay = DateTimeFormat.forPattern("yyyyMMdd").print(currentDateTime);

		// alwaysTimeRoleCheck
		if (RestrictedResourceHolder.RESTRICTED_RESOURCE_TYPE[0].equals(RestrictedResourceHolder.getPresentResource())) {
			return lookupRoleOrUrlInCandidateListCheck(alwaysTimeRoleCheck, url, currentDateTime, currentTime, true,
					true);
		}

		// dailyFilteredTimeRoleCheck
		if (RestrictedResourceHolder.RESTRICTED_RESOURCE_TYPE[1].equals(RestrictedResourceHolder.getPresentResource())) {
			List todayRoleList = (List) dailyFilteredTimeRoleCheck.get(currentDay);
			return lookupRoleOrUrlInCandidateListCheck(todayRoleList, url, currentDateTime, currentTime, false, true);
		}

		// alwaysTimeResourceCheck
		if (RestrictedResourceHolder.RESTRICTED_RESOURCE_TYPE[2].equals(RestrictedResourceHolder.getPresentResource())) {
			return lookupRoleOrUrlInCandidateListCheck(alwaysTimeResourceCheck, url, currentDateTime, currentTime,
					true, false);
		}

		// dailyFilteredTimeResourceCheck
		if (RestrictedResourceHolder.RESTRICTED_RESOURCE_TYPE[3].equals(RestrictedResourceHolder.getPresentResource())) {
			List todayUrlList = (List) dailyFilteredTimeResourceCheck.get(currentDay);
			return lookupRoleOrUrlInCandidateListCheck(todayUrlList, url, currentDateTime, currentTime, false, false);
		}

		return null;
	}

	/**
	 * Get the String Array of Days between startDate and endDate.
	 * @param startDate
	 * @param endDate
	 * @return String Array. The Days between startDate and endDate. 
	 */
	public String[] getBetweenDays(String startDate, String endDate) {
		List betweenDays = new ArrayList();

		DateTime tempDate = DateTimeFormat.forPattern("yyyyMMdd").parseDateTime(startDate);
		DateTime endJodaDate = DateTimeFormat.forPattern("yyyyMMdd").parseDateTime(endDate);

		betweenDays.add(DateTimeFormat.forPattern("yyyyMMdd").print(tempDate));
		while (tempDate.isBefore(endJodaDate.getMillis())) {
			tempDate = tempDate.plusDays(1);
			betweenDays.add(DateTimeFormat.forPattern("yyyyMMdd").print(tempDate));
		}

		return (String[]) betweenDays.toArray(new String[0]);
	}

	/**
	 * validateConfigAttributes not supported
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
