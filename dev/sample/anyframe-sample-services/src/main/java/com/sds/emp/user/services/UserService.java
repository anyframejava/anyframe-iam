package com.sds.emp.user.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import anyframe.common.Page;

public interface UserService {
    String ROLE = UserService.class.getName();

    /** Logger object. */
    Log LOGGER = LogFactory.getLog(UserService.class);

    /**
     * Returns User List as <code>Page</code> Object.
     * @param searchVO
     *        Search Object.
     * @return <code>Page</code> containing user
     *         List.
     * @throws Exception
     *         if query fails.
     */
    Page getUserList(SearchVO searchVO) throws Exception;

    /**
     * Returns <code>UserVO</code> for the userid
     * passed.
     * @param userId
     *        useri for which search will happen.
     * @return UserVO object
     * @throws Exception
     *         if </code>UserVo</code> is not found
     *         or if any exception is raised.
     */
    UserVO getUser(String userId) throws Exception;

    /**
     * Adds User.
     * @param userVO
     *        User Info
     * @throws Exception
     *         if add query fails.
     */
    void addUser(UserVO userVO) throws Exception;

    /**
     * Update User.
     * @param userVO
     *        User Info
     * @throws Exception
     *         if update query fails or if user id is
     *         not found.
     */
    void updateUser(UserVO userVO) throws Exception;

    /**
     * check for duplicate user id.
     * @param userId
     *        user id to be searched
     * @return <code>true</code> if user exist else
     *         <code>false</code>
     * @throws Exception
     *         if query fails
     */
    boolean checkDuplication(String userId) throws Exception;

}
