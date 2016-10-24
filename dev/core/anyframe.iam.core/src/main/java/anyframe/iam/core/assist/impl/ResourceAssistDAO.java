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
package anyframe.iam.core.assist.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class ResourceAssistDAO {

	Log LOGGER = LogFactory.getLog(ResourceAssistDAO.class);

	public static final String DEL_CANDIDATE_RESOURCE_SQL = "DELETE FROM CANDIDATE_SECURED_RESOURCES";

	public static final String INS_CANDIDATE_RESOURCE_SQL = "INSERT INTO CANDIDATE_SECURED_RESOURCES	"
			+ "VALUES(:candidate_resource_id, :system_name, :beanid, 	" + "		:packages, :classes, :method, 		"
			+ "		:parameter, :requestmapping, 		" + "		:pointcut, :candidate_resource_type)";

	private String delCandidateResources;

	private String insCandidateResources;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public ResourceAssistDAO() {
		this.delCandidateResources = DEL_CANDIDATE_RESOURCE_SQL;
		this.insCandidateResources = INS_CANDIDATE_RESOURCE_SQL;
	}

	public void setDataSource(DataSource dataSource) {
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public String getDelCandidateResources() {
		return delCandidateResources;
	}

	public void setDelCandidateResources(String delCandidateResources) {
		this.delCandidateResources = delCandidateResources;
	}

	public String getInsCandidateResources() {
		return insCandidateResources;
	}

	public void setInsCandidateResources(String insCandidateResources) {
		this.insCandidateResources = insCandidateResources;
	}

	public boolean createCandidateResource(List resourceMapList) throws Exception {
		int deleteRow = namedParameterJdbcTemplate.getJdbcOperations().update(getDelCandidateResources());

		LOGGER.debug(deleteRow + " rows deleted");

		for (int i = 0; i < resourceMapList.size(); i++) {
			Map resourcesMap = (Map) resourceMapList.get(i);
			resourcesMap.put("candidate_resource_id", new Integer(i + 1));
			namedParameterJdbcTemplate.update(getInsCandidateResources(), resourcesMap);
		}

		return true;
	}
}
