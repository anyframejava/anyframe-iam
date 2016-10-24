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
package anyframe.iam.core.assist.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;

import anyframe.iam.core.assist.IResourceGatherAssistService;

public class ResourceGatherAssistServiceImpl implements IResourceGatherAssistService, ApplicationContextAware {
	public static final String SERVICE_BEAN_POST_FIX = "Service";

	private ApplicationContext context;

	private String candidateBeanPostfix;

	public ResourceGatherAssistServiceImpl() {
		this.candidateBeanPostfix = SERVICE_BEAN_POST_FIX;
	}

	public String getCandidateBeanPostfix() {
		return candidateBeanPostfix;
	}

	public void setCandidateBeanPostfix(String candidateBeanPostfix) {
		this.candidateBeanPostfix = candidateBeanPostfix;
	}

	public List getTargetApplicationResourceInformation() throws Exception {
		ApplicationContext rootContext = ContextLoader.getCurrentWebApplicationContext();
		String[] beanNames = rootContext.getBeanDefinitionNames();
		String[] beanPostFix = getCandidateBeanPostfix().split(",");

		List resourceMapList = new ArrayList();
		Map resourceMap;
		Set pointcutSet = new TreeSet();

		/**
		 * 1.Service Bean
		 */
		for (int i = 0; i < beanNames.length; i++) {
			for (int j = 0; j < beanPostFix.length; j++) {
				if (beanNames[i].lastIndexOf(beanPostFix[j].trim()) > 0) {
					if (beanPostFix[j].trim().equals(
							beanNames[i].substring(beanNames[i].lastIndexOf(beanPostFix[j].trim())))) {
						Class[] classes = rootContext.getBean(beanNames[i]).getClass().getInterfaces();

						for (int k = 0; k < classes.length; k++) {
							Method[] methods = ReflectionUtils.getAllDeclaredMethods(classes[k]);
							String className = classes[k].getName();

							if (className.indexOf("anyframe") < 0 && className.indexOf("org") < 0
									&& className.indexOf("net") < 0 && className.indexOf("java") < 0) {

								/**
								 * using pointcut pattern
								 */
								pointcutSet.add(classes[k].getPackage().getName());

								for (int l = 0; l < methods.length; l++) {
									Class[] parameterTypes = methods[l].getParameterTypes();
									String parameter = "";

									for (int m = 0; m < parameterTypes.length; m++) {
										if (m == parameterTypes.length - 1) {
											parameter = parameter + parameterTypes[m].getSimpleName();
										}
										else {
											parameter = parameter + parameterTypes[m].getSimpleName() + ",";
										}
									}
									resourceMap = new HashMap();

									resourceMap.put("beanid", beanNames[i]);
									resourceMap.put("packages", classes[k].getPackage().getName());
									resourceMap.put("classes", classes[k].getSimpleName());
									resourceMap.put("method", methods[l].getName());
									resourceMap.put("parameter", parameter);
									resourceMap.put("requestmapping", "");
									resourceMap.put("pointcut", classes[k].getName() + "." + methods[l].getName());
									resourceMap.put("candidate_resource_type", "method");

									resourceMapList.add(resourceMap);
								}
							}
						}
					}
				}
			}
		}

		/**
		 * 2.pointcut
		 */
		if (pointcutSet.size() > 0) {
			Iterator pointcutItr = pointcutSet.iterator();

			while (pointcutItr.hasNext()) {
				String pattern = pointcutItr.next().toString();

				for (int j = 0; j < beanPostFix.length; j++) {
					String pointcut = pattern + "..*" + beanPostFix[j] + "*.*(..)";

					resourceMap = new HashMap();

					resourceMap.put("beanid", "");
					resourceMap.put("packages", pattern);
					resourceMap.put("classes", "");
					resourceMap.put("method", "");
					resourceMap.put("parameter", "");
					resourceMap.put("requestmapping", "");
					resourceMap.put("pointcut", pointcut);
					resourceMap.put("candidate_resource_type", "pointcut");

					resourceMapList.add(resourceMap);
				}
			}
		}

		/**
		 * 3.handler mapping
		 */
		Map handlerMappingAssistMap = getHandlerMappingInformation(context);

		if (handlerMappingAssistMap.size() > 0) {
			Iterator requestMappingItr = handlerMappingAssistMap.entrySet().iterator();

			while (requestMappingItr.hasNext()) {
				Entry entry = (Entry) requestMappingItr.next();

				String entryKey = entry.getKey().toString();
				String entryValue = entry.getValue().toString();
				entryValue = entryValue.substring(entryValue.indexOf("{") + 1, entryValue.lastIndexOf("}") - 1);

				String[] splitValue = entryValue.split(",");

				/**
				 * 현재 BeanNameUrlHandlerMapping 일 경우에만 상세 정보가 필요하다.
				 */
				if ("BeanNameUrlHandlerMapping".equals(entryKey)) {
					for (int i = 0; i < splitValue.length; i++) {
						String[] resources = splitValue[i].trim().split("=");

						Class classes = this.context.getBean(resources[0]).getClass();
						Method[] methods = classes.getDeclaredMethods();

						if (!"ParameterizableViewController".equals(classes.getSimpleName())
								&& !"ForwardController".equals(classes.getSimpleName())) {
							for (int j = 0; j < methods.length; j++) {
								Class[] parameterTypes = methods[j].getParameterTypes();
								String parameter = "";

								for (int k = 0; k < parameterTypes.length; k++) {
									if (k == parameterTypes.length - 1) {
										parameter = parameter + parameterTypes[k].getSimpleName();
									}
									else {
										parameter = parameter + parameterTypes[k].getSimpleName() + ",";
									}
								}

								resourceMap = new HashMap();

								resourceMap.put("beanid", "");
								resourceMap.put("packages", classes.getPackage().getName());
								resourceMap.put("classes", classes.getSimpleName());
								resourceMap.put("method", methods[j].getName());
								resourceMap.put("parameter", parameter);
								resourceMap.put("requestmapping", resources[0]);
								resourceMap.put("pointcut", "");
								resourceMap.put("candidate_resource_type", "url");

								resourceMapList.add(resourceMap);
							}
						}
						else {
							resourceMap = new HashMap();

							resourceMap.put("beanid", "");
							resourceMap.put("packages", classes.getPackage().getName());
							resourceMap.put("classes", classes.getSimpleName());
							resourceMap.put("method", "");
							resourceMap.put("parameter", "");
							resourceMap.put("requestmapping", resources[0]);
							resourceMap.put("pointcut", "");
							resourceMap.put("candidate_resource_type", "url");

							resourceMapList.add(resourceMap);
						}
					}
				}
				else {
					for (int i = 0; i < splitValue.length; i++) {
						String[] resources = splitValue[i].trim().split("=");

						String className = resources[1].substring(0, resources[1].lastIndexOf("@"));
						String packageName = className.substring(0, className.lastIndexOf("."));
						String simpleClassName = className.substring(className.lastIndexOf(".") + 1);

						resourceMap = new HashMap();

						resourceMap.put("beanid", "");
						resourceMap.put("packages", packageName);
						resourceMap.put("classes", simpleClassName);
						resourceMap.put("method", "");
						resourceMap.put("parameter", "");
						resourceMap.put("requestmapping", resources[0]);
						resourceMap.put("pointcut", "");
						resourceMap.put("candidate_resource_type", "url");

						resourceMapList.add(resourceMap);
					}
				}
			}
		}

		return resourceMapList;
	}

	private Map getHandlerMappingInformation(ApplicationContext context) throws BeansException {

		String[] webBeanNames = context.getBeanDefinitionNames();

		Map handlerMappingAssistMap = new HashMap();

		for (int i = 0; i < webBeanNames.length; i++) {
			Object bean = context.getBean(webBeanNames[i]);

			LOGGER.debug("ResourceAssistProcessor postProcessBeforeInitialization with : " + webBeanNames[i]);

			if (bean instanceof AbstractUrlHandlerMapping) {
				Map handlerMap = ((AbstractUrlHandlerMapping) bean).getHandlerMap();
				handlerMappingAssistMap.put(bean.getClass().getSimpleName(), handlerMap);
			}
		}

		return handlerMappingAssistMap;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}
