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

package anyframe.iam.admin.candidatesecuredresources.dao.impl;

import java.util.List;
import java.util.Map;

import anyframe.common.util.StringUtil;
import anyframe.iam.admin.candidatesecuredresources.dao.CandidateSecuredResourcesDao;
import anyframe.iam.admin.common.IamGenericDaoHibernate;
import anyframe.iam.admin.domain.CandidateSecuredResources;

public class CandidateSecuredResourcesDaoHibernateImpl extends
		IamGenericDaoHibernate<CandidateSecuredResources, String> implements CandidateSecuredResourcesDao {

	public CandidateSecuredResourcesDaoHibernateImpl() {
		super(CandidateSecuredResources.class);
	}

	public String getPackagesList(String keyword) throws Exception {
		keyword = StringUtil.null2str(keyword);
		keyword = keyword.toLowerCase();
		Object[] args = new Object[1];
		args[0] = "keyword=" + keyword + "%";
		List list = this.getDynamicHibernateService().findList("findPackagesNameList", args);

		StringBuffer resourceNameList = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			resourceNameList.append(((Map) list.get(i)).get("packages") + "\n");
		}
		return resourceNameList.toString();
	}

	public String getClassesList(String keyword, String packages) throws Exception {
		keyword = StringUtil.null2str(keyword);
		keyword = keyword.toLowerCase();
		packages = StringUtil.null2str(packages);
		packages = packages.toLowerCase();
		Object[] args = new Object[2];
		args[0] = "keyword=" + keyword + "%";
		args[1] = "packages=" + packages;
		List list = this.getDynamicHibernateService().findList("findClassesNameList", args);

		StringBuffer resourceNameList = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			resourceNameList.append(((Map) list.get(i)).get("classes") + "\n");
		}
		return resourceNameList.toString();
	}

	public String getMethodList(String keyword, String packages, String classes) throws Exception {
		keyword = StringUtil.null2str(keyword);
		keyword = keyword.toLowerCase();
		packages = StringUtil.null2str(packages);
		packages = packages.toLowerCase();
		classes = StringUtil.null2str(classes);
		classes = classes.toLowerCase();
		Object[] args = new Object[3];
		args[0] = "keyword=" + keyword + "%";
		args[1] = "packages=" + packages;
		args[2] = "classes=" + classes;
		List list = this.getDynamicHibernateService().findList("findMethodNameList", args);

		StringBuffer resourceNameList = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			resourceNameList.append(((Map) list.get(i)).get("method") + "\n");
		}
		return resourceNameList.toString();
	}

	public String getRequestMappingList(String keyword) throws Exception {
		keyword = StringUtil.null2str(keyword);
		keyword = keyword.toLowerCase();
		Object[] args = new Object[1];
		args[0] = "keyword=" + keyword + "%";
		List list = this.getDynamicHibernateService().findList("findRequestMappingNameList", args);

		StringBuffer resourceNameList = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			resourceNameList.append(((Map) list.get(i)).get("requestMapping") + "\n");
		}
		return resourceNameList.toString();
	}

	public String getParameterList(String keyword, String requestMapping) throws Exception {
		keyword = StringUtil.null2str(keyword);
		keyword = keyword.toLowerCase();
		requestMapping = StringUtil.null2str(requestMapping);
		requestMapping = requestMapping.toLowerCase();
		Object[] args = new Object[2];
		args[0] = "keyword=" + keyword + "%";
		args[1] = "urlPattern_do=" + requestMapping;
		List list = this.getDynamicHibernateService().findList("findParameterNameList", args);

		StringBuffer resourceNameList = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			resourceNameList.append(((Map) list.get(i)).get("parameter") + "\n");
		}
		return resourceNameList.toString();
	}

	public String getPointCutList(String keyword) throws Exception {
		keyword = StringUtil.null2str(keyword);
		keyword = keyword.toLowerCase();
		Object[] args = new Object[1];
		args[0] = "keyword=" + keyword + "%";
		List list = this.getDynamicHibernateService().findList("findPointCutNameList", args);

		StringBuffer resourceNameList = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			resourceNameList.append(((Map) list.get(i)).get("pointCut") + "\n");
		}
		return resourceNameList.toString();
	}
}
