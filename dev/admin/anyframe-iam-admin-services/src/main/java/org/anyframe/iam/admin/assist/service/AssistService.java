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

package org.anyframe.iam.admin.assist.service;

import java.util.List;
import java.util.Map;

import org.anyframe.generic.service.GenericService;
import org.anyframe.iam.admin.domain.CandidateSecuredResources;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An interface that provides the AssisService
 * 
 * @author jongpil.park
 */
public interface AssistService extends GenericService<CandidateSecuredResources, String> {
	Log LOGGER = LogFactory.getLog(AssistService.class);
	
	/**
	 * remove and save candidate secured resource into DB
	 * @param resourceMapList List<Map<String, Object>> object
	 * @return true if save success
	 * @throws Exception fail to save data
	 */
	boolean save(List<Map<String, Object>> resourceMapList) throws Exception;
}
