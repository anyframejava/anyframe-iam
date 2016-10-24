package anyframe.iam.admin.dataupload.dao.impl;

import java.util.List;

import anyframe.common.Page;
import anyframe.common.util.StringUtil;
import anyframe.iam.admin.common.IamGenericDaoHibernate;
import anyframe.iam.admin.dataupload.dao.DataUploadDao;
import anyframe.iam.admin.domain.DataUpload;
import anyframe.iam.admin.vo.CommonSearchVO;

public class DataUploadDaoHibernateImpl extends IamGenericDaoHibernate<DataUpload, String> implements DataUploadDao{

	public DataUploadDaoHibernateImpl(){
		super(DataUpload.class);
	}
	
	@SuppressWarnings("unchecked")
	public Page getList(CommonSearchVO searchVO) throws Exception{
		int pageIndex = searchVO.getPage();
		int pageSize = searchVO.getRows();
		String sidx = StringUtil.null2str(searchVO.getSidx());
		String sord = StringUtil.null2str(searchVO.getSord());
		if(pageSize <= 0)
			pageSize = this.getPropertiesService().getInt("PAGE_SIZE");
		int pageUnit = this.getPropertiesService().getInt("PAGE_UNIT");
		String searchCondition = StringUtil.null2str(searchVO.getSearchCondition());
		String searchKeyword = StringUtil.null2str(searchVO.getSearchKeyword());
		
		Object[] args = new Object[5];
		args[0] = "condition=" + searchCondition;
		args[1] = "keywordStr=%" + searchKeyword + "%";
		args[2] = "keywordNum=" + searchKeyword + "";
		args[3] = "sidx=" + sidx;
		args[4] = "sord=" + sord;
		
		List resultList = this.getDynamicHibernateService().findList("findDataUploadList", args, pageIndex, pageSize);
		Long totalSize = (Long) this.getDynamicHibernateService().find("countDataUploadList", args);

		Page resultPage = new Page(resultList, pageIndex, totalSize.intValue(), pageUnit, pageSize);
		return resultPage;
	}
}
