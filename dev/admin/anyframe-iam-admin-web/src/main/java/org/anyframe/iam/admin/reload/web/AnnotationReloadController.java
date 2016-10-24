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

package org.anyframe.iam.admin.reload.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.anyframe.iam.core.reload.IResourceReloadService;
import org.anyframe.pagination.Page;
import org.anyframe.util.properties.PropertiesService;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Annotation Reload Controller
 * @author jongpil.park
 * 
 */
@Controller
public class AnnotationReloadController {
	
	@Resource(name = "propertiesService")
	PropertiesService propertiesService;

	/**
	 * make bean list that matches the given bean ID when application reload
	 * @param request HttpServletRequest object
	 * @param beanid Bean ID
	 * @param reloadmaps Reload Map
	 * @param reloadtimes Reload Times
	 * @param model Modal object to add attributes
	 * @return jsonView
	 * @throws Exception fail to reload
	 */
	@RequestMapping("/admin/reload/resourceReload.do")
	public String resourceReload(
			HttpServletRequest request, 
			@RequestParam(value = "beanid") String beanid,
			@RequestParam(value = "reloadmaps", required = false) String reloadmaps,
			@RequestParam(value = "reloadtimes", required = false) String reloadtimes, 
			Model model) throws Exception {
		boolean result = false;

		WebApplicationContext context = RequestContextUtils.getWebApplicationContext(request);
		IResourceReloadService resourceReloadServiceClient = (IResourceReloadService) context.getBean(beanid);

		result = resourceReloadServiceClient.resourceReload(reloadmaps, reloadtimes);

		model.addAttribute("result", result);

		return "jsonView";
	}
	
	/**
	 * make bean list that matches the given bean id when application reload
	 * @param request HttpServletRequest 
	 * @param beanid list of bean id
	 * @param reloadmaps value of reloadmaps check box
	 * @param model Model object
	 * @return jsonView
	 * @throws Exception fail to reload
	 */
	@RequestMapping("/admin/reload/reloadMaps.do")
	public String reloadMaps(
			HttpServletRequest request,
			@RequestParam(value = "beanid") String[] beanid,
			@RequestParam(value = "reloadmaps") String reloadmaps,
			Model model) throws Exception{
		boolean result = false;

		WebApplicationContext context = RequestContextUtils.getWebApplicationContext(request);
		IResourceReloadService resourceReloadServiceClient = null;
		
		for(int i = 0 ; i < beanid.length ; i++){
			resourceReloadServiceClient = (IResourceReloadService) context.getBean(beanid[i]);
			result = resourceReloadServiceClient.resourceReload(reloadmaps, "");
			if(!result)
				break;
		}
		
		model.addAttribute("result", result);
		return "jsonView";
	}
	
	/**
	 * make bean list that matches the given bean id when application reload
	 * @param request HttpServletRequest
	 * @param beanid list of bean id
	 * @param reloadtimes value of reloadtimes check box
	 * @param model Model object
	 * @return jsonView
	 * @throws Exception fail to reload
	 */
	@RequestMapping("/admin/reload/reloadTimes.do")
	public String reloadTimes(
			HttpServletRequest request,
			@RequestParam(value = "beanid") String[] beanid,
			@RequestParam(value = "reloadtimes") String reloadtimes,
			Model model) throws Exception{
		boolean result = false;

		WebApplicationContext context = RequestContextUtils.getWebApplicationContext(request);
		IResourceReloadService resourceReloadServiceClient = null;
		
		for(int i = 0 ; i < beanid.length ; i++){
			resourceReloadServiceClient = (IResourceReloadService) context.getBean(beanid[i]);
			result = resourceReloadServiceClient.resourceReload("", reloadtimes);
			if(result)
				break;
		}
		
		model.addAttribute("result", result);
		return "jsonView";
	}

	/**
	 * make bean url list when target application reload
	 * @param request HttpServletRequest
	 * @param model Model object to add attributes
	 * @return move to "/reload/reloadlis"
	 * @throws Exception fail to make list
	 */
	@RequestMapping("/admin/reload/reloadlist.do")
	public String list(HttpServletRequest request, Model model) throws Exception {
		WebApplicationContext context = RequestContextUtils.getWebApplicationContext(request);

		String[] webBeanNames = context.getBeanDefinitionNames();

		List<Map<String, String>> beanList = new ArrayList<Map<String, String>>();
		Map<String, String> beanMap = new HashMap<String, String>();

		for (int i = 0; i < webBeanNames.length; i++) {
			Object bean = context.getBean(webBeanNames[i]);

			if (bean instanceof IResourceReloadService) {
				beanMap = new HashMap<String, String>();

				HttpInvokerProxyFactoryBean proxyBean = (HttpInvokerProxyFactoryBean) context.getBean("&"
						+ webBeanNames[i]);

				beanMap.put("beanId", webBeanNames[i]);
				beanMap.put("serverUrl", proxyBean.getServiceUrl());

				beanList.add(beanMap);
			}
		}

		if (beanList != null) {
			model.addAttribute("beanlist", beanList);
		}

		return "/reload/reloadlist";
	}
	
	/**
	 * make json data that includes system name, bean id and serverURL information.
	 * @param request HttpServletRequest
	 * @param model Model object
	 * @return jsonView
	 * @throws Exception fail to make json data
	 */
	@RequestMapping("/admin/reload/listData.do")
	public String listData(HttpServletRequest request, Model model) throws Exception{
		WebApplicationContext context = RequestContextUtils.getWebApplicationContext(request);
		int j = 0;
		String[] systemName = propertiesService.getStringArray("SYSTEM_NAMES");
		
		String[] webBeanNames = context.getBeanDefinitionNames();
		List<Map<String, String>> beanList = new ArrayList<Map<String, String>>();
		Map<String, String> beanMap = new HashMap<String, String>();
		
		for (int i = 0; i < webBeanNames.length; i++) {
			Object bean = context.getBean(webBeanNames[i]);

			if (bean instanceof IResourceReloadService) {
				beanMap = new HashMap<String, String>();

				HttpInvokerProxyFactoryBean proxyBean = (HttpInvokerProxyFactoryBean) context.getBean("&"
						+ webBeanNames[i]);

				beanMap.put("systemName", systemName[j++]);
				beanMap.put("beanId", webBeanNames[i]);
				beanMap.put("serverUrl", proxyBean.getServiceUrl());

				beanList.add(beanMap);
			}
		}
		
		Page resultPage = null;
		if(beanList != null){
			resultPage = new Page(beanList, 1, beanList.size(), 1, 10);
		}
		
		model.addAttribute("page", resultPage.getCurrentPage() + "");
		model.addAttribute("total", resultPage.getMaxPage() + "");
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());
		
		return "jsonView";
	}
}
