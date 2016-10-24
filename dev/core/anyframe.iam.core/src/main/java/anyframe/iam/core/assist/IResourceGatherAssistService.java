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
package anyframe.iam.core.assist;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Collecting information of the target application
 * @author Jongpil Park
 * 
 */
public interface IResourceGatherAssistService {
	Log LOGGER = LogFactory.getLog(IResourceGatherAssistService.class);

	/**
	 * Collecting information
	 * @return Collected information list
	 * @throws Exception failed to collection information
	 */
	public List getTargetApplicationResourceInformation() throws Exception;
}
