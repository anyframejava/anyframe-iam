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

package anyframe.iam.admin.viewresources.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import anyframe.common.Page;
import anyframe.common.util.StringUtil;
import anyframe.core.idgen.IIdGenerationService;
import anyframe.iam.admin.common.IAMException;
import anyframe.iam.admin.common.web.JsonError;
import anyframe.iam.admin.domain.Attributes;
import anyframe.iam.admin.domain.Data;
import anyframe.iam.admin.domain.IamTree;
import anyframe.iam.admin.domain.JSTreeNode;
import anyframe.iam.admin.domain.ViewHierarchy;
import anyframe.iam.admin.domain.ViewHierarchyId;
import anyframe.iam.admin.domain.ViewResource;
import anyframe.iam.admin.viewresources.service.ViewResourcesService;
import anyframe.iam.admin.vo.ViewResourceSearchVO;

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
	
	@Resource(name = "idGenerationServiceView")
	private IIdGenerationService idGenerationServiceView;

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
	public String listData(ViewResourceSearchVO searchVO, Model model)
			throws Exception {
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
			@RequestParam(value = "viewResourceId", required = false) String viewResourceId,
			Model model) throws Exception {
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
			Model model) throws Exception {

		model.addAttribute("viewResources", new ViewResource());
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
	public String add(ViewResource viewResource, ViewHierarchyId viewHierarchyId) throws Exception {

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
	public String update(ViewResource vr) throws Exception {

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

	@RequestMapping("/viewresources/viewlist.do")
	public String list(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "/viewresources/viewlist";
	}

	@RequestMapping("/viewresources/listTreeData.do")
	@SuppressWarnings("unchecked")
	public String listViewData(@RequestParam("id") String id, 
			@RequestParam(value = "viewName", required = false) String viewName,
			@RequestParam(value = "searchClickYn") String searchClick, Model model)	throws Exception {
		List<IamTree> list = null;
		ArrayList<JSTreeNode> listNode = new ArrayList<JSTreeNode>();
		JSTreeNode node = null;
		Attributes attribute = null;
		Data data = null;
		
		if("N".equals(searchClick)){
			
			if (id.equals("0")) {
				listNode = makeRootNode();
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
					listNode = makeRootNode();
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
				listNode = makeRootNode();
		}
		
		model.addAttribute(listNode);
		
		return "jsonView";
	}

	private ArrayList<JSTreeNode> makeRootNode() throws Exception {
		List<IamTree> list = null;
		ArrayList<JSTreeNode> listNode = new ArrayList<JSTreeNode>();
		JSTreeNode node = null;
		Attributes attribute = null;
		Data data = null;

		list = viewResourcesService.getRootNodeOfViews();
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
	
	@RequestMapping("/viewresources/getViewResourceId.do")
	public String getViewResourceId(Model model) throws Exception {
		String viewResourceId = idGenerationServiceView.getNextStringId();

		model.addAttribute("viewResourceId", viewResourceId);

		return "jsonView";
	}
	
	@RequestMapping("/viewresources/getViewNameList.do")
	public void getViewNameList(@RequestParam(value = "q", required = false) String keyword, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		keyword = new String(keyword.getBytes("8859_1"), "utf-8");
		String resultList = viewResourcesService.getViewNameList(keyword);
		response.getOutputStream().print(new String(resultList.getBytes("utf-8"), "8859_1"));
	}
	
	@JsonError
	@RequestMapping("/viewresources/remove.do")
	public String remove(ViewResource viewResource) throws Exception{
		
		viewResourcesService.remove(viewResource.getViewResourceId());
		
		return "jsonView";
	}
}
