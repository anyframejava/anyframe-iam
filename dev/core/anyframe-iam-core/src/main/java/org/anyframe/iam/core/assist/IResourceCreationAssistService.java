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
package org.anyframe.iam.core.assist;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Target applications of the collected information is stored.
 * @author Jongpil Park
 * 
 */
public interface IResourceCreationAssistService {
	Log LOGGER = LogFactory.getLog(IResourceCreationAssistService.class);

	/**
	 * the collected information is stored.
	 * @param resourceMapList the collected information
	 * @return true if this collected information was saved, false otherwise
	 * @throws Exception failed to save the collected information
	 */
	public boolean createTargetApplicationResourceInformation(List resourceMapList) throws Exception;
}
