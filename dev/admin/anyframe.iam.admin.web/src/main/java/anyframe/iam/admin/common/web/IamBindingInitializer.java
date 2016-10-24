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

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.hibernate.collection.PersistentSet;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.json.JsonWriterConfiguratorTemplateRegistry;
import org.springframework.web.servlet.view.json.writer.jsonlib.JsonlibJsonWriterConfiguratorTemplate;

/**
 * Initializer class to control LazyLoadingException
 * @author byounghoon.woo
 * 
 */
public class IamBindingInitializer implements WebBindingInitializer {

	public void initBinder(WebDataBinder binder, WebRequest request) {
		JsonWriterConfiguratorTemplateRegistry registry = JsonWriterConfiguratorTemplateRegistry
				.load((HttpServletRequest) ((NativeWebRequest) request).getNativeRequest());
		registry.registerConfiguratorTemplate(new JsonlibJsonWriterConfiguratorTemplate() {
			@Override
			public JsonConfig getJsonConfig() {
				JsonConfig config = new JsonConfig();

				// Exclude nested Set Object properties
				config.setJsonPropertyFilter(new PropertyFilter() {
					public boolean apply(Object source, String name, Object value) {
						if (value != null && value.getClass().getSimpleName().equals("BeanPropertyBindingResult")) 
							return true;
						if (value != null && PersistentSet.class.isAssignableFrom(value.getClass()))
							return true;
						return false;
					}
				});
				
				return config;
			}
		});

	}

}
