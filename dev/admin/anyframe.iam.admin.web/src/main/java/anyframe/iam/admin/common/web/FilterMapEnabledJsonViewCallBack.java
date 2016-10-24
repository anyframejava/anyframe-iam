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

import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.json.error.StandartErrorConverter;

/**
 * An object to filter useless data when convert data to JSON object
 * @author byounghoon.woo
 * 
 */
public class FilterMapEnabledJsonViewCallBack extends StandartErrorConverter {
	private List<String> filterKeys;

	@SuppressWarnings("unchecked")
	@Override
	public void onPopulateSuccess(Map model, RequestContext rc, BindingResult br) {

		for (String filterKey : filterKeys) {
			if (model.containsKey(filterKey)) {
				model.remove(filterKey);
			}
		}

		super.onPopulateSuccess(model, rc, br);
	}

	public List<String> getFilterKeys() {
		return filterKeys;
	}

	public void setFilterKeys(List<String> filterKeys) {
		this.filterKeys = filterKeys;
	}

}
