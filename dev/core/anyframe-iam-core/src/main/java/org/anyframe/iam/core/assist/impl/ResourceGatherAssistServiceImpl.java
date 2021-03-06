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
package org.anyframe.iam.core.assist.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.anyframe.iam.core.assist.IResourceGatherAssistService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.util.FieldUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;

public class ResourceGatherAssistServiceImpl implements IResourceGatherAssistService, ApplicationContextAware {
	public static final String SERVICE_BEAN_POST_FIX = "Service";

	private ApplicationContext context;

	private String candidateBeanPostfix;

	private List filterPatterns;

	private Set compiledPatterns = new HashSet();
	
	private String systemName;

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public ResourceGatherAssistServiceImpl() {
		this.candidateBeanPostfix = SERVICE_BEAN_POST_FIX;
	}

	public String getCandidateBeanPostfix() {
		return candidateBeanPostfix;
	}

	public void setCandidateBeanPostfix(String candidateBeanPostfix) {
		this.candidateBeanPostfix = candidateBeanPostfix;
	}

	public void setFilterPatterns(List filterPatterns) {
		this.filterPatterns = filterPatterns;

		// pattern compile
		for (int i = 0; i < filterPatterns.size(); i++) {
			compiledPatterns.add(Pattern.compile((String) filterPatterns.get(i)));
		}
	}

	public List getFilterPatterns() {
		return filterPatterns;
	}

	private boolean attemptPatternMatch(String className) {
		Iterator itr = compiledPatterns.iterator();
		boolean isMatchFound = false;
		while (itr.hasNext()) {
			if (((Pattern) itr.next()).matcher(className).matches()) {
				isMatchFound = true;
				break;
			}
		}
		return isMatchFound;
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

							// skip user defined filtering pattern
							if (attemptPatternMatch(className)) {
								continue;
							}

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
								resourceMap.put("systemname", this.getSystemName());
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
					resourceMap.put("systemname", this.getSystemName());
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
				//entryValue = entryValue.substring(entryValue.indexOf("{") + 1, entryValue.lastIndexOf("}") - 1);
				entryValue = entryValue.replaceAll("\\{|\\}", "");

				String[] splitValue = entryValue.split(",");

				if(!"".equals(entryValue)){
					
					/*
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
									resourceMap.put("systemname", this.getSystemName());
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
								resourceMap.put("systemname", this.getSystemName());
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
							resourceMap.put("systemname", this.getSystemName());
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
		}
		
		/**
		 * 4.paramResolver
		 */
		String paramResolverName = getMethodNameResolverInformation(context); 
		resourceMap = new HashMap();

		resourceMap.put("beanid", paramResolverName);
		resourceMap.put("systemname", this.getSystemName());
		resourceMap.put("packages", "paramResolver");
		resourceMap.put("classes", "");
		resourceMap.put("method", "");
		resourceMap.put("parameter", "");
		resourceMap.put("requestmapping", "");
		resourceMap.put("pointcut", "");
		resourceMap.put("candidate_resource_type", "param");

		resourceMapList.add(resourceMap);

		return resourceMapList;
	}

	private Map getHandlerMappingInformation(ApplicationContext context) throws BeansException {

		String[] webBeanNames = context.getBeanDefinitionNames();

		Map handlerMappingAssistMap = new HashMap();

		for (int i = 0; i < webBeanNames.length; i++) {
			Object bean = context.getBean(webBeanNames[i]);

			LOGGER.debug("ResourceAssistProcessor postProcessBeforeInitialization with : " + webBeanNames[i]);

			// Url Handler Mapping
			if (bean instanceof AbstractUrlHandlerMapping) {
				Map handlerMap = ((AbstractUrlHandlerMapping) bean).getHandlerMap();
				handlerMappingAssistMap.put(bean.getClass().getSimpleName(), handlerMap);
			}
			
			// Annotation Handler Mapping
			if (bean instanceof DefaultAnnotationHandlerMapping) {
				Map handlerMap = ((DefaultAnnotationHandlerMapping) bean).getHandlerMap();
				handlerMappingAssistMap.put(bean.getClass().getSimpleName(), handlerMap);
			}
		}

		return handlerMappingAssistMap;
	}
	
	private String getMethodNameResolverInformation(ApplicationContext context) throws BeansException, Exception {
		String[] webBeanNames = context.getBeanDefinitionNames();
		Object paramName = "";
		
		for (int i = 0; i < webBeanNames.length; i++) {
			Object bean = context.getBean(webBeanNames[i]);
			
			if (bean instanceof MethodNameResolver) {
				Field field = ReflectionUtils.findField(bean.getClass(),"paramName");
				if (field != null) {
					try {
						paramName = FieldUtils.getFieldValue(bean,field.getName());
					}
					catch (IllegalAccessException e) {
						// nothing
					}
				}
			}
		}
		
		return paramName.toString();
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

}
