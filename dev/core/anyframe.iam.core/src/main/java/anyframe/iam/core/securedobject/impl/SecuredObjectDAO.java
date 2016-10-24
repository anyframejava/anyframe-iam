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
package anyframe.iam.core.securedobject.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.SecurityConfig;
import org.springframework.security.intercept.web.RequestKey;

import anyframe.iam.core.securedobject.ISecuredObjectService;

/**
 * This class is DAO(Data access object) to offer information about Secured Object based on DB
 * It offer some default query and user can re-set query.
 * 
 * @author ByungHun Woo
 */
public class SecuredObjectDAO {

	/**
	 * default query to find mapping information about Role - secured resources with URL type
	 */
	public static final String DEF_ROLES_AND_URL_QUERY = "SELECT a.resource_pattern url, b.role_id authority "
			+ "FROM secured_resources a, secured_resources_roles b " + "WHERE a.resource_id = b.resource_id "
			+ "AND a.resource_type = 'url' " + "ORDER BY a.sort_order, a.resource_id ";

	/**
	 * default query to find mapping information about Role - secured resources with Method type
	 */
	public static final String DEF_ROLES_AND_METHOD_QUERY = "SELECT a.resource_pattern method, b.role_id authority "
			+ "FROM secured_resources a, secured_resources_roles b " + "WHERE a.resource_id = b.resource_id "
			+ "AND a.resource_type = 'method' " + "ORDER BY a.sort_order, a.resource_id ";

	/**
	 * default query to find mapping information about Role - secured resources with PointCut type 
	 */
	public static final String DEF_ROLES_AND_POINTCUT_QUERY = "SELECT a.resource_pattern pointcut, b.role_id authority "
			+ "FROM secured_resources a, secured_resources_roles b "
			+ "WHERE a.resource_id = b.resource_id "
			+ "AND a.resource_type = 'pointcut' " + "ORDER BY a.sort_order, a.resource_id ";

	/**
	 * default query to find mapping information about Role - best matching secured resources with URL type
	 * in every request.
	 * (Oracle 10g specific)
	 */
	public static final String DEF_REGEX_MATCHED_REQUEST_MAPPING_QUERY_ORACLE10G = "SELECT a.resource_pattern uri, b.role_id authority "
			+ "FROM secured_resources a, secured_resources_roles b "
			+ "WHERE a.resource_id = b.resource_id "
			+ "AND a.resource_id =  "
			+ " ( SELECT resource_id FROM "
			+ "    ( SELECT resource_id, ROW_NUMBER() OVER (ORDER BY sort_order) resource_order FROM secured_resources c "
			+ "      WHERE REGEXP_LIKE ( :url, c.resource_pattern ) "
			+ "      AND c.resource_type = 'url' "
			+ "      ORDER BY c.sort_order ) " + "   WHERE resource_order = 1 ) ";

	/**
	 * default query to find hierarchy of Role
	 */
	public static final String DEF_HIERARCHICAL_ROLES_QUERY = "SELECT a.child_role child, a.parent_role parent "
			+ "FROM roles_hierarchy a LEFT JOIN roles_hierarchy b on (a.child_role = b.parent_role) ";

	/**
	 * default query to find mapping information about restricted times roles.
	 */
	public static final String DEF_RESTRICTED_TIMES_ROLES_QUERY = "SELECT time_type, a.time_id as time_id, start_date, start_time, end_date, end_time, role_id "
			+ "FROM restricted_times a, restricted_times_roles b "
			+ "WHERE a.time_id = b.time_id "
			+ "ORDER BY time_type, start_date, start_time, end_date, end_time ";

	/**
	 * default query to find mapping information about restricted times resources.
	 */
	public static final String DEF_RESTRICTED_TIMES_RESOURCES_QUERY = "SELECT time_type, a.time_id as time_id, start_date, start_time, end_date, end_time, b.resource_id as resource_id, resource_pattern, role_id as exclusion_role_id "
			+ "FROM restricted_times a,  "
			+ "	restricted_times_resources b left outer join times_resources_exclusion c "
			+ "	on (b.time_id = c.time_id and b.resource_id = c.resource_id) "
			+ ", secured_resources d "
			+ "WHERE a.time_id = b.time_id "
			+ "AND b.resource_id = d.resource_id "
			+ "AND d.resource_type = 'url' "
			+ "ORDER BY d.sort_order, time_type, start_date, start_time, end_date, end_time ";

	/**
	 * default query to find mapping information of view resource.
	 */
	public static final String DEF_VIEW_RESOURCE_MAPPING_QUERY = "SELECT view_resource_id, ref_type, ref_id, mask, permissions  "
			+ "FROM view_resources_mapping "
			+ "WHERE view_resource_id = :viewResourceId "
			+ "AND ( "
			+ "		   ( ref_id IN ( {{userRoleList}} ) AND ref_type = 'ROLE' ) "
			+ "		OR ( ref_id = :userId AND ref_type = 'USER' ) "
			+ "		OR ( ref_id = :groupId AND ref_type = 'GROUP' ) "
			+ "	) "
			+ "ORDER BY CASE ref_type WHEN 'USER' THEN 1 WHEN 'GROUP' THEN 2 WHEN 'ROLE' THEN 3 ELSE 10 END, ref_id ";

