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

package anyframe.iam.admin.candidatesecuredresources.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import anyframe.iam.admin.candidatesecuredresources.service.CandidateSecuredResourcesService;

/**
 * Annotation Candidate Secured Resources Controller
 * @author youngmin.jo
 * 
 */
@Controller
public class AnnotationCandidateSecuredResourcesController {

	@Resource(name = "candidateSecuredResourcesService")
	private CandidateSecuredResourcesService candidateSecuredResourcesService;

	/**
	 * find list of package name that matches the given keyword in
	 * Candidate_Secured_Resources table
	 * @param keyword keyword
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param model Model
	 * @throws Exception fail to find list
	 */
	@RequestMapping("/candidateSecuredResources/getPackagesList.do")
	public void getPackagesList(
			@RequestParam(value = "q", required = false) String keyword,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session,
			Model model) throws Exception {

		String systemName = (String) session.getAttribute("systemName");
		
		String resultList = candidateSecuredResourcesService.getPackagesList(keyword, systemName);
		response.getOutputStream().print(resultList);
	}

	/**
	 * find list of class name that matches the given keyword and package name
	 * in Candidate_Secured_Resources table
	 * @param keyword keyword
	 * @param packages package name
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param model Model
	 * @throws Exception fail to find list
	 */
	@RequestMapping("/candidateSecuredResources/getClassesList.do")
	public void getClassesList(
			@RequestParam(value = "q", required = false) String keyword,
			@RequestParam(value = "packages", required = false) String packages, 
			HttpServletRequest request,
			HttpServletResponse response, 
			HttpSession session,
			Model model) throws Exception {

		String systemName = (String) session.getAttribute("systemName");
		
		String resultList = candidateSecuredResourcesService.getClassesList(keyword, packages, systemName);
		response.getOutputStream().print(resultList);
	}

	/**
	 * find list of method name that matches the given keyword, package name and
	 * class name in Candidate_Secured_Resources table
	 * @param keyword keyword
	 * @param packages package name
	 * @param classes class name
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param model Model
	 * @throws Exception fail to find list
	 */
	@RequestMapping("/candidateSecuredResources/getMethodList.do")
	public void getMethodList(
			@RequestParam(value = "q", required = false) String keyword,
			@RequestParam(value = "packages", required = false) String packages,
			@RequestParam(value = "classes", required = false) String classes, 
			HttpServletRequest request,
			HttpServletResponse response, 
			HttpSession session,
			Model model) throws Exception {
		
		String systemName = (String) session.getAttribute("systemName");
		
		String resultList = candidateSecuredResourcesService.getMethodList(keyword, packages, classes, systemName);
		response.getOutputStream().print(resultList);
	}

	/**
	 * find list of URL that matches the given keyword in
	 * Candidate_Secured_Resources table
	 * @param keyword keyword
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param model Model
	 * @throws Exception fail to find list
	 */
	@RequestMapping("/candidateSecuredResources/getUrlMappingList.do")
	public void getUrlMappingList(
			@RequestParam(value = "q", required = false) String keyword,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session,
			Model model) throws Exception {

		String systemName = (String) session.getAttribute("systemName");
		
		String resultList = candidateSecuredResourcesService.getRequestMappingList(keyword, systemName);
		response.getOutputStream().print(resultList);
	}

	/**
	 * find list of parameter name that matches the given keyword and URL in
	 * Candidate_Secured_Resources table
	 * @param keyword keyword
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param model Model
	 * @throws Exception fail to find list
	 */
	@RequestMapping("/candidateSecuredResources/getParameterList.do")
	public void getParameterList(
			@RequestParam(value = "q", required = false) String keyword,
			@RequestParam(value = "requestMapping", required = false) String requestMapping,
			HttpServletRequest request, 
			HttpServletResponse response,
			HttpSession session,
			Model model) throws Exception {

		String systemName = (String)session.getAttribute("systemName");
		
		String resultList = candidateSecuredResourcesService.getParameterList(keyword, requestMapping, systemName);
		response.getOutputStream().print(resultList);
	}

	/**
	 * find list of point-cut name that matches the given keyword in
	 * Candidate_Secured_Resources table
	 * @param keyword keyword
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param model Model
	 * @throws Exception fail to find list
	 */
	@RequestMapping("/candidateSecuredResources/getPointCutList.do")
	public void getPointCutList(
			@RequestParam(value = "q", required = false) String keyword,
			HttpServletRequest request, 
			HttpServletResponse response, 
			HttpSession session,
			Model model) throws Exception {

		String systemName = (String)session.getAttribute("systemName");
		
		String resultList = candidateSecuredResourcesService.getPointCutList(keyword, systemName);
		response.getOutputStream().print(resultList);
	}
}
