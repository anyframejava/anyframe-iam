package org.anyframe.iam.admin.dataupload.service;

import org.anyframe.generic.service.GenericService;
import org.anyframe.iam.admin.domain.DataUpload;
import org.anyframe.iam.admin.vo.CommonSearchVO;
import org.anyframe.pagination.Page;

public interface DataUploadService extends GenericService<DataUpload, String>{
	public DataUpload save(DataUpload dataUpload) throws Exception;
	
	public Page getList(CommonSearchVO searchVO) throws Exception;
	
	public void doSnapshot() throws Exception;
	
	public void applyExcel(String fileId) throws Exception;
}
