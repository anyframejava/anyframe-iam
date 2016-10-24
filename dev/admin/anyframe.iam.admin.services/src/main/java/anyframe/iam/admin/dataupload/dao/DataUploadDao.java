package anyframe.iam.admin.dataupload.dao;

import anyframe.common.Page;
import anyframe.iam.admin.common.IamGenericDao;
import anyframe.iam.admin.domain.DataUpload;
import anyframe.iam.admin.vo.CommonSearchVO;

public interface DataUploadDao extends IamGenericDao<DataUpload, String> {
	public Page getList(CommonSearchVO searchVO) throws Exception;
}
