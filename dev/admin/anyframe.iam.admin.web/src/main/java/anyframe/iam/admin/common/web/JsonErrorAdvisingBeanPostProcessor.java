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

package anyframe.iam.admin.common.web;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.RootClassFilter;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Bean Post Processor to handle JsonError annotation
 * @author byounghoon.woo
 * 
 */
public class JsonErrorAdvisingBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

	private final Log log = LogFactory.getLog(this.getClass());

	private ApplicationContext context;

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		log.debug("postProcessAfterInitialization with : " + beanName);
		return bean;
	}

	@SuppressWarnings("unchecked")
	public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {

		// 현재 생성처리할 bean 이름 출력
		log.debug("postProcessBeforeInitialization with : " + beanName);

		Class clazz = bean.getClass();

		// JsonError Annotation 체크를 위한 MethodMatcher
		AnnotationMethodMatcher annotationMethodMatcher = new AnnotationMethodMatcher(JsonError.class);

		// advice(JsonIAMException 으로 재처리하는 JsonExceptionTransfer) + Pointcut 으로
		// 구성된 Advisor
		DefaultPointcutAdvisor advisor = (DefaultPointcutAdvisor) context.getBean("jsonErrorAdvisor");

		// 현재 bean Class 에 존재하는 모든 메서드를 검색하며 JsonError Annotation 이 달려 있는 경우를
		// 체크함
		for (Method method : clazz.getDeclaredMethods()) {
			// JsonError Annotation 이 달려 있는 경우
			if (annotationMethodMatcher.matches(method, clazz)) {
				// maching method 로그 출력
				log.debug("maching method : " + clazz.getSimpleName() + "." + method.getName());

				// jsonErrorAdvisor 조작 전 기본 Pointcut 인 경우 - 현재 찾아진 JsonError
				// Annotation 이 달려있는 Bean 에 대한 Pointcut 으로 대체
				if (advisor.getPointcut() == Pointcut.TRUE) {
					advisor.setPointcut(new ComposablePointcut(new RootClassFilter(clazz), annotationMethodMatcher));
					// 한번 대체된 Pointcut 에 대해서는 다음에 찾아진 JsonError Annotation 이
					// 달려있는 Bean 에 대한 Pointcut 을 union 시켜 나감.
				}
				else {
					ComposablePointcut pointcut = (ComposablePointcut) advisor.getPointcut();
					pointcut.union(new ComposablePointcut(new RootClassFilter(clazz), annotationMethodMatcher));
					advisor.setPointcut(pointcut);
				}
			}
		}

		return bean;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

}