	private String sqlRolesAndUrl;

	private String sqlRolesAndMethod;

	private String sqlRolesAndPointcut;

	private String sqlRegexMatchedRequestMapping;

	private String sqlHierarchicalRoles;

	private String sqlRestrictedTimesRoles;

	private String sqlRestrictedTimesResources;

	private String sqlViewResourceMapping;

	public SecuredObjectDAO() {
		this.sqlRolesAndUrl = DEF_ROLES_AND_URL_QUERY;
		this.sqlRolesAndMethod = DEF_ROLES_AND_METHOD_QUERY;
		this.sqlRolesAndPointcut = DEF_ROLES_AND_POINTCUT_QUERY;
		this.sqlRegexMatchedRequestMapping = DEF_REGEX_MATCHED_REQUEST_MAPPING_QUERY_ORACLE10G;
		this.sqlHierarchicalRoles = DEF_HIERARCHICAL_ROLES_QUERY;
		this.sqlRestrictedTimesRoles = DEF_RESTRICTED_TIMES_ROLES_QUERY;
		this.sqlRestrictedTimesResources = DEF_RESTRICTED_TIMES_RESOURCES_QUERY;
		this.sqlViewResourceMapping = DEF_VIEW_RESOURCE_MAPPING_QUERY;
	}

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public String getSqlRolesAndUrl() {
		return sqlRolesAndUrl;
	}

	public void setSqlRolesAndUrl(String sqlRolesAndUrl) {
		this.sqlRolesAndUrl = sqlRolesAndUrl;
	}

	public String getSqlRolesAndMethod() {
		return sqlRolesAndMethod;
	}

	public void setSqlRolesAndMethod(String sqlRolesAndMethod) {
		this.sqlRolesAndMethod = sqlRolesAndMethod;
	}

	public String getSqlRolesAndPointcut() {
		return sqlRolesAndPointcut;
	}

	public void setSqlRolesAndPointcut(String sqlRolesAndPointcut) {
		this.sqlRolesAndPointcut = sqlRolesAndPointcut;
	}

	public String getSqlRegexMatchedRequestMapping() {
		return sqlRegexMatchedRequestMapping;
	}

	public void setSqlRegexMatchedRequestMapping(String sqlRegexMatchedRequestMapping) {
		this.sqlRegexMatchedRequestMapping = sqlRegexMatchedRequestMapping;
	}

	public String getSqlHierarchicalRoles() {
		return sqlHierarchicalRoles;
	}

	public void setSqlHierarchicalRoles(String sqlHierarchicalRoles) {
		this.sqlHierarchicalRoles = sqlHierarchicalRoles;
	}

	public String getSqlRestrictedTimesRoles() {
		return sqlRestrictedTimesRoles;
	}

	public void setSqlRestrictedTimesRoles(String sqlRestrictedTimesRoles) {
		this.sqlRestrictedTimesRoles = sqlRestrictedTimesRoles;
	}

	public String getSqlRestrictedTimesResources() {
		return sqlRestrictedTimesResources;
	}

	public void setSqlRestrictedTimesResources(String sqlRestrictedTimesResources) {
		this.sqlRestrictedTimesResources = sqlRestrictedTimesResources;
	}

	public String getSqlViewResourceMapping() {
		return sqlViewResourceMapping;
	}

	public void setSqlViewResourceMapping(String sqlViewResourceMapping) {
		this.sqlViewResourceMapping = sqlViewResourceMapping;
	}

