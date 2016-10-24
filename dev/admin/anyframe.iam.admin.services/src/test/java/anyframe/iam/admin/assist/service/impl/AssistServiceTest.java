package anyframe.iam.admin.assist.service.impl;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import anyframe.iam.admin.assist.service.AssistService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/context-test-all-*" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class AssistServiceTest {

	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;
	
	@Autowired
	@Qualifier("assistService")
	AssistService assistService;
	
	@Test
	public void testSave() throws Exception{
		
		List<Map<String, Object>> resourceMapList = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> resourceMap = new HashMap<String, Object>();
		resourceMap.put("beanid", "codeService");
		resourceMap.put("systemName", "sample1");
		resourceMapList.add(resourceMap);
		assistService.save(resourceMapList);
		assertNotNull(resourceMap);
		assertNotNull(resourceMapList);
	}
}
