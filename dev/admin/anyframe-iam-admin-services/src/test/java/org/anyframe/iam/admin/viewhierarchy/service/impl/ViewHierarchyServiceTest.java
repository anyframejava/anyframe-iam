package org.anyframe.iam.admin.viewhierarchy.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.anyframe.iam.admin.domain.ViewHierarchy;
import org.anyframe.iam.admin.domain.ViewHierarchyId;
import org.anyframe.iam.admin.viewhierarchy.service.ViewHierarchyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/context-test-all-*" })
public class ViewHierarchyServiceTest {
	
	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;
	
	@Autowired
	@Qualifier("viewHierarchyService")
	ViewHierarchyService viewHierarchyService;
	
	public ViewHierarchy makeDomain(){
		ViewHierarchy viewHierarchy = new ViewHierarchy();
		ViewHierarchyId viewHierarchyId = new ViewHierarchyId();
		
		viewHierarchyId.setChildView("addProduct");
		viewHierarchyId.setParentView("testUser");
		
		viewHierarchy.setId(viewHierarchyId);
		
		return viewHierarchy;
	}
	
	@Test
	public void testSave() throws Exception{
		ViewHierarchy domain = makeDomain();
		
		ViewHierarchy resultDomain = viewHierarchyService.save(domain);
		
		assertNotNull(resultDomain);
		assertEquals(domain.getId().getChildView(), resultDomain.getId().getChildView());
		assertEquals(domain.getId().getParentView(), resultDomain.getId().getParentView());
	}
}
