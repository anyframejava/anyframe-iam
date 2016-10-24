package anyframe.iam.admin.dataupload.service.impl;

import static org.junit.Assert.assertEquals;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import anyframe.iam.admin.dataupload.service.DataUploadService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/context-test-all-*" })
public class DataUploadServiceTest {

	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	@Autowired
	@Qualifier("dataUploadService")
	DataUploadService dataUploadService;
	
	@Test
	public void dummyTest() throws Exception{
		assertEquals(0 , 0);
	}
//	
//	@Test
//	public void testDoSnapshot() throws Exception{
//		dataUploadService.doSnapshot();
//		assertEquals(0, 0);
//	}
//	
//	@Test
//	public void testApplyExcel() throws Exception{
//		dataUploadService.doSnapshot();
//		String fileId = "SNAPSHOT-00001";
//		dataUploadService.applyExcel(fileId);
//	}
	
}
