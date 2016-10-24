package org.anyframe.iam.admin.viewhierarchy.dao;

import org.anyframe.iam.admin.common.IamGenericDao;
import org.anyframe.iam.admin.domain.ViewHierarchy;
import org.anyframe.iam.admin.domain.ViewHierarchyId;


public interface ViewHierarchyDao extends IamGenericDao<ViewHierarchy, ViewHierarchyId>{
	void removeAllViewHierarchy() throws Exception;
}
