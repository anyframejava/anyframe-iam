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

package anyframe.iam.admin.candidatesecuredresources.service.impl;

import java.util.regex.Pattern;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.springframework.security.util.RegexUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;

import anyframe.core.generic.service.impl.GenericServiceImpl;
import anyframe.iam.admin.candidatesecuredresources.dao.CandidateSecuredResourcesDao;
import anyframe.iam.admin.candidatesecuredresources.service.CandidateSecuredResourcesService;
import anyframe.iam.admin.domain.CandidateSecuredResources;

public class CandidateSecuredResourcesServiceImpl extends GenericServiceImpl<CandidateSecuredResources, String>
		implements CandidateSecuredResourcesService {

	CandidateSecuredResourcesDao candidateSecuredResourcesDao;

	private UrlMatcher urlMatcher;

	private boolean isRegex = false;

	private PointcutParser parser;

	public boolean isRegex() {
		return isRegex;
	}

	public void setRegex(boolean isRegex) {
		this.isRegex = isRegex;
	}

	public UrlMatcher getUrlMatcher() {
		return urlMatcher;
	}

	public void setUrlMatcher(UrlMatcher urlMatcher) {
		this.urlMatcher = urlMatcher;
		if (urlMatcher instanceof RegexUrlPathMatcher) {
			isRegex = true;
		}
	}

	public CandidateSecuredResourcesServiceImpl(CandidateSecuredResourcesDao candidateSecuredResourcesDao) {
		super(candidateSecuredResourcesDao);
		this.candidateSecuredResourcesDao = candidateSecuredResourcesDao;
		parser = PointcutParser.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();
	}

	public String getPackagesList(String keyword) throws Exception {
		return this.candidateSecuredResourcesDao.getPackagesList(keyword);
	}

	public String getClassesList(String keyword, String packages) throws Exception {
		return this.candidateSecuredResourcesDao.getClassesList(keyword, packages);
	}

	public String getMethodList(String keyword, String packages, String classes) throws Exception {
		return this.candidateSecuredResourcesDao.getMethodList(keyword, packages, classes);
	}

	public String getRequestMappingList(String keyword) throws Exception {
		return this.candidateSecuredResourcesDao.getRequestMappingList(keyword);
	}

	public String getParameterList(String keyword, String requestMapping) throws Exception {
		return this.candidateSecuredResourcesDao.getParameterList(keyword, requestMapping);
	}

	public String getPointCutList(String keyword) throws Exception {
		return this.candidateSecuredResourcesDao.getPointCutList(keyword);
	}

	// Resource Pattern이 적절한지 여부를 검사하는 method
	public boolean checkMatched(String savePattern, String resourceType) throws Exception {
		if ("url".equalsIgnoreCase(resourceType)) {
			String[] allMappings = this.candidateSecuredResourcesDao.getRequestMappingList("").split("\n");
			for (String presentUrl : allMappings) {
				Object savePatternObj = (isRegex ? Pattern.compile(savePattern) : savePattern);
				if (urlMatcher.pathMatchesUrl(savePatternObj, presentUrl)) {
					return true;
				}
			}

			for (String presentUrl : allMappings) {
				String beanid = this.candidateSecuredResourcesDao.findMethodParam();
				String[] methodParam = this.candidateSecuredResourcesDao.getParameterList("", presentUrl).split("\n");

				for (String parameter : methodParam) {
					String checkPattern = presentUrl + "?" + beanid + "=" + parameter;

					Object savePatternObj = (isRegex ? Pattern.compile(savePattern) : savePattern);
					if (urlMatcher.pathMatchesUrl(savePatternObj, checkPattern)) {
						return true;
					}
				}
			}
			return false;
		}
		else if ("method".equalsIgnoreCase(resourceType)) {
			String[] allMappings = this.candidateSecuredResourcesDao.getPointCutList("").split("\n");
			for (String presentMethod : allMappings) {
				if (presentMethod.equalsIgnoreCase(savePattern))
					return true;
			}
		}
		else if ("pointcut".equalsIgnoreCase(resourceType)) {

			try {
				PointcutExpression pexp = parser.parsePointcutExpression(savePattern);
				if (pexp != null) {
					// TODO : more detail target class matching check
					return true;
				}
				else {
					return false;
				}
			}
			catch (Exception e) {
				log.debug(e.getMessage());
				return false;
			}
		}

		return false;
	}

	public String findMethodParam() throws Exception {
		return this.candidateSecuredResourcesDao.findMethodParam();
	}
}
