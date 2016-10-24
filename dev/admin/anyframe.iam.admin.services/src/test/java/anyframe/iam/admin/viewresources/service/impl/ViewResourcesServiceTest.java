/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package anyframe.iam.admin.viewresources.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import anyframe.common.Page;
import anyframe.common.exception.BaseException;
import anyframe.iam.admin.domain.IamTree;
import anyframe.iam.admin.domain.TempViewResources;
import anyframe.iam.admin.domain.ViewHierarchy;
import anyframe.iam.admin.domain.ViewHierarchyId;
import anyframe.iam.admin.domain.ViewResource;
import anyframe.iam.admin.viewresources.service.ViewResourcesService;
import anyframe.iam.admin.vo.ViewResourceSearchVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/context-test-all-*" })
//@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
//@Transactional
public class ViewResourcesServiceTest {

	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	@Autowired
	@Qualifier("viewResourcesService")
	ViewResourcesService viewResourcesService;

	// make domain
	public ViewResource makeDomain() {
		ViewResource domain = new ViewResource();
		domain.setViewResourceId("addUser");
		domain.setViewName("addUser");
		domain.setCategory("User Mgmt.");
		domain.setDescription("사용자 추가");
		domain.setViewInfo("사용자 추가");
		domain.setViewType("Button");
		domain.setVisible("Y");
		domain.setSystemName("SAMPLE");
		
		ViewHierarchyId viewHierarchyId = new ViewHierarchyId();
		ViewHierarchy viewHierarchy = new ViewHierarchy();
		
		viewHierarchyId.setChildView("listTransaction");
		viewHierarchyId.setParentView(domain.getViewResourceId());
		viewHierarchy.setId(viewHierarchyId);
		
		Set<ViewHierarchy> parentView = new HashSet<ViewHierarchy>();
		
		parentView.add(viewHierarchy);
		
		domain.setViewHierarchiesForParentView(parentView);

		return domain;
	}
	
	@Test
	public void testSave() throws Exception{
		ViewResource domain = makeDomain();
		
		viewResourcesService.save(domain);
		
		ViewResource resultDomain = viewResourcesService.get(domain.getViewResourceId());
		
		assertNotNull(resultDomain);
		assertEquals(domain.getViewResourceId(), resultDomain.getViewResourceId());
		assertEquals("addUser", resultDomain.getViewResourceId());
	}

	@Test
	public void testGetList() throws Exception {

		// make domain
		ViewResource domain = makeDomain();

		// save
		viewResourcesService.save(domain);

		// search
		ViewResourceSearchVO searchVO = new ViewResourceSearchVO();
		searchVO.setSearchCondition("viewResourceId");
		searchVO.setSearchKeyword("addUser");
		searchVO.setSystemName("SAMPLE");
		Page resultPage = viewResourcesService.getList(searchVO);

		// check
		assertNotNull(resultPage);
		assertTrue(resultPage.getSize() > 0);
		assertEquals(1, resultPage.getSize());

		Iterator iter = resultPage.getList().iterator();
		HashMap resultDomain = (HashMap) iter.next();
		assertEquals(domain.getViewResourceId(), resultDomain.get("viewResourceId"));
	}

	@Test
	public void testDelete() throws Exception {

		// make domain
		ViewResource domain = makeDomain();

		// save
		viewResourcesService.save(domain);

		// delete
		String[] viewResourceIds = new String[1];
		viewResourceIds[0] = "addUser";
		viewResourcesService.delete(viewResourceIds);

		// check
		try {
			viewResourcesService.get("addUser");
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.ViewResource' object with id '" + domain.getViewResourceId()
							+ "' not found", e.getMessage());
		}
	}

	@Test
	public void testUpdate() throws Exception {

		// make domain
		ViewResource domain = makeDomain();

		// save
		ViewResource updatedDomain = viewResourcesService.save(domain);

		// update
		updatedDomain.setCategory("사용자관리");
		updatedDomain.setViewName("사용자추가");
		viewResourcesService.update(updatedDomain);

		// check
		ViewResource resultDomain = viewResourcesService.get(updatedDomain.getViewResourceId());
		assertEquals(resultDomain.getViewName(), updatedDomain.getViewName());
		assertEquals(resultDomain.getCategory(), updatedDomain.getCategory());

	}
	
	@Test
	public void testGetRootNodeOfViews() throws Exception{
		List<IamTree> rootNode = viewResourcesService.getRootNodeOfViews();
		assertNotNull(rootNode);
		assertEquals("addProduct", rootNode.get(0).getId());
	}
	
	@Test
	public void testGetViewTree() throws Exception{
		
		// make domain
		ViewResource domain = makeDomain();
		
		// save
		ViewResource savedViewResource = viewResourcesService.save(domain);
		
		// get view tree
		List<IamTree> resultList = viewResourcesService.getViewTree("listTransaction");
		
		System.out.println(resultList.size());
		assertNotNull(resultList);
		assertTrue(resultList.size() > 0);
		assertEquals("addUser", resultList.get(0).getId());
		
	}

	
	@Test
	public void testMakeAllTempViewList() throws Exception{
		List<TempViewResources> resultList = viewResourcesService.makeAllTempViewList();
		assertNotNull(resultList);
		assertTrue(resultList.size() > 0);
		System.out.println(resultList.size());
	}
	
//	@Test
//	public void testRemoveAllRoles() throws Exception{
//		viewResourcesService.removeAllViewResources();
//
//		ViewResourceSearchVO searchVO = new ViewResourceSearchVO();
//		Page resultPage = viewResourcesService.getList(searchVO);
//
//		assertTrue(resultPage.getSize() == 0);
//		
//	}
	
	@Test
	public void testSaveList() throws Exception{
//		viewResourcesService.removeAllViewResources();
		
		TempViewResources tv1 = new TempViewResources();
		
		tv1.setViewResourceId("tempView1");
		tv1.setViewName("tempView1");
		tv1.setViewInfo("tempView1");
		tv1.setViewType("button");
		tv1.setVisible("Y");
		tv1.setCategory("tempView1");
		tv1.setDescription("tempView1");
		tv1.setRefId("ROLE_USER");
		tv1.setRefType("ROLE");
		tv1.setPermissions("READ");
		tv1.setSystemName("sample");
		
		TempViewResources tv2 = new TempViewResources();
		
		tv2.setViewResourceId("tempView2");
		tv2.setViewName("tempView2");
		tv2.setViewInfo("tempView2");
		tv2.setViewType("button");
		tv2.setVisible("Y");
		tv2.setCategory("tempView2");
		tv2.setDescription("tempView2");
		tv2.setRefId("user");
		tv2.setRefType("USER");
		tv2.setPermissions("READ,WRITE");
		tv2.setSystemName("sample");
		tv2.setParentView("tempView1");
		
		List viewList = new ArrayList();
		viewList.add(tv1);
		viewList.add(tv2);
		
		viewResourcesService.save(viewList);
		
		List resultList = viewResourcesService.getParentsViewIds(tv2.getViewResourceId());
		assertTrue(resultList.size() > 0);
		assertEquals(resultList.get(0), tv2.getParentView());
		
	}
	
}
