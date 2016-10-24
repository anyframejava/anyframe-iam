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
package org.anyframe.iam.core.reload.impl;

import java.util.List;

import org.anyframe.iam.core.intercept.web.ReloadableRestrictedTimesFilterInvocationSecurityMetadataSource;
import org.anyframe.iam.core.reload.IResourceReloadService;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AnyframeRoleHierarchyRestrictedVoter;
import org.springframework.security.web.access.intercept.AnyframeReloadableDefaultFilterInvocationSecurityMetadataSource;

public class ResourceReloadServiceImpl implements IResourceReloadService {

	private AnyframeReloadableDefaultFilterInvocationSecurityMetadataSource databaseSecurityMetadataSource;

	private ReloadableRestrictedTimesFilterInvocationSecurityMetadataSource restrictedTimesSecurityMetadataSource;

	private AffirmativeBased restrictedTimesAccessDecisionManager;

	public void setDatabaseSecurityMetadataSource(
			AnyframeReloadableDefaultFilterInvocationSecurityMetadataSource databaseSecurityMetadataSource) {
		this.databaseSecurityMetadataSource = databaseSecurityMetadataSource;
	}

	public void setRestrictedTimesSecurityMetadataSource(
			ReloadableRestrictedTimesFilterInvocationSecurityMetadataSource restrictedTimesSecurityMetadataSource) {
		this.restrictedTimesSecurityMetadataSource = restrictedTimesSecurityMetadataSource;
	}

	public void setRestrictedTimesAccessDecisionManager(AffirmativeBased restrictedTimesAccessDecisionManager) {
		this.restrictedTimesAccessDecisionManager = restrictedTimesAccessDecisionManager;
	}

	public boolean resourceReload(String reloadmaps, String reloadtimes) throws Exception {
		if ("maps".equals(reloadmaps)) {
			if (databaseSecurityMetadataSource != null) {
				databaseSecurityMetadataSource.reloadRequestMap();
			}
		}

		if ("times".equals(reloadtimes)) {
			if (restrictedTimesSecurityMetadataSource != null) {
				restrictedTimesSecurityMetadataSource.reloadRestrictedTimes();
			}

			if (restrictedTimesAccessDecisionManager != null) {
				List voterList = restrictedTimesAccessDecisionManager.getDecisionVoters();

				if (voterList != null) {
					for (int i = 0; i < voterList.size(); i++) {
						AccessDecisionVoter voter = (AccessDecisionVoter) voterList.get(i);
						if (voter instanceof AnyframeRoleHierarchyRestrictedVoter) {
							((AnyframeRoleHierarchyRestrictedVoter) voter).clearHierarchyAddedCadCache();
						}
					}
				}
			}
		}

		return true;
	}
}
