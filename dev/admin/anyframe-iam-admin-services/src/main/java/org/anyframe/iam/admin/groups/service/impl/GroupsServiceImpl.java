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

package org.anyframe.iam.admin.groups.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.anyframe.generic.service.impl.GenericServiceImpl;
import org.anyframe.iam.admin.authorities.service.AuthoritiesService;
import org.anyframe.iam.admin.common.IAMException;
import org.anyframe.iam.admin.domain.Groups;
import org.anyframe.iam.admin.domain.GroupsHierarchy;
import org.anyframe.iam.admin.domain.GroupsHierarchyId;
import org.anyframe.iam.admin.domain.IamTree;
import org.anyframe.iam.admin.domain.TempGroups;
import org.anyframe.iam.admin.groups.dao.GroupsDao;
import org.anyframe.iam.admin.groups.service.GroupsService;
import org.anyframe.iam.admin.groupshierarchy.service.GroupsHierarchyService;
import org.anyframe.iam.admin.groupsusers.dao.GroupsUsersDao;
import org.anyframe.iam.admin.groupsusers.service.GroupsUsersService;
import org.anyframe.util.DateUtil;

public class GroupsServiceImpl extends GenericServiceImpl<Groups, String>
		implements GroupsService {
	private GroupsDao groupsDao;
	private GroupsUsersDao groupsUsersDao;
	private GroupsHierarchyService groupsHierarchyService;
	private GroupsUsersService groupsUsersService;
	private AuthoritiesService authoritiesService;

	private static List<String> groupIds = new ArrayList<String>();

	public GroupsServiceImpl(GroupsDao groupsDao) {
		super(groupsDao);
		this.groupsDao = groupsDao;
	}

	public void setGroupsUsersDao(GroupsUsersDao groupsUsersDao) {
		this.groupsUsersDao = groupsUsersDao;
	}

	public void setGroupsHierarchyService(
			GroupsHierarchyService groupsHierarchyService) {
		this.groupsHierarchyService = groupsHierarchyService;
	}

	public void setGroupsUsersService(GroupsUsersService groupsUsersService) {
		this.groupsUsersService = groupsUsersService;
	}

	public void setAuthoritiesService(AuthoritiesService authoritiesService) {
		this.authoritiesService = authoritiesService;
	}

	// Group Tree 정보 조회
	public List<IamTree> getGroupTree(String parentNode) throws Exception {
		return groupsDao.getGroupTree(parentNode);
	}

	// RootNode 정보를 조회

	public List<IamTree> getRootNodeOfGroups() throws Exception {
		return groupsDao.getRootNodeOfGroups();
	}

	// Group 정보를 수정
	public void update(Groups groups) throws Exception {
		groups.setModifyDate(DateUtil.getCurrentTime("yyyyMMdd"));

		groupsDao.update(groups);
	}

	// Group 정보 등록
	public Groups save(Groups groups) throws Exception {
		String createDate = DateUtil.getCurrentTime("yyyyMMdd");

		Iterator<GroupsHierarchy> it = groups
				.getGroupsHierarchiesForChildGroup().iterator();

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

	public Groups saveWithoutHierarchy(Groups groups) throws Exception {
		return groupsDao.save(groups);
	}

	// Group 정보를 삭제한다.
	public void remove(String currentNode) throws Exception {
		// node 가 선택이 되었을 경우에만 수행을 한다.
		if (currentNode != null) {
			// 선택된 node 의 하위 node 가 있는지 확인한다.
			List<String> childNode = groupsDao.getGroupHierarchy(currentNode);
			int childNodeCount = childNode.size();

			// 하위 node 가 있을 경우 하위 node 부터 삭제를 한다.
			if (childNodeCount > 0) {
				for (int i = 0; i < childNodeCount; i++) {
					String child = childNode.get(i).toString();
					remove(child);
				}
			}

			groupsUsersDao.remove(currentNode);
			// 선택된 node 정보를 삭제한다.
			groupsDao.remove(currentNode);
		}

		// node 정보가 없이 remove method 를 호출 할 경우 Exception 발생
		else {
			throw new IAMException("No selected node");
		}
	}

	public List<Groups> getList() throws Exception {
		List<Groups> list = groupsDao.getList();

		for (int i = 0; i < list.size(); i++) {
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
		Set<GroupsHierarchy> childSet = foundGroup
				.getGroupsHierarchiesForChildGroup();

		for (GroupsHierarchy groupsHierarchy : childSet) {
			Groups parentGroup = groupsHierarchy.getGroupsByParentGroup();
			groupIds.add(parentGroup.getGroupId());
			findRepeatParentGroup(parentGroup);
		}

		return groupIds;
	}

	public List<TempGroups> makeAllTempGroupsList() throws Exception {
		return this.groupsDao.makeAllTempGroupsList();
	}

	public List<Groups> save(List<TempGroups> tempGroupList) throws Exception {
		List<Groups> resultList = new ArrayList<Groups>();

		for (int i = 0; i < tempGroupList.size(); i++) {
			TempGroups tempGroups = (TempGroups) tempGroupList.get(i);
			Groups groups = saveTempGroupsToGroups(tempGroups);
			resultList.add(groups);
		}

		for (int i = 0; i < tempGroupList.size(); i++) {
			TempGroups tempGroup = (TempGroups) tempGroupList.get(i);
			String parentGroup = tempGroup.getParentGroup();

			if (!"".equals(parentGroup) && parentGroup != null) {
				GroupsHierarchy groupsHierarchy = new GroupsHierarchy();
				GroupsHierarchyId groupsHierarchyId = new GroupsHierarchyId();

				groupsHierarchyId.setChildGroup(tempGroup.getGroupId());
				groupsHierarchyId.setParentGroup(parentGroup);

				groupsHierarchy.setId(groupsHierarchyId);
				groupsHierarchy.setCreateDate(tempGroup.getCreateDate());
				
				groupsHierarchyService.save(groupsHierarchy);
			}
		}
		return resultList;
	}

	public void removeAllGroups() throws Exception {
		groupsHierarchyService.removeAllGroupsHierarchy();
		groupsUsersService.removeAllGroupsUsers();
		authoritiesService.removeAllAuthorities();

		groupsDao.removeAllGroups();
	}

	public Groups saveTempGroupsToGroups(TempGroups tempGroups)
			throws Exception {
		Groups groups = new Groups();

		groups.setGroupId(tempGroups.getGroupId());
		groups.setGroupName(tempGroups.getGroupName());
		groups.setCreateDate(tempGroups.getCreateDate());
		groups.setModifyDate(tempGroups.getModifyDate());

		return saveWithoutHierarchy(groups);
	}
}