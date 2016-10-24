package anyframe.iam.sample.users.service.impl;

import anyframe.common.Page;
import anyframe.common.util.SearchVO;
import anyframe.iam.sample.users.dao.UsersDao;
import anyframe.iam.sample.domain.Users;
import anyframe.iam.sample.users.service.UsersService;
import anyframe.core.generic.service.impl.GenericServiceImpl;

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