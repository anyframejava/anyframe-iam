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

package org.anyframe.iam.admin.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Candidate_Secured_Resources Domain Object that related with
 * CANDIDATE_SECURED_RESOURCES table
 * @author AnyframeGen
 * 
 */
@Entity
@Table(name = "CANDIDATE_SECURED_RESOURCES")
public class CandidateSecuredResources implements Serializable {

	private static final long serialVersionUID = 1L;

	private String candidateResourceId;
	
	private String beanid;

	private String packages;

	private String classes;

	private String method;

	private String parameter;

	private String requestMapping;
	
	private String pointCut;
	
	private String candidateResourceType;
	
	private String systemName;
	
	public CandidateSecuredResources() {

	}

	@Id
	@Column(name = "CANDIDATE_RESOURCE_ID", unique = true, nullable = false, length = 20)
	public String getCandidateResourceId() {
		return candidateResourceId;
	}

	public void setCandidateResourceId(String candidateResourceId) {
		this.candidateResourceId = candidateResourceId;
	}
	
	@Column(name = "BEANID", length = 128)
	public String getBeanid() {
		return beanid;
	}

	public void setBeanid(String beanid) {
		this.beanid = beanid;
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
	
	@Column(name = "SYSTEM_NAME", length = 15)
	public String getSystemName(){
		return systemName;
	}
	
	public void setSystemName(String systemName){
		this.systemName = systemName;
	}

	public boolean equals(Object o) {
		if(this == o){
			return true;
		}
		
		if((o == null) || (getClass() != o.getClass())){
			return false;
		}
		
		CandidateSecuredResources pojo = (CandidateSecuredResources) o;
		
		if((candidateResourceId != null) ? (!candidateResourceId.equals(pojo.candidateResourceId)) : (pojo.candidateResourceId != null)){
			return false;
		}
		
		if((beanid != null) ? (!beanid.equals(pojo.beanid)) : (pojo.beanid != null)){
			return false;
		}
		
		if((packages != null) ? (!packages.equals(pojo.packages)) : (pojo.packages != null)){
			return false;
		}
		
		if((classes != null) ? (!classes.equals(pojo.classes)) : (pojo.classes != null)){
			return false;
		}
		
		if((method != null) ? (!method.equals(pojo.method)) : (pojo.method != null)){
			return false;
		}
		
		if((parameter != null) ? (!parameter.equals(pojo.parameter)) : (pojo.parameter != null)){
			return false;
		}
		
		if((requestMapping != null) ? (!requestMapping.equals(pojo.requestMapping)) : (pojo.requestMapping != null)){
			return false;
		}
		
		if((pointCut != null) ? (!pointCut.equals(pojo.pointCut)) : (pojo.pointCut != null)){
			return false;
		}
		
		if((candidateResourceType != null) ? (!candidateResourceType.equals(pojo.candidateResourceType)) : (pojo.candidateResourceType != null)){
			return false;
		}
		
		return true;
	}

	public int hashCode() {
		int result = 0;
		result = ((candidateResourceId != null) ? candidateResourceId.hashCode() : 0);
		result = (31 * result) + ((beanid != null) ? beanid.hashCode() : 0);
		result = (31 * result) + ((packages != null) ? packages.hashCode() : 0);
		result = (31 * result) + ((classes != null) ? classes.hashCode() : 0);
		result = (31 * result) + ((method != null) ? method.hashCode() : 0);
		result = (31 * result) + ((parameter != null) ? parameter.hashCode() : 0);
		result = (31 * result) + ((requestMapping != null) ? requestMapping.hashCode() : 0);
		result = (31 * result) + ((pointCut != null) ? pointCut.hashCode() : 0);
		result = (31 * result) + ((candidateResourceType != null) ? candidateResourceType.hashCode() : 0);
		
		return result;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());
		
		sb.append(" [");
		sb.append("candidateResourceId").append("='").append(getCandidateResourceId()).append("', ");
		sb.append("beanid").append("='").append(getBeanid()).append("', ");
		sb.append("packages").append("='").append(getPackages()).append("', ");
		sb.append("classes").append("='").append(getClasses()).append("', ");
		sb.append("method").append("='").append(getMethod()).append("', ");
		sb.append("parameter").append("='").append(getParameter()).append("', ");
		sb.append("requestMapping").append("='").append(getRequestMapping()).append("', ");
		sb.append("pointCut").append("='").append(getPointCut()).append("', ");
		sb.append("candidateResourceType").append("='").append(getCandidateResourceType()).append("', ");
		
		sb.append("]");
		return sb.toString();
	}

}
