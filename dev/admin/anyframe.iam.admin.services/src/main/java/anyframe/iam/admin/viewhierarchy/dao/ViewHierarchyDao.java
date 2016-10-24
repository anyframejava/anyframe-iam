package anyframe.iam.admin.viewhierarchy.dao;

import anyframe.iam.admin.common.IamGenericDao;
import anyframe.iam.admin.domain.ViewHierarchy;
import anyframe.iam.admin.domain.ViewHierarchyId;

public interface ViewHierarchyDao extends IamGenericDao<ViewHierarchy, ViewHierarchyId>{
	void removeAllViewHierarchy() throws Exception;
}
