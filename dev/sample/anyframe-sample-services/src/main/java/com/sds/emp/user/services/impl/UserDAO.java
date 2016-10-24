package com.sds.emp.user.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.orm.ObjectRetrievalFailureException;

import anyframe.common.Page;
import anyframe.common.util.DateUtil;
import anyframe.core.properties.IPropertiesService;
import anyframe.core.query.IQueryService;

import com.sds.emp.common.EmpUtil;
import com.sds.emp.user.services.SearchVO;
import com.sds.emp.user.services.UserVO;

public class UserDAO {

    /** an instance variable for the queryService. */
    private IQueryService queryService;

    /** an instance variable for the propertiesService. */
    private IPropertiesService propertiesService;

    /** an instance variable for the hibernateService. */
    // private ICommonService hibernateService;
    /**
     * Sets the name of the QueryService to use.
     * @param queryService
     *        queryService for this member
     */
    public void setQueryService(IQueryService queryService) {
        this.queryService = queryService;
    }

    /**
     * Sets the name of the PropertiesService to use.
     * @param propertiesService
     *        propertiesService for this member.
     */
    public void setPropertiesService(IPropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    /**
     * Sets the name of the HibernateService to use.
     * @param commonService
     *        commonService for this member
     */
    /*
     * public void setHibernateService(ICommonService
     * hibernateService) { this.hibernateService =
     * hibernateService; }
     */

    /**
     * Returns User List based on
     * <code>searchVO.getSearchCondition()</code> if
     * value is 0 then userId is used else userName is
     * used. Wild card parten % is added to the search
     * @param searchVO
     *        search criteria.
     * @return Page as User List.
     * @throws Exception
     *         if query fails.
     */
    public Page getUserList(SearchVO searchVO) throws Exception {
        int pageIndex = searchVO.getPageIndex();
        int pageSize = propertiesService.getInt("PAGE_SIZE");
        int pageUnit = propertiesService.getInt("PAGE_UNIT");

        Object[] iVal = new Object[1];

        String searchCondition =
            EmpUtil.null2str(searchVO.getSearchCondition());
        String searchKeyword = EmpUtil.null2str(searchVO.getSearchKeyword());

        if ("".equals(searchCondition) || "0".equals(searchCondition))
            iVal[0] = "userId=%" + searchKeyword + "%";
        else
            iVal[0] = "userName=%" + searchKeyword + "%";

        HashMap userListMap =
            queryService.findWithRowCount("getUserList", iVal, pageIndex,
                pageSize);

        ArrayList resultList = (ArrayList) userListMap.get(IQueryService.LIST);
        int totalSize =
            ((Long) userListMap.get(IQueryService.COUNT)).intValue();
        Page resultPage =
            new Page(resultList, (new Integer(pageIndex)).intValue(),
                totalSize, pageUnit, pageSize);

        return resultPage;

    }

    /**
     * Returns UserVO for the given user id.
     * @param userId
     *        search criteria.
     * @return <code>UserVO</code> if found else
     *         <code>null</code>
     * @throws Exception
     *         if query fails.
     */
    public UserVO getUser(String userId) throws Exception {
        try {
            // return (UserVO)
            // hibernateService.getObject(UserVO.class,
            // userId);
            Collection saleCollection =
                queryService.find("getUser", new Object[] {userId });

            Iterator userItr = saleCollection.iterator();
            if (userItr.hasNext()) {
                return (UserVO) userItr.next();
            }
            return null;
        } catch (ObjectRetrievalFailureException e) {
            return null;
        }
    }

    /**
     * Adds user.
     * @param userVO
     *        user object
     * @throws Exception
     *         if query fails.
     */
    public void addUser(UserVO userVO) throws Exception {
        userVO.setEnabled(true);

        // hibernateService.saveObject(userVO);
        queryService.create("addUser", new Object[] {userVO.getUserId(),
            userVO.getUserName(), userVO.getPassword() });
        //queryService.create("addAuthority", new Object[] {userVO.getUserId() });
    }

    /**
     * Updates the given user.
     * @param userVO
     *        user object
     * @throws Exception
     *         if query fails.
     */
    public void updateUser(UserVO userVO) throws Exception {
        // hibernateService.updateObject(userVO);

        String userName = userVO.getUserName();
        String userId = userVO.getUserId();

        queryService.update("updateUser", new Object[] {userName, userId });

    }

    /**
     * checks for duplicate value.
     * @param userId
     *        search criteria.
     * @return <code>0</code> if user does not exist
     *         else returns the number of time user is
     *         present in system.
     * @throws Exception
     *         if find query fails.
     */
    public int checkDuplication(String userId) throws Exception {
        Collection countCollection = null;

        countCollection =
            queryService.find("checkDuplication", new Object[] {userId });

        Iterator countItr = countCollection.iterator();
        if (countItr.hasNext()) {
            Map countMap = (Map) countItr.next();
            int count = ((Integer) countMap.get("rowcount")).intValue();
            return count;
        }
        return 0;
    }

}
