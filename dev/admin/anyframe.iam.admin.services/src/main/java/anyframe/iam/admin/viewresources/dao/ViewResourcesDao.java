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

package anyframe.iam.admin.viewresources.dao;

import anyframe.common.Page;
import anyframe.iam.admin.common.IamGenericDao;
import anyframe.iam.admin.domain.ViewResource;
import anyframe.iam.admin.vo.ViewResourceSearchVO;

/**
 * An interface that provides a data management interface to the View_Resources
 * table.
 * @author youngmin.jo
 */
public interface ViewResourcesDao extends IamGenericDao<ViewResource, String> {

	/**
	 * find list of view resources that matches the given search conditions in
	 * View_Resources table
	 * @param viewResourceSearchVO an object that contains search conditions
	 * @return Page list of view resources mapping data
	 * @throws Exception fail to find list
	 */
	Page getList(ViewResourceSearchVO viewResourceSearchVO) throws Exception;
}
