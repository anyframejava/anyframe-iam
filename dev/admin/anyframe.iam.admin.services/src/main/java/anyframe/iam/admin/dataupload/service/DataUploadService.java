package anyframe.iam.admin.dataupload.service;

import anyframe.common.Page;
import anyframe.core.generic.service.GenericService;
import anyframe.iam.admin.domain.DataUpload;
import anyframe.iam.admin.vo.CommonSearchVO;

public interface DataUploadService extends GenericService<DataUpload, String>{
	public DataUpload save(DataUpload dataUpload) throws Exception;
	
	public Page getList(CommonSearchVO searchVO) throws Exception;
	
	public void doSnapshot() throws Exception;
	
	public void applyExcel(String fileId) throws Exception;
}
