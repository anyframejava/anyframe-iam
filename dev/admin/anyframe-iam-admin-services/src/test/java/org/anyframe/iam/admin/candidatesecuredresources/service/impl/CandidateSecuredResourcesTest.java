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

package org.anyframe.iam.admin.candidatesecuredresources.service.impl;

import static org.junit.Assert.assertEquals;

import javax.sql.DataSource;

import org.anyframe.iam.admin.candidatesecuredresources.service.CandidateSecuredResourcesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/context-test-all-*" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class CandidateSecuredResourcesTest {

	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;

	@Autowired
	@Qualifier("candidateSecuredResourcesService")
	CandidateSecuredResourcesService candidateSecuredResourcesService;

	@Test
	public void testGetPackagesList() throws Exception {
		String keyword = "com.sds.emp.user";
		String result = candidateSecuredResourcesService.getPackagesList(keyword);
		String expect = "com.sds.emp.user.service\n";
		assertEquals(expect, result);
	}

	@Test
	public void testGetClassesList() throws Exception {
		String keyword = "User";
		String packages = "com.sds.emp.user.service";
		String result = candidateSecuredResourcesService.getClassesList(keyword, packages);
		String expect = "UserService\n";
		assertEquals(expect, result);
	}

	@Test
	public void testMethodList() throws Exception {
		String keyword = "find";
		String packages = "com.sds.emp.user.service";
		String classes = "UserService";
		String result = candidateSecuredResourcesService.getMethodList(keyword, packages, classes);
		String expected = "findUser\n" + "findUserList\n";
		assertEquals(expected, result);
	}

	@Test
	public void testPointCutList() throws Exception {
		String keyword = "com.sds.emp.sales.service.CategoryService";
		String result = candidateSecuredResourcesService.getPointCutList(keyword);
		String expected = "com.sds.emp.sales.service.CategoryService.createCategory\n"
				+ "com.sds.emp.sales.service.CategoryService.findCategory\n"
				+ "com.sds.emp.sales.service.CategoryService.findCategoryList\n"
				+ "com.sds.emp.sales.service.CategoryService.findCategoryListAll\n"
				+ "com.sds.emp.sales.service.CategoryService.findDropDownCategoryList\n"
				+ "com.sds.emp.sales.service.CategoryService.processAll\n"
				+ "com.sds.emp.sales.service.CategoryService.removeCategory\n"
				+ "com.sds.emp.sales.service.CategoryService.updateCategory\n";
		assertEquals(expected, result);

	}

	@Test
	public void testGetRequestMappingList() throws Exception {
		String keyword = "/empadduser";
		String result = candidateSecuredResourcesService.getRequestMappingList(keyword);
		String expected = "/empadduser.do\n" + "/empadduserview.do\n";
		assertEquals(expected, result);
	}

	@Test
	public void testGetParameterList() throws Exception {
		String keyword = "abc";
		String url = "/empadduser";
		String result = candidateSecuredResourcesService.getParameterList(keyword, url);
		String expected = "";
		assertEquals(expected, result);
	}

	@Test
	public void testCheckMatched() throws Exception {

		// check URL type : In Case Matched Pattern
		String urlKeyword = "\\A/empaddproductview\\.do.*\\Z";
		String urlResourceType = "url";

		boolean isMatchedURL = candidateSecuredResourcesService.checkMatched(urlKeyword, urlResourceType);
		assertEquals(true, isMatchedURL);

		// check URL type : In Case Unmatched Pattern
		String urlUnMatchedKeyword = "\\Aempview\\.do.*\\Z";
		String urlUnMatchedResourceType = "url";

		boolean isUnmatchedURL = candidateSecuredResourcesService.checkMatched(urlUnMatchedKeyword,
				urlUnMatchedResourceType);
		assertEquals(false, isUnmatchedURL);

		// check PointCut type : In Case Matched Pattern
		String pointCutKeyword = "execution(* com.sds.emp.sales..*ServiceImpl.findCategoryList(..))";
		String pointCutType = "pointcut";

		boolean isMatchedPointCut = candidateSecuredResourcesService.checkMatched(pointCutKeyword, pointCutType);
		assertEquals(true, isMatchedPointCut);

		// check PointCut type : In Case Unmatched Pattern
		String pointCutUnmatchedKeyword = "excution( com.sds.emp.sles..ServiceImpl.finCategory(..))";
		String pointCutUnmatchedType = "pointcut";

		boolean isUnmatchedPointCut = candidateSecuredResourcesService.checkMatched(pointCutUnmatchedKeyword,
				pointCutUnmatchedType);
		assertEquals(false, isUnmatchedPointCut);

		// check Method type : In Case Matched Pattern
		String methodKeyword = "com.sds.emp.sales.service.CategoryService.createCategory";
		String methodType = "method";

		boolean isMatchedMethod = candidateSecuredResourcesService.checkMatched(methodKeyword, methodType);
		assertEquals(true, isMatchedMethod);

		// check Method type : In Case Unmatched Pattern
		String methodUnmatchedKeyword = "com.ds.ep.sales.service.CategorySevice.creaCategory";
		String methodUnmatchedType = "method";

		boolean isUnmatchedMethod = candidateSecuredResourcesService.checkMatched(methodUnmatchedKeyword,
				methodUnmatchedType);
		assertEquals(false, isUnmatchedMethod);

	}
	
	@Test
	public void estFindMethodParam() throws Exception{
		String beanid = candidateSecuredResourcesService.findMethodParam();
		assertEquals("", beanid);
	}
}