	public LinkedHashMap getRolesAndResources(String resourceType) throws Exception {

		LinkedHashMap resourcesMap = new LinkedHashMap();

		String sqlRolesAndResources;
		boolean isResourcesUrl = true;
		if ("method".equals(resourceType)) {
			sqlRolesAndResources = getSqlRolesAndMethod();
			isResourcesUrl = false;
		}
		else if ("pointcut".equals(resourceType)) {
			sqlRolesAndResources = getSqlRolesAndPointcut();
			isResourcesUrl = false;
		}
		else {
			sqlRolesAndResources = getSqlRolesAndUrl();
		}

		List resultList = this.namedParameterJdbcTemplate.queryForList(sqlRolesAndResources, new HashMap());

		Iterator itr = resultList.iterator();
		Map tempMap;
		String preResource = null;
		String presentResourceStr;
		Object presentResource;
		while (itr.hasNext()) {
			tempMap = (Map) itr.next();

			presentResourceStr = (String) tempMap.get(resourceType);
			// url 인 경우 RequestKey 형식의 key를 Map에 담아야 함
			presentResource = isResourcesUrl ? new RequestKey(presentResourceStr) : (Object) presentResourceStr;
			List configList = new LinkedList();

			// 이미 requestMap 에 해당 Resource 에 대한 Role 이
			// 하나 이상 맵핑되어 있었던 경우, sort_order 는
			// resource(Resource) 에 대해 매겨지므로 같은
			// Resource 에 대한 Role 맵핑은 연속으로 조회됨.
			// 해당 맵핑 Role List (SecurityConfig) 의 데이터를
			// 재활용하여 새롭게 데이터 구축
			if (preResource != null && presentResourceStr.equals(preResource)) {
				List preAuthList = (List) ((ConfigAttributeDefinition) resourcesMap.get(presentResource))
						.getConfigAttributes();
				Iterator preAuthItr = preAuthList.iterator();
				while (preAuthItr.hasNext()) {
					SecurityConfig tempConfig = (SecurityConfig) preAuthItr.next();
					configList.add(tempConfig);
				}
			}

			configList.add(new SecurityConfig((String) tempMap.get("authority")));
			ConfigAttributeDefinition cad = new ConfigAttributeDefinition(configList);

			// 만약 동일한 Resource 에 대해 한개 이상의 Role 맵핑 추가인
			// 경우 이전 resourceKey 에 현재 새로 계산된 Role 맵핑
			// 리스트로 덮어쓰게 됨.
			resourcesMap.put(presentResource, cad);

			// 이전 resource 비교위해 저장
			preResource = presentResourceStr;
		}

		return resourcesMap;
	}

	public LinkedHashMap getRolesAndUrl() throws Exception {
		return getRolesAndResources("url");
	}

	public LinkedHashMap getRolesAndMethod() throws Exception {
		return getRolesAndResources("method");
	}

	public LinkedHashMap getRolesAndPointcut() throws Exception {
		return getRolesAndResources("pointcut");
	}

	public ConfigAttributeDefinition getRegexMatchedRequestMapping(String url) throws Exception {

		ConfigAttributeDefinition attributes = null;

		// best regex matching - best 매칭된 Uri 에 따른 Role
		// List 조회, DB 차원의 정규식 지원이 있는 경우 사용 (ex. hsqldb
		// custom function, Oracle 10g regexp_like 등)
		Map paramMap = new HashMap();
		paramMap.put("url", url);
		List resultList = this.namedParameterJdbcTemplate.queryForList(getSqlRegexMatchedRequestMapping(), paramMap);

		Iterator itr = resultList.iterator();
		Map tempMap;
		List configList = new LinkedList();
		// 같은 Uri 에 대한 Role 맵핑이므로 무조건 configList 에 add
		// 함
		while (itr.hasNext()) {
			tempMap = (Map) itr.next();
			configList.add(new SecurityConfig((String) tempMap.get("authority")));
		}

		if (configList.size() > 0) {
			attributes = new ConfigAttributeDefinition(configList);
			ISecuredObjectService.LOGGER.debug("Request Uri : " + url + ", matched Uri : "
					+ ((Map) resultList.get(0)).get("uri") + ", mapping Roles : " + attributes);
		}

		return attributes;
	}

	public String getHierarchicalRoles() throws Exception {

		List resultList = this.namedParameterJdbcTemplate.queryForList(getSqlHierarchicalRoles(), new HashMap());

		Iterator itr = resultList.iterator();
		StringBuffer concatedRoles = new StringBuffer();
		Map tempMap;
		while (itr.hasNext()) {
			tempMap = (Map) itr.next();
			concatedRoles.append(tempMap.get("child"));
			concatedRoles.append(" > ");
			concatedRoles.append(tempMap.get("parent"));
			concatedRoles.append("\n");
		}

		return concatedRoles.toString();
	}

	public List getRestrictedTimesRoles() throws Exception {

		return this.namedParameterJdbcTemplate.queryForList(getSqlRestrictedTimesRoles(), new HashMap());

	}

	public List getRestrictedTimesResources() throws Exception {

		return this.namedParameterJdbcTemplate.queryForList(getSqlRestrictedTimesResources(), new HashMap());

	}

	public List getViewResourceMapping(Map paramMap) {

		String sql = getSqlViewResourceMapping();
		String userRoleList = "'UNDEFINED'";
		String groupId = "UNDEFINED";
		if (!paramMap.containsKey("groupId")) {
			paramMap.put("groupId", groupId);
		}

		List authorities = (List) paramMap.get("authorities");
		if (authorities != null) {
			for (int i = 0; i < authorities.size(); i++) {
				if (i == 0) {
					userRoleList = ":userRole" + i;
					paramMap.put("userRole" + i, authorities.get(i));
				}
				else {
					userRoleList += (", :userRole" + i);
					paramMap.put("userRole" + i, authorities.get(i));
				}
			}
		}

		sql = sql.replace("{{userRoleList}}", userRoleList);

		return this.namedParameterJdbcTemplate.queryForList(sql, paramMap);
	}

}
