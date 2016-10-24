package com.sds.emp.purchase.services.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import anyframe.common.Page;
import anyframe.core.properties.IPropertiesService;
import anyframe.core.query.IQueryService;

import com.sds.emp.common.EmpUtil;
import com.sds.emp.purchase.services.DeliveryCompanyVO;
import com.sds.emp.purchase.services.SearchVO;

/**
 * This is the DAO for DeliveryCompany Service.
 */

public class DeliveryCompanyDAO {

    /** an instance variable for the queryService. */
    private IQueryService queryService;

    /** an instance variable for the propertiesService. */
    private IPropertiesService propertiesService;

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
     * This method is used to add the delivery company
     * details into the corresponding deliveryCompnay
     * VO.
     * @param deliveryCompanyVO
     *        Object contains all the details of the
     *        delivery company.
     * @throws Exception
     *         If it fails to add the data into the
     *         Delivery Company VO
     */
    public void addDeliveryCompany(DeliveryCompanyVO deliveryCompanyVO)
            throws Exception {
        String dlvyCompNo = deliveryCompanyVO.getDlvyCompNo();
        String dlvyCompName = deliveryCompanyVO.getDlvyCompName();
        String businessNo = deliveryCompanyVO.getBusinessNo();
        String dlvyCompDesc = deliveryCompanyVO.getDlvyCompDesc();
        String useYn = deliveryCompanyVO.getUseYn();
        String regId = deliveryCompanyVO.getRegId();

        queryService.create("addDeliveryCompany", new Object[] {dlvyCompNo,
            dlvyCompName, businessNo, dlvyCompDesc, useYn, regId, regId });

    }

    /**
     * This method is used to update the delivery
     * company details of the deliveryCompnay VO.
     * @param deliveryCompanyVO
     *        contains all the details of delivery
     *        company
     * @throws Exception
     *         If it fails to update the Delivery
     *         Company data
     */
    public void updateDeliveryCompany(DeliveryCompanyVO deliveryCompanyVO)
            throws Exception {
        String dlvyCompNo = deliveryCompanyVO.getDlvyCompNo();
        String dlvyCompName = deliveryCompanyVO.getDlvyCompName();
        String businessNo = deliveryCompanyVO.getBusinessNo();
        String dlvyCompDesc = deliveryCompanyVO.getDlvyCompDesc();
        String useYn = deliveryCompanyVO.getUseYn();
        String modifyId = deliveryCompanyVO.getModifyId();

        queryService.update("updateDeliveryCompany",
            new Object[] {dlvyCompName, businessNo, dlvyCompDesc, useYn,
                modifyId, dlvyCompNo });

    }

    /**
     * This method is used to get the delivery company
     * details based on dlvyCompNo.
     * @param dlvyCompNo
     *        identifier of DeliveryCompany to execute
     * @return DeliveryCompanyVO contains all the
     *         details of delivery company null if no
     *         deleivery company found.
     * @throws Exception
     *         If it is unable to get delivery company
     *         details
     */
    public DeliveryCompanyVO getDeliveryCompany(String dlvyCompNo)
            throws Exception {

        Collection dlvyCompCollection =
            queryService.find("getDeliveryCompany", new Object[] {dlvyCompNo });
        Iterator dlvyCompItr = dlvyCompCollection.iterator();
        if (dlvyCompItr.hasNext()) {
            return (DeliveryCompanyVO) dlvyCompItr.next();
        }

        return null;
    }

    /**
     * This method is used to get the list of delivery
     * company details based on searchVO.
     * @param searchVO
     *        contains delivery company no and name as
     *        the major search key words.
     * @return Page Contains list of delivery company
     *         details never null.
     * @throws Exception
     *         If it fails to get the list of delivery
     *         company details
     */
    public Page getDeliveryCompanyList(SearchVO searchVO) throws Exception {
        int pageIndex = searchVO.getPageIndex();
        int pageSize = propertiesService.getInt("PAGE_SIZE");
        int pageUnit = propertiesService.getInt("PAGE_UNIT");

        Object[] iVal = new Object[2];

        String searchCondition =
            EmpUtil.null2str(searchVO.getSearchCondition());
        String searchKeyword = EmpUtil.null2str(searchVO.getSearchKeyword());

        if (("".equals(searchCondition) || "0".equals(searchCondition))) {
            iVal[0] = "dlvyCompNo=%" + searchKeyword + "%";
        } else {
            iVal[0] = "dlvyCompName=%" + searchKeyword + "%";
        }

        String searchUseYn = searchVO.getSearchUseYn();

        if (searchUseYn != null && !"A".equals(searchUseYn))
            iVal[1] = "useYn=" + searchUseYn;

        Map dlvyCompListMap = null;

        dlvyCompListMap =
            queryService.findWithRowCount("getDeliveryCompanyList", iVal,
                pageIndex, pageSize);

        List resultList = (List) dlvyCompListMap.get(IQueryService.LIST);
        int totalSize =
            ((Long) dlvyCompListMap.get(IQueryService.COUNT)).intValue();
        Page resultPage =
            new Page(resultList, (new Integer(pageIndex)).intValue(),
                totalSize, pageUnit, pageSize);

        return resultPage;
    }

    /**
     * This method returns delivery company details as
     * a drop down list.
     * @return List consisist of delivery comp no and
     *         name
     * @throws Exception
     *         If any problem while getting the
     *         dropdown list of delivery comapny
     */
    public Collection getDropDownDeliveryCompanyList() throws Exception {

        return queryService.find("getDropDownDeliveryCompanyList",
            new Object[] {});
    }
}
