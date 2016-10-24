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

package org.anyframe.iam.admin.viewresources.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.anyframe.iam.admin.common.IAMException;
import org.anyframe.iam.admin.common.web.JsonError;
import org.anyframe.iam.admin.domain.Attributes;
import org.anyframe.iam.admin.domain.Data;
import org.anyframe.iam.admin.domain.IamTree;
import org.anyframe.iam.admin.domain.JSTreeNode;
import org.anyframe.iam.admin.domain.ViewHierarchy;
import org.anyframe.iam.admin.domain.ViewHierarchyId;
import org.anyframe.iam.admin.domain.ViewResource;
import org.anyframe.iam.admin.viewresources.service.ViewResourcesService;
import org.anyframe.iam.admin.vo.ViewResourceSearchVO;
import org.anyframe.idgen.IdGenService;
import org.anyframe.pagination.Page;
import org.anyframe.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

/**
 * Annotation ViewResources Controller
 * 
 * @author sungryong.kim
 * 
 */
@Controller
public class AnnotationViewResourcesController {

	@Resource(name = "viewResourcesService")
	private ViewResourcesService viewResourcesService;
	
	@Resource(name = "idGenServiceView")
	private IdGenService idGenServiceView;

	/**
	 * move to view resources List page
	 * 
	 * @return move to view resources list page
	 * @throws Exception
	 *             fail to move to the page
	 */
	@RequestMapping("/viewresources/list.do")
	public String list() throws Exception {
		return "/viewresources/viewresourcelist";
	}

	/**
	 * make data of view resource list
	 * 
	 * @param searchVO
	 *            an object that contains search conditions
	 * @param model
	 *            Model object to add attributes
	 * @return jsonView
	 * @throws Exception
	 *             fail to make data
	 */
	@JsonError
	@RequestMapping("/viewresources/listData.do")
	public String listData(ViewResourceSearchVO searchVO, Model model, HttpSession session)
			throws Exception {
		String systemName = (String) session.getAttribute("systemName");
		searchVO.setSystemName(systemName);
		Page resultPage = viewResourcesService.getList(searchVO);
		model.addAttribute("page", resultPage.getCurrentPage() + "");
		model.addAttribute("total", resultPage.getMaxPage() + "");
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());

