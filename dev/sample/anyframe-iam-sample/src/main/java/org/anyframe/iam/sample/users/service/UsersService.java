package org.anyframe.iam.sample.users.service;

import org.anyframe.datatype.SearchVO;
import org.anyframe.generic.service.GenericService;
import org.anyframe.iam.sample.domain.Users;
import org.anyframe.pagination.Page;

public interface UsersService extends GenericService<Users, String> {

	Page getPagingList(SearchVO searchVO) throws Exception;
	Users get(String userId) throws Exception;
	void create(Users users) throws Exception;
	void update(Users users) throws Exception;
	void remove(String userId) throws Exception;
}