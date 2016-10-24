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

package anyframe.iam.admin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import anyframe.core.generic.model.BaseObject;

/**
 * Candidate_Secured_Resources Domain Object that related with
 * CANDIDATE_SECURED_RESOURCES table
 * @author AnyframeGen
 * 
 */
@Entity
@Table(name = "CANDIDATE_SECURED_RESOURCES")
public class CandidateSecuredResources extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String candidateResourceId;

	private String packages;

	private String classes;

	private String method;

	private String parameter;

	private String requestMapping;

	private String candidateResourceType;

	private String pointCut;

	public CandidateSecuredResources() {

	}

	@Id
	@Column(name = "CANDIDATE_RESOURCE_ID", unique = true, nullable = false, length = 10)
	public String getCandidateResourceId() {
		return candidateResourceId;
	}

	public void setCandidateResourceId(String candidateResourceId) {
		this.candidateResourceId = candidateResourceId;
	}

	@Column(name = "PACKAGES", length = 128)
	public String getPackages() {
		return packages;
	}

	public void setPackages(String packages) {
		this.packages = packages;
	}

	@Column(name = "CLASSES", length = 128)
	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	@Column(name = "METHOD", length = 128)
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Column(name = "PARAMETER", length = 128)
	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Column(name = "REQUESTMAPPING", length = 128)
	public String getRequestMapping() {
		return requestMapping;
	}

	public void setRequestMapping(String requestMapping) {
		this.requestMapping = requestMapping;
	}

	@Column(name = "CANDIDATE_RESOURCE_TYPE", length = 10)
	public String getCandidateResourceType() {
		return candidateResourceType;
	}

	public void setCandidateResourceType(String candidateResourceType) {
		this.candidateResourceType = candidateResourceType;
	}

	@Column(name = "POINTCUT", length = 512)
	public String getPointCut() {
		return pointCut;
	}

	public void setPointCut(String pointCut) {
		this.pointCut = pointCut;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
