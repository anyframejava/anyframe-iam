package org.anyframe.iam.admin.dataupload.dao;

import org.anyframe.iam.admin.common.IamGenericDao;
import org.anyframe.iam.admin.domain.DataUpload;
import org.anyframe.iam.admin.vo.CommonSearchVO;
import org.anyframe.pagination.Page;

public interface DataUploadDao extends IamGenericDao<DataUpload, String> {
	public Page getList(CommonSearchVO searchVO) throws Exception;
}
