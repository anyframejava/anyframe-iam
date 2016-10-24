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
	
	// make another domain
	public ViewResource makeAnotherDomain() {
		ViewResource domain = new ViewResource();
		domain.setViewResourceId("addAnotherUser");
		domain.setViewName("addAnotherUser");
		domain.setCategory("User Mgmt.");
		domain.setDescription("다른 사용자 추가");
		domain.setViewInfo("다른 사용자 추가");
		domain.setViewType("Button");
		domain.setVisible("Y");
		domain.setSystemName("SAMPLE");
		
		ViewHierarchyId viewHierarchyId = new ViewHierarchyId();
		ViewHierarchy viewHierarchy = new ViewHierarchy();
		
		viewHierarchyId.setChildView("addUser");
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
		
		ViewResource anotherDomain = makeAnotherDomain();
		
		viewResourcesService.save(anotherDomain);
		
		ViewResource resultAnotherDomain = viewResourcesService.get(anotherDomain.getViewResourceId());
		
		assertNotNull(resultAnotherDomain);
		assertEquals(anotherDomain.getViewResourceId(), resultAnotherDomain.getViewResourceId());
		assertEquals("addAnotherUser", resultAnotherDomain.getViewResourceId());
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
	public void testMultiDelete() throws Exception{
		// make domain
		ViewResource domain = makeDomain();
		ViewResource anotherDomain = makeAnotherDomain();

		// save
		viewResourcesService.save(domain);
		viewResourcesService.save(anotherDomain);
		
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

		try {
			viewResourcesService.get("addAnotherUser");
			fail("ObjectRetrievalFailureException 이 발생해야합니다.");
		}
		catch (Exception e) {
			assertNotNull(e);
			assertTrue("Fail to check exception.", e instanceof BaseException);
			assertEquals("Fail to compare exception message.",
					"'class anyframe.iam.admin.domain.ViewResource' object with id '" + anotherDomain.getViewResourceId()
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
	public void testGetViewResourceIdByViewName() throws Exception{
		// make domain
		ViewResource domain = makeDomain();
		
		// save
		ViewResource savedViewResource = viewResourcesService.save(domain);
		
		String viewId = viewResourcesService.getViewResourceIdByViewName(domain.getViewName());
		
		assertNotNull(viewId);
		assertEquals(viewId, savedViewResource.getViewResourceId());
	}
	
	@Test
	public void testGetViewNameList() throws Exception{
		// make domain
		ViewResource domain = makeDomain();
		
		// save
		ViewResource savedViewResource = viewResourcesService.save(domain);
		
		String resultList = viewResourcesService.getViewNameList(savedViewResource.getViewName());
		assertNotNull(resultList);
		assertEquals(resultList, "");
	}
	
	@Test
	public void testGetViewNameListWithSystemName() throws Exception{
		// make domain
		ViewResource domain = makeDomain();
		
		// save
		ViewResource savedViewResource = viewResourcesService.save(domain);
		
		String resultList = viewResourcesService.getViewNameListWithSystemName(savedViewResource.getViewName(), savedViewResource.getSystemName());
		assertNotNull(resultList);
		assertEquals(resultList, domain.getViewName() + "\n");
	}
	
	@Test
	public void testGetRootNodeOfViewsWithSystemName() throws Exception{
		// make domain
		ViewResource domain = makeDomain();
		
		List<IamTree> list = viewResourcesService.getRootNodeOfViewsWithSystemName(domain.getSystemName());
		
		assertNotNull(list);
		assertTrue(list.size() > 0);
		assertEquals(list.get(0).getId(), "addProduct");
	}
	
	@Test
	public void testGetParentsViewIds() throws Exception{
		// make domain
		ViewResource domain = makeDomain();

		// save
		viewResourcesService.save(domain);
		
		List<String> ids = viewResourcesService.getParentsViewIds(domain.getViewResourceId());
		
		assertNotNull(ids);
		assertTrue(ids.size() > 0);
		assertEquals(ids.get(0), "listTransaction");
	}
	
}
