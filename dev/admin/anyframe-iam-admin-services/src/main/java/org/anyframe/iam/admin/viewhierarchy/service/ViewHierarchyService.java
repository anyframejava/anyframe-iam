package org.anyframe.iam.admin.viewhierarchy.service;

import org.anyframe.generic.service.GenericService;
import org.anyframe.iam.admin.domain.ViewHierarchy;
import org.anyframe.iam.admin.domain.ViewHierarchyId;

public interface ViewHierarchyService extends GenericService<ViewHierarchy, ViewHierarchyId>{
	ViewHierarchy save(ViewHierarchy viewHierarchy) throws Exception;
	void removeAllViewHierarchy() throws Exception;
}
