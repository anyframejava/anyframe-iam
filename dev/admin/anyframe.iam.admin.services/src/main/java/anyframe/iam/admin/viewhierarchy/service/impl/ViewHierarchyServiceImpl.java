package anyframe.iam.admin.viewhierarchy.service.impl;

import anyframe.core.generic.service.impl.GenericServiceImpl;
import anyframe.iam.admin.domain.ViewHierarchy;
import anyframe.iam.admin.domain.ViewHierarchyId;
import anyframe.iam.admin.viewhierarchy.dao.ViewHierarchyDao;
import anyframe.iam.admin.viewhierarchy.service.ViewHierarchyService;

public class ViewHierarchyServiceImpl extends GenericServiceImpl<ViewHierarchy, ViewHierarchyId> implements ViewHierarchyService{
	ViewHierarchyDao viewHierarchyDao;
	
	public ViewHierarchyServiceImpl(ViewHierarchyDao viewHierarchyDao){
		super(viewHierarchyDao);
		this.viewHierarchyDao = viewHierarchyDao;
	}
	
	public ViewHierarchy save(ViewHierarchy viewHierarchy) throws Exception{
		return viewHierarchyDao.save(viewHierarchy);
	}
	
	public void removeAllViewHierarchy() throws Exception{
		viewHierarchyDao.removeAllViewHierarchy();
	}
}