		return "jsonView";
	}

	/**
	 * get a ViewResources object that matches the given ID
	 * 
	 * @param viewResourceId
	 *            View Resources ID
	 * @param model
	 *            Model object to add attributes
	 * @return move to "/viewresources/viewresourcedetail"
	 * @throws Exception
	 *             faii to get data
	 */
	@RequestMapping("/viewresources/get.do")
	public String get(
			HttpSession session,
			@RequestParam(value = "viewResourceId", required = false) String viewResourceId,
			Model model) throws Exception {
		
		String[] systemName = new String[1];
		systemName[0] = (String) session.getAttribute("systemName");
		
		model.addAttribute("systemNames", systemName);
		
		if (!StringUtils.isBlank(viewResourceId)) {
			ViewResource gettedVR = viewResourcesService.get(viewResourceId);

			model.addAttribute("viewResources", gettedVR);
		} else{
			ViewResource newVR = new ViewResource();
			model.addAttribute("viewResources", newVR);
			
		}
		return "/viewresources/viewresourcedetail";
	}

	/**
	 * delete view resources data
	 * 
	 * @param viewResourceIds
	 *            array of view resource IDs
	 * @param status
	 *            SessionStatus object to prevent double submit
	 * @return move to "/viewresources/listData.do"
	 * @throws Exception
	 *             fail to delete data
	 */
	@JsonError
	@RequestMapping("/viewresources/delete.do")
	public String delete(
			@RequestParam("viewResourceIds") String[] viewResourceIds,
			@RequestParam(value = "parentViewResourceId", required = false) String parentViewResourceId,
			SessionStatus status) throws Exception {

		parentViewResourceId = StringUtil.null2str(parentViewResourceId);
		viewResourcesService.delete(viewResourceIds);
		status.setComplete();

		return "forward:/viewresources/listData.do?";
	}

	/**
	 * move to view resources adding page
	 * 
	 * @param searchVO
	 *            an object that contains search conditions
	 * @param model
	 *            Model object to add attributes
	 * @return move to "/viewresources/viewresourcedetail"
	 * @throws Exception
	 *             fail to move to the page
	 */
	@RequestMapping("/viewresources/addView.do")
	public String addView(
			@ModelAttribute("searchVO") ViewResourceSearchVO searchVO,
			Model model, HttpSession session) throws Exception {

		String[] systemName = new String[1];
		systemName[0] = (String) session.getAttribute("systemName");
		
		ViewResource viewResource = new ViewResource();
		
		model.addAttribute("systemNames", systemName);
		model.addAttribute("viewResources", viewResource);
		return "/viewresources/viewresourcedetail";
	}

	/**
	 * delete view resources data
	 * 
	 * @param resourceIds
	 *            array of view resources IDs
	 * @param status
	 *            SessionStatus object to block double submit
	 * @return move to "/viewresources/list.do"
	 * @throws Exception
	 *             fail to delete data
	 */
	@RequestMapping("/viewresources/deleteFromDetail.do")
	public String deleteFromDetail(
			@RequestParam("viewResourceId") String[] resourceIds,
			SessionStatus status) throws Exception {

		viewResourcesService.delete(resourceIds);
		return "forward:/viewresources/list.do";
	}

	/**
	 * add View Resource data
	 * 
	 * @param vr
	 *            ViewResource domain object
	 * @param bindingResult
	 *            an object to check input data with validation rules
	 * @param status
	 *            SessionStatus object to block double submit
	 * @return move to "/viewresources/viewresourcesdetail"
	 * @throws Exception
	 *             fail to add data
	 */
	@RequestMapping("/viewresources/add.do")
	public String add(ViewResource viewResource, ViewHierarchyId viewHierarchyId, HttpSession session) throws Exception {

		String systemName = (String) session.getAttribute("systemName");
		viewResource.setSystemName(systemName);
		String viewName = viewResource.getViewName();
		if(viewResourcesService.isExistName(viewName))
			viewResource.setViewName(viewResource.getViewResourceId() + " Name");
		
		ViewHierarchy viewHierarchy = new ViewHierarchy();
		viewHierarchy.setId(viewHierarchyId);
		
		Set<ViewHierarchy> parentView = new HashSet<ViewHierarchy>();
		
		parentView.add(viewHierarchy);
		
		viewResource.setViewHierarchiesForParentView(parentView);
		
		viewResourcesService.save(viewResource);
		
		return "forward:/viewresources/list.do";
	}

	/**
	 * update ViewResource data
	 * 
	 * @param vr
	 *            ViewResource domain object
	 * @param bindingResult
	 *            an object to check input data with validation rules
	 * @param status
	 *            SessionStatus object to block double submit
	 * @return move to "viewresources/viewresourcesdetail"
	 * @throws Exception
	 *             fail to update data
	 */
	@RequestMapping("/viewresources/update.do")
	public String update(ViewResource vr, HttpSession session) throws Exception {

		if("".equals(vr.getDescription()))
			vr.setDescription(viewResourcesService.get(vr.getViewResourceId()).getDescription());
		
		if("".equals(vr.getViewType()))
			vr.setViewType(viewResourcesService.get(vr.getViewResourceId()).getViewType());
		
		if("".equals(vr.getCategory()))
			vr.setCategory(viewResourcesService.get(vr.getViewResourceId()).getCategory());
		
		if("".equals(vr.getVisible()))
			vr.setVisible(viewResourcesService.get(vr.getViewResourceId()).getVisible());

		if("".equals(vr.getViewInfo()))
			vr.setViewInfo(viewResourcesService.get(vr.getViewResourceId()).getViewInfo());
		
		vr.setSystemName((String) session.getAttribute("systemName"));
		
		
		
		viewResourcesService.update(vr);

		return "forward:/viewresources/list.do";
	}

	/**
	 * move to ViewResouece Id checking page
	 * 
	 * @param viewResourceId
	 *            ViewResouece ID
	 * @param model
	 *            Model object to add attributes
	 * @return move to "viewresources/checkid"
	 * @throws Exception
	 *             fail to move to the page
	 */
	@RequestMapping("/viewresources/checkid.do")
	public String checkId(
			@RequestParam(value = "viewResourceId") String viewResourceId,
			Model model) throws Exception {
		viewResourceId = StringUtil.null2str(viewResourceId);
		model.addAttribute("viewResourceId", viewResourceId);
		return "/viewresources/checkid";
	}
	
	/**
	 * move to ViewResouece Name checking page
	 * 
	 * @param viewName
	 *            ViewResouece Name
	 * @param model
	 *            Model object to add attributes
	 * @return move to "viewresources/checkname"
	 * @throws Exception
	 *             fail to move to the page
	 */
	@RequestMapping("/viewresources/checkName.do")
	public String checkName(
			@RequestParam(value = "viewName") String viewName,
			Model model) throws Exception {
		viewName = StringUtil.null2str(viewName);
		model.addAttribute("viewName", viewName);
		return "/viewresources/checkname";
	}

	/**
	 * check ViewResource ID if it is duplicated
	 * 
	 * @param viewResourceId
	 *            ViewResource ID that want to be checked
	 * @param model
	 *            Model object to add attributes
	 * @return move to "viewresources/checkid"
	 * @throws Exception
	 *             fail to check Id
	 */
	@RequestMapping("/viewresources/duplicationconfirm.do")
	public String duplicationConfirm(
			@RequestParam(value = "viewResourceId", required = true) String viewResourceId,
			Model model) throws Exception {
		String newId = new String(viewResourceId.getBytes("8859_1"), "utf-8");
		if (!StringUtils.isBlank(newId)) {
			boolean exist = viewResourcesService.exists(newId);
			model.addAttribute("viewResourceId", viewResourceId);
			model.addAttribute("exist", exist);
		} else
			model.addAttribute("exist", true);
		return "/viewresources/checkid";
	}
	
	/**
	 * check ViewResource Name whether it is duplicated or not
	 * 
	 * @param viewName
	 *            View Name that want to be checked
	 * @param model
	 *            Model object to add attributes
	 * @return move to "viewresources/checkname"
	 * @throws Exception
	 *             fail to check name
	 */
	@RequestMapping("/viewresources/duplicationNameConfirm.do")
	public String duplicationNameConfirm(
			@RequestParam(value = "viewName", required = true) String viewName,
			Model model) throws Exception {
		String newName = new String(viewName.getBytes("8859_1"), "utf-8");
		if (!StringUtils.isBlank(newName)) {
			boolean exist = viewResourcesService.isExistName(newName);
			model.addAttribute("viewName", viewName);
			model.addAttribute("exist", exist);
		} else
			model.addAttribute("exist", true);
		return "/viewresources/checkname";
	}

	/**
	 * move to view list view
	 * @param request HttpServletRequest
	 * @param response HttpSErvletResponse
	 * @return move to "/viewresources/viewlist.jsp"
	 * @throws Exception fail to move
	 */
	@RequestMapping("/viewresources/viewlist.do")
	public String list(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "/viewresources/viewlist";
	}

	/**
	 * make tree data of view list
	 * @param session HttpSession
	 * @param id viewResourceId, if id is '0', it represents root node
	 * @param viewName view Name
	 * @param searchClick value of check box named searchClick
	 * @param model Model object
	 * @return jsonView
	 * @throws Exception fail to make data
	 */
	@RequestMapping("/viewresources/listTreeData.do")
	@SuppressWarnings("unchecked")
	public String listViewData(
			HttpSession session,
			@RequestParam("id") String id, 
			@RequestParam(value = "viewName", required = false) String viewName,
			@RequestParam(value = "searchClickYn") String searchClick, 
			Model model) throws Exception {
		
		List<IamTree> list = null;
		ArrayList<JSTreeNode> listNode = new ArrayList<JSTreeNode>();
		JSTreeNode node = null;
		Attributes attribute = null;
		Data data = null;
		String systemName = (String) session.getAttribute("systemName");
		
		if("N".equals(searchClick)){
			
			if (id.equals("0")) {
				listNode = makeRootNode(systemName);
			}
			else {
				list = viewResourcesService.getViewTree(id);
				
				for(int i = 0 ; i < list.size(); i++){
					IamTree tree = list.get(i);
					
					node = new JSTreeNode();
					data = new Data();
					attribute = new Attributes();
	
					data.setTitle(tree.getTitle());
					attribute.setId(tree.getId());
	
					node.setAttributes(attribute);
					node.setData(data);
					node.setState(tree.getState());
	
					listNode.add(node);
				}
			}
		}
		else if("Y".equals(searchClick)){
			String viewId = viewResourcesService.getViewResourceIdByViewName(viewName);
			
			if(!"".equals(viewId)){
				List<String> ids = viewResourcesService.getParentsViewIds(viewId);
				int size = ids.size();
				
				if(size == 0)
					listNode = makeRootNode(systemName);
				else{
					ArrayList<JSTreeNode>[] childNode = new ArrayList[size];
					
					for(int i = 0 ; i < size ; i++){
						list = viewResourcesService.getViewTree(ids.get(i));
						childNode[i] = new ArrayList<JSTreeNode>();
						
						for(int j = 0 ; j < list.size() ; j++){
							IamTree tree = list.get(j);
							
							node = new JSTreeNode();
							data = new Data();
							attribute = new Attributes();
							
							data.setTitle(tree.getTitle());
							attribute.setId(tree.getId());
							
							node.setAttributes(attribute);
							node.setData(data);
							node.setState(tree.getState());
							
							if(i != 0){
								if(tree.getId().equals(ids.get(i - 1))){
									node.setState("open");
									node.setChildren(childNode[i-1]);
								}
							}
							
							childNode[i].add(node);
						}
					}
					
					ViewResource rootNode = viewResourcesService.get(ids.get(size - 1));
					
					node = new JSTreeNode();
					data = new Data();
					attribute = new Attributes();
					
					data.setTitle(rootNode.getViewName());
					attribute.setId(rootNode.getViewResourceId());
					
					node.setAttributes(attribute);
					node.setData(data);
					node.setState("open");
					node.setChildren(childNode[size - 1]);
					
					listNode.add(node);
				}
			}
			
			else 
				listNode = makeRootNode(systemName);
		}
		
		model.addAttribute(listNode);
		
		return "jsonView";
	}

	/**
	 * find root node of ViewResource
	 * @param systemName system name
	 * @return ArrayList<JSTreeNode> root node
	 * @throws Exception fail to find root node
	 */
	private ArrayList<JSTreeNode> makeRootNode(String systemName) throws Exception {
		List<IamTree> list = null;
		ArrayList<JSTreeNode> listNode = new ArrayList<JSTreeNode>();
		JSTreeNode node = null;
		Attributes attribute = null;
		Data data = null;

		list = viewResourcesService.getRootNodeOfViewsWithSystemName(systemName);
		if (list.size() > 0) {

			IamTree rootNode = list.get(0);

			node = new JSTreeNode();
			data = new Data();
			attribute = new Attributes();

			data.setTitle(rootNode.getTitle());
			attribute.setId(rootNode.getId());

			node.setAttributes(attribute);
			node.setData(data);
			node.setState(rootNode.getState());

			listNode.add(node);

			return listNode;
		} else {
			throw new IAMException("Root node is not exist");
		}

	}
	
	/**
	 * get next view resource id
	 * @param model Model object
	 * @return jsonView
	 * @throws Exception fail to get next view resource id
	 */
	@RequestMapping("/viewresources/getViewResourceId.do")
	public String getViewResourceId(Model model) throws Exception {
		String viewResourceId = idGenServiceView.getNextStringId();

		model.addAttribute("viewResourceId", viewResourceId);

		return "jsonView";
	}
	
	/**
	 * find list of view resource name that matches the given keyword
	 * @param keyword keyword
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param session HttpSession
	 * @param model Model
	 * @throws Exception fail to find list
	 */
	@RequestMapping("/viewresources/getViewNameList.do")
	public void getViewNameList(
			@RequestParam(value = "q", required = false) String keyword, 
			HttpServletRequest request, 
			HttpServletResponse response,
			HttpSession session,
			Model model) throws Exception{
		
		String systemName = (String) session.getAttribute("systemName");
		
		keyword = new String(keyword.getBytes("8859_1"), "utf-8");
		String resultList = viewResourcesService.getViewNameListWithSystemName(keyword, systemName);
		response.getOutputStream().print(new String(resultList.getBytes("utf-8"), "8859_1"));
	}
	
	/**
	 * remove the selected view resource
	 * @param viewResource an object that want to be deleted
	 * @return jsonView
	 * @throws Exception fail to delete
	 */
	@JsonError
	@RequestMapping("/viewresources/remove.do")
	public String remove(ViewResource viewResource) throws Exception{
		
		viewResourcesService.remove(viewResource.getViewResourceId());
		
		return "jsonView";
	}
}
