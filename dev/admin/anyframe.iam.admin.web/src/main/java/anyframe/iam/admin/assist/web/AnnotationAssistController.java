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

package anyframe.iam.admin.assist.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import anyframe.common.Page;
import anyframe.core.properties.IPropertiesService;
import anyframe.iam.admin.assist.service.AssistService;
import anyframe.iam.core.assist.IResourceGatherAssistService;

/**
 * Annotation Assist Controller
 * 
 * @author Jongpil Park
 * 
 */
@Controller
public class AnnotationAssistController {

	@Resource(name = "assistService")
	private AssistService assistService;

	@Resource(name = "propertiesService")
	private IPropertiesService propertiesService;

	/**
	 * collect information of target application
	 * 
	 * @return information of target application
	 * @throws Exception
	 *             fail to collect data
	 */

	// @RequestMapping("/admin/assist/resourceAssist.do")
	// public String reloadResources(Model model) throws Exception {
	// boolean result = false;
	// IResourceGatherAssistService gatherServiceClient = null;
	// @SuppressWarnings("unchecked")
	// List<Map<String, Object>> resourceMapList =
	// gatherServiceClient.getTargetApplicationResourceInformation();
	// result = assistService.save(resourceMapList);
	//
	// model.addAttribute("msg", result);
	//
	// return "jsonView";
	// }

	/**
	 * collect information of selected target application
	 * @param beanid list of bean ids
	 * @param request HttpServletRequest
	 * @param model Model object
	 * @return information of target application
	 * @throws Exception fail to collect data
	 */
	@RequestMapping("/admin/assist/resourceGatehr.do")
	public String reloadResources(
			@RequestParam(value = "beanid") String[] beanid,
			HttpServletRequest request, Model model) throws Exception {
		boolean result = false;

		WebApplicationContext context = RequestContextUtils
				.getWebApplicationContext(request);
		IResourceGatherAssistService gatherService = null;

		for (int i = 0; i < beanid.length; i++) {
			gatherService = (IResourceGatherAssistService) context
					.getBean(beanid[i]);

			@SuppressWarnings("unchecked")
			List<Map<String, Object>> resourceMapList = gatherService
					.getTargetApplicationResourceInformation();
			result = assistService.save(resourceMapList);

			if (!result)
				break;
			
			model.addAttribute("msg", result);
		}

		return "jsonView";
	}

	/**
	 * make json data that includes system name, bean id and server url information.
	 * @param request HttpServletRequest
	 * @param model Model object 
	 * @return jsonView
	 * @throws Exception fail to make json data
	 */
	@RequestMapping("/admin/assist/listData.do")
	public String listData(HttpServletRequest request, Model model)
			throws Exception {
		WebApplicationContext context = RequestContextUtils
				.getWebApplicationContext(request);
		
		int j = 0;
		String[] systemNames = propertiesService.getStringArray("SYSTEM_NAMES");
		
		String[] webBeanNames = context.getBeanDefinitionNames();
		List<Map<String, String>> beanList = new ArrayList<Map<String, String>>();
		Map<String, String> beanMap = new HashMap<String, String>();

		for (int i = 0; i < webBeanNames.length; i++) {
			Object bean = context.getBean(webBeanNames[i]);

			if (bean instanceof IResourceGatherAssistService) {
				beanMap = new HashMap<String, String>();

				beanMap.put("systemName", systemNames[j++]);
				HttpInvokerProxyFactoryBean proxyBean = (HttpInvokerProxyFactoryBean) context
						.getBean("&" + webBeanNames[i]);

				beanMap.put("beanId", webBeanNames[i]);
				beanMap.put("serverUrl", proxyBean.getServiceUrl());

				beanList.add(beanMap);
			}
		}

		Page resultPage = null;
		if (beanList != null) {
			resultPage = new Page(beanList, 1, beanList.size(), 1, 10);
		}

		model.addAttribute("page", resultPage.getCurrentPage() + "");
		model.addAttribute("total", resultPage.getMaxPage() + "");
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());

		return "jsonView";
	}

	/**
	 * make bean list of target application
	 * 
	 * @param request
	 *            HttpServletRequest object
	 * @param model
	 *            Model object to add attributes
	 * @return String list bean list
	 */
	@RequestMapping("/admin/assist/assistlist.do")
	public String list(HttpServletRequest request, Model model) {
		WebApplicationContext context = RequestContextUtils
				.getWebApplicationContext(request);

		String[] webBeanNames = context.getBeanDefinitionNames();

		List<Map<String, String>> beanList = new ArrayList<Map<String, String>>();
		Map<String, String> beanMap = new HashMap<String, String>();

		for (int i = 0; i < webBeanNames.length; i++) {
			Object bean = context.getBean(webBeanNames[i]);

			if (bean instanceof IResourceGatherAssistService) {
				beanMap = new HashMap<String, String>();

				HttpInvokerProxyFactoryBean proxyBean = (HttpInvokerProxyFactoryBean) context
						.getBean("&" + webBeanNames[i]);

				beanMap.put("beanId", webBeanNames[i]);
				beanMap.put("serverUrl", proxyBean.getServiceUrl());

				beanList.add(beanMap);
			}
		}

		if (beanList != null) {
			model.addAttribute("beanlist", beanList);
		}
		return "/assist/assistlist";
	}

}
