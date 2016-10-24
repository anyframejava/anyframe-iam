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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import anyframe.iam.core.assist.IResourceCreationAssistService;
import anyframe.iam.core.assist.IResourceGatherAssistService;

/**
 * Annotation Assist Controller
 * 
 * @author jongpil.park
 * 
 */
@Controller
public class AnnotationAssistController {

	@Resource(name = "gatherServiceClient")
	private IResourceGatherAssistService gatherServiceClient;

	@Resource(name = "creationServiceClient")
	private IResourceCreationAssistService creationServiceClient;

	/**
	 * collect information of target application
	 * 
	 * @return information of target application
	 * @throws Exception fail to collect data
	 */
	@RequestMapping("/admin/assist/resourceAssist.do")
	public String reloadResources(Model model) throws Exception {
		boolean result = false;
		List<Map<String, Object>> resourceMapList = gatherServiceClient.getTargetApplicationResourceInformation();
		result = creationServiceClient.createTargetApplicationResourceInformation(resourceMapList);

		model.addAttribute("msg", result);

		return "jsonView";

		// return result ? "common/complete" : "common/error";
	}

	/**
	 * make bean list of target application
	 * @param request HttpServletRequest object
	 * @param model Model object to add attributes
	 * @return String list bean list
	 */
	@RequestMapping("/admin/assist/assistlist.do")
	public String list(HttpServletRequest request, Model model) {
		WebApplicationContext context = RequestContextUtils.getWebApplicationContext(request);

		String[] webBeanNames = context.getBeanDefinitionNames();

		List<Map<String, String>> beanList = new ArrayList<Map<String, String>>();
		Map<String, String> beanMap = new HashMap<String, String>();

		for (int i = 0; i < webBeanNames.length; i++) {
			Object bean = context.getBean(webBeanNames[i]);

			if (bean instanceof IResourceGatherAssistService) {
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
		return "/assist/assistlist";
	}

}
