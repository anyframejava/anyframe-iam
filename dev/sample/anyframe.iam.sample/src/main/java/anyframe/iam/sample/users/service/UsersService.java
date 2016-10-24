package anyframe.iam.sample.users.service;

import anyframe.common.Page;
import anyframe.common.util.SearchVO;
import anyframe.core.generic.service.GenericService;
import anyframe.iam.sample.domain.Users;

public interface UsersService extends GenericService<Users, String> {

	Page getPagingList(SearchVO searchVO) throws Exception;
	Users get(String userId) throws Exception;
	void create(Users users) throws Exception;
	void update(Users users) throws Exception;
	void remove(String userId) throws Exception;
}