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

package anyframe.iam.admin.groups.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import anyframe.core.generic.service.impl.GenericServiceImpl;
import anyframe.iam.admin.common.IAMException;
import anyframe.iam.admin.domain.Groups;
import anyframe.iam.admin.domain.GroupsHierarchy;
import anyframe.iam.admin.domain.GroupsHierarchyId;
import anyframe.iam.admin.domain.IamTree;
import anyframe.iam.admin.groups.dao.GroupsDao;
import anyframe.iam.admin.groups.service.GroupsService;

public class GroupsServiceImpl extends GenericServiceImpl<Groups, String> implements GroupsService {
	private GroupsDao groupsDao;
	
	private static List<String> groupIds = new ArrayList<String>();

	public GroupsServiceImpl(GroupsDao groupsDao) {
		super(groupsDao);
		this.groupsDao = groupsDao;
	}

//	Group Tree 정보 조회
	 
	public List<IamTree> getGroupTree(String parentNode) throws Exception {
		return groupsDao.getGroupTree(parentNode);
	}

	
//	RootNode 정보를 조회
	
	public List<IamTree> getRootNodeOfGroups() throws Exception {
		return groupsDao.getRootNodeOfGroups();
	}

//	Group 정보를 수정
	public void update(Groups groups) throws Exception {
		groups.setModifyDate(anyframe.common.util.DateUtil.getCurrentTime("yyyyMMdd"));
		
		groupsDao.update(groups);
	}

//	Group 정보 등록
	public Groups save(Groups groups) throws Exception {
		String createDate = anyframe.common.util.DateUtil.getCurrentTime("yyyyMMdd");

		Iterator<GroupsHierarchy> it = groups.getGroupsHierarchiesForChildGroup().iterator();

		GroupsHierarchy hierarchy = (GroupsHierarchy) it.next();

		GroupsHierarchy groupsHierarchy = new GroupsHierarchy();
		GroupsHierarchyId groupsHierarchyId = new GroupsHierarchyId();

		groupsHierarchyId.setChildGroup(groups.getGroupId());
		groupsHierarchyId.setParentGroup(hierarchy.getId().getParentGroup());

		groupsHierarchy.setId(groupsHierarchyId);
		groupsHierarchy.setCreateDate(createDate);

		Set<GroupsHierarchy> childGroup = new HashSet<GroupsHierarchy>();

		childGroup.add(groupsHierarchy);

		groups.setCreateDate(createDate);
		groups.setGroupsHierarchiesForChildGroup(childGroup);

		return groupsDao.save(groups);
	}

//	Group 정보를 삭제한다.
	public void remove(String currentNode) throws Exception {
//		node 가 선택이 되었을 경우에만 수행을 한다.
		if (currentNode != null) {

//			선택된 node 의 하위 node 가 있는지 확인한다.
			List<String> childNode = groupsDao.getGroupHierarchy(currentNode);
			int childNodeCount = childNode.size();


//			하위 node 가 있을 경우 하위 node 부터 삭제를 한다.
			if (childNodeCount > 0) {
				for (int i = 0; i < childNodeCount; i++) {
					dao.remove(childNode.get(i).toString());
				}
			}


//			선택된 node 정보를 삭제한다.
			dao.remove(currentNode);
		}

//		node 정보가 없이 remove method 를 호출 할 경우 Exception 발생
		else {
			throw new IAMException("No selected node");
		}
	}

	public List<Groups> getList() throws Exception {
		List<Groups> list = groupsDao.getList();
		
		for(int i = 0 ; i < list.size() ; i++) {
			Groups groups = list.get(i);
			findRepeatParentGroup(groups);
		}
		
		return list;
	}
	
	public String getGroupNameList(String keyword) throws Exception {
		return this.groupsDao.getGroupNameList(keyword);
	}
	
	public String getGroupIdByGroupName(String groupName) throws Exception {
		return groupsDao.getGroupIdByGroupName(groupName);
	}
	
	public List<String> getParentsGroupIds(String groupId) throws Exception {
		Groups groups = this.groupsDao.get(groupId);
		
		List<String> groupIds = findRepeatParentGroup(groups);

		return groupIds;
	}
	
	private List<String> findRepeatParentGroup(Groups foundGroup) {
		Set<GroupsHierarchy> childSet = foundGroup.getGroupsHierarchiesForChildGroup();
		
		for(GroupsHierarchy groupsHierarchy : childSet) {
			Groups parentGroup = groupsHierarchy.getGroupsByParentGroup();
			groupIds.add(parentGroup.getGroupId());
			findRepeatParentGroup(parentGroup);
		}

		return groupIds;
	}
}