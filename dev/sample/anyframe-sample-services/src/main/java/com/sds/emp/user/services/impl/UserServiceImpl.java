package com.sds.emp.user.services.impl;

import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;

import anyframe.common.Page;

import com.sds.emp.common.EmpException;
import com.sds.emp.user.services.SearchVO;
import com.sds.emp.user.services.UserService;
import com.sds.emp.user.services.UserVO;

public class UserServiceImpl implements UserService, ApplicationContextAware {

    /** an instance variable for the deliveryCompanyDAO. */
    private UserDAO userDAO;

    /**
     * Used by <code>LOGGER</code> and for creating
     * Exception message.
     */
    private MessageSource messageSource;

    /**
     * This is for setting the Message Source
     * @param messageSource
     *        contains message source
     */
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * sets User dao
     * @param userDAO
     *        user dao
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Returns User List as <code>Page</code> Object.
     * @param searchVO
     *        Search Object.
     * @return <code>Page</code> containing user
     *         List.
     * @throws Exception
     *         if query fails.
     * @see com.sds.emp.user.services.UserService#getUserList(com.sds.emp.user.services.SearchVO)
     */
    public Page getUserList(SearchVO searchVO) throws Exception {
        return userDAO.getUserList(searchVO);
    }

    /**
     * Returns <code>UserVO</code> for the userid
     * passed.
     * @param userId
     *        useri for which search will happen.
     * @return UserVO object
     * @throws Exception
     *         if </code>UserVo</code> is not found
     *         or if any exception is raised.
     * @see com.sds.emp.user.services.UserService#getUser(java.lang.String)
     */
    public UserVO getUser(String userId) throws Exception {

        UserVO userVO = userDAO.getUser(userId);

        if (userVO == null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(messageSource.getMessage("debug.user.get",
                    new String[] {userId }, Locale.getDefault()));
            }
            throw new EmpException(messageSource.getMessage("debug.user.get",
                new String[] {userId }, Locale.getDefault()), null);
        }

        return userVO;
    }

    /**
     * Adds User.
     * @param userVO
     *        User Info
     * @throws Exception
     *         if add query fails.
     * @see com.sds.emp.user.services.UserService#addUser(com.sds.emp.user.services.UserVO)
     */
    public void addUser(UserVO userVO) throws Exception {
        userDAO.addUser(userVO);
    }

    /**
     * Update User.
     * @param userVO
     *        User Info
     * @throws Exception
     *         if update query fails or if user id is
     *         not found.
     * @see com.sds.emp.user.services.UserService#updateUser(com.sds.emp.user.services.UserVO)
     */
    public void updateUser(UserVO userVO) throws Exception {
        userDAO.updateUser(userVO);
    }

    /**
     * check for duplicate user id.
     * @param userId
     *        user id to be searched
     * @return <code>true</code> if user exist else
     *         <code>false</code>
     * @throws Exception
     *         if query fails
     * @see com.sds.emp.user.services.UserService#checkDuplication(java.lang.String)
     */
    public boolean checkDuplication(String userId) throws Exception {
        int count = userDAO.checkDuplication(userId);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(messageSource.getMessage(
                "debug.user.checkDuplication",
                new String[] {userId, count + "" }, Locale.getDefault()));
        }
        return !(count == 0);

    }

    /**
     * This method is used to get the product details
     * based on product No
     * @param applicationContext
     *        <code>ApplicationContext</code> object.
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        messageSource =
            (MessageSource) applicationContext.getBean("messageSource");
    }

}
