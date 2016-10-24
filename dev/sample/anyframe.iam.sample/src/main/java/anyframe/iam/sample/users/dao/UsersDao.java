package anyframe.iam.sample.users.dao;

import anyframe.common.Page;
import anyframe.common.util.SearchVO;
import anyframe.core.generic.dao.GenericDao;

import anyframe.iam.sample.domain.Users;

/**
 * An interface that provides a data management interface to the Users table.
 */
public interface UsersDao extends GenericDao<Users, String> {

	Page getPagingList(SearchVO searchVO) throws Exception;
}