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

package anyframe.iam.admin.groupsusers.dao;

import anyframe.iam.admin.common.IamGenericDao;
import anyframe.iam.admin.domain.GroupsUsers;
import anyframe.iam.admin.domain.GroupsUsersId;

/**
 * An interface that provides a data management interface to the GroupsUsers
 * table.
 * 
 * @author jongpil.park
 */
public interface GroupsUsersDao extends IamGenericDao<GroupsUsers, GroupsUsersId> {
	void remove(String groupId) throws Exception;
	void removeAllGroupsUsers() throws Exception;
}