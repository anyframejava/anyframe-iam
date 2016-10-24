package org.anyframe.iam.admin.ids.service.impl;

import java.util.List;

import org.anyframe.generic.service.impl.GenericServiceImpl;
import org.anyframe.iam.admin.domain.Groups;
import org.anyframe.iam.admin.domain.Ids;
import org.anyframe.iam.admin.domain.RestrictedTimes;
import org.anyframe.iam.admin.domain.Roles;
import org.anyframe.iam.admin.domain.SecuredResources;
import org.anyframe.iam.admin.domain.ViewResource;
import org.anyframe.iam.admin.ids.dao.IdsDao;
import org.anyframe.iam.admin.ids.service.IdsService;

public class IdsServiceImpl extends GenericServiceImpl<Ids, String> implements
		IdsService {

	private IdsDao idsDao;

	public void setIdsDao(IdsDao idsDao) {
		this.idsDao = idsDao;
	}

	public IdsServiceImpl(IdsDao idsDao) throws Exception {
		super(idsDao);
		this.idsDao = idsDao;
	}

	public Ids save(Ids ids) throws Exception {
		return this.idsDao.save(ids);
	}

	@SuppressWarnings("unchecked")
	public long updateNextId(List objectList, String tableName)
			throws Exception {

		String prefix = "";
		String id = "";
		long maxId = 0;
		String cipers = "";

		for (int i = 0; i < objectList.size(); i++) {
			if ("GROUPS".equals(tableName)) {
				prefix = "GRP-";
				cipers = "[0-9][0-9][0-9][0-9]";
				Groups groups = (Groups) objectList.get(i);
				id = groups.getGroupId();
			} else if("ROLES".equals(tableName)){
				prefix = "ROLE-";
				cipers = "[0-9][0-9][0-9][0-9][0-9]";
				Roles roles = (Roles) objectList.get(i);
				id = roles.getRoleId();
			} else if("VIEW_RESOURCE".equals(tableName)){
				cipers = "[0-9][0-9][0-9][0-9][0-9]";
				prefix = "VIEW-";
				ViewResource viewResources = (ViewResource) objectList.get(i);
				id = viewResources.getViewResourceId();
			} else if("TIMES".equals(tableName)){
				prefix = "TIME-";
				cipers = "[0-9][0-9][0-9][0-9][0-9]";
				RestrictedTimes restrictedTimes = (RestrictedTimes) objectList.get(i);
				id = restrictedTimes.getTimeId();
			} else if("RESOURCE_METHOD".equals(tableName)){
				prefix = "MTD-";
				cipers = "[0-9][0-9][0-9][0-9][0-9][0-9]";
				SecuredResources securedResources = (SecuredResources) objectList.get(i);
				id = securedResources.getResourceId();
				
				updateNextId(objectList, "RESOURCE_PNC");
				updateNextId(objectList, "RESOURCE_URL");
				
			} else if("RESOURCE_PNC".equals(tableName)){
				prefix = "PNC-";
				cipers = "[0-9][0-9][0-9][0-9][0-9][0-9]";
				SecuredResources securedResources = (SecuredResources) objectList.get(i);
				id = securedResources.getResourceId();
				
			} else if("RESOURCE_URL".equals(tableName)){
				prefix = "WEB-";
				cipers = "[0-9][0-9][0-9][0-9][0-9][0-9]";
				SecuredResources securedResources = (SecuredResources) objectList.get(i);
				id = securedResources.getResourceId();
				
			}
			
			if(id.matches(prefix + cipers)){
				id = id.substring(prefix.length());
				int tempInt = Integer.parseInt(id);
				
				if (tempInt > maxId)
					maxId = tempInt;
			}
		}

		Ids ids = new Ids();
		ids.setNextId(maxId + 1);
		ids.setTableName(tableName);

		save(ids);

		return maxId;
	}
}
