package org.anyframe.iam.admin.roleshierarchy.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.anyframe.iam.admin.domain.RolesHierarchy;
import org.anyframe.iam.admin.domain.RolesHierarchyId;
import org.anyframe.iam.admin.roleshierarchy.service.RolesHierarchyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/context-test-all-*" })
public class RolesHierarchyServiceTest {

	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;
	
	@Autowired
	@Qualifier("rolesHierarchyService")
	RolesHierarchyService rolesHierarchyService;
	
	public RolesHierarchy makeDomain(){
		RolesHierarchy rolesHierarchy = new RolesHierarchy();
		RolesHierarchyId rolesHierarchyId = new RolesHierarchyId();
		
		rolesHierarchyId.setChildRole("ROLE_USER");
		rolesHierarchyId.setParentRole("ROLE_TEST");
		
		rolesHierarchy.setId(rolesHierarchyId);
		return rolesHierarchy;
	}
	
	@Test
	public void testSave() throws Exception{
		RolesHierarchy domain = makeDomain();
		
		RolesHierarchy resultDomain = rolesHierarchyService.save(domain);
		assertNotNull(resultDomain);
		assertEquals(domain.getId().getChildRole(), resultDomain.getId().getChildRole());
		assertEquals(domain.getId().getParentRole(), resultDomain.getId().getParentRole());
	}
}
