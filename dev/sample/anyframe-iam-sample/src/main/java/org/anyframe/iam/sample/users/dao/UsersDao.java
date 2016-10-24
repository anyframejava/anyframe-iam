package org.anyframe.iam.sample.users.dao;

import org.anyframe.datatype.SearchVO;
import org.anyframe.generic.dao.GenericDao;
import org.anyframe.iam.sample.domain.Users;
import org.anyframe.pagination.Page;


/**
 * An interface that provides a data management interface to the Users table.
 */
public interface UsersDao extends GenericDao<Users, String> {

	Page getPagingList(SearchVO searchVO) throws Exception;
}