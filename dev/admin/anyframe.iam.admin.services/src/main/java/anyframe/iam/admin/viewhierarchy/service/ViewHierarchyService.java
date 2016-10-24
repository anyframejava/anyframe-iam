package anyframe.iam.admin.viewhierarchy.service;

import anyframe.core.generic.service.GenericService;
import anyframe.iam.admin.domain.ViewHierarchy;
import anyframe.iam.admin.domain.ViewHierarchyId;

public interface ViewHierarchyService extends GenericService<ViewHierarchy, ViewHierarchyId>{
	ViewHierarchy save(ViewHierarchy viewHierarchy) throws Exception;
	void removeAllViewHierarchy() throws Exception;
}
