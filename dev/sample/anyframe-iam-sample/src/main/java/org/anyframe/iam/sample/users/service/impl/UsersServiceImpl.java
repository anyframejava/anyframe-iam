package org.anyframe.iam.sample.users.service.impl;

import org.anyframe.datatype.SearchVO;
import org.anyframe.generic.service.impl.GenericServiceImpl;
import org.anyframe.iam.sample.domain.Users;
import org.anyframe.iam.sample.users.dao.UsersDao;
import org.anyframe.iam.sample.users.service.UsersService;
import org.anyframe.pagination.Page;

public class UsersServiceImpl extends GenericServiceImpl<Users, String> implements UsersService {
    UsersDao usersDao;

    public UsersServiceImpl(UsersDao usersDao) {
        super(usersDao);
        this.usersDao = usersDao;
    }
    
	public Page getPagingList(SearchVO searchVO) throws Exception {
		return this.usersDao.getPagingList(searchVO);
	}        
}