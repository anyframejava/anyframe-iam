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
package org.anyframe.iam.core.userdetails.jdbc;

import java.util.Date;

/**
 * 
 * @author Byunghun Woo
 *
 */
public class CustomUser implements java.io.Serializable {

	private static final long serialVersionUID = 2427239479058233120L;

	private String userId;

	private String userName;

	private String password;

	private Boolean enabled;

	private String ssn;

	private Character slYn;

	private String birthDay;

	private Short age;

	private String cellPhone;

	private String addr;

	private String email;

	private Character emailYn;

	private String imageFile;

	private Date regDate;

	public CustomUser() {
	}

	public CustomUser(String userId, String userName, String password) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
	}

	public CustomUser(String userId, String userName, String password, Boolean enabled, String ssn, Character slYn,
			String birthDay, Short age, String cellPhone, String addr, String email, Character emailYn,
			String imageFile, Date regDate) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
		this.ssn = ssn;
		this.slYn = slYn;
		this.birthDay = birthDay;
		this.age = age;
		this.cellPhone = cellPhone;
		this.addr = addr;
		this.email = email;
		this.emailYn = emailYn;
		this.imageFile = imageFile;
		this.regDate = regDate;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getSsn() {
		return this.ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public Character getSlYn() {
		return this.slYn;
	}

	public void setSlYn(Character slYn) {
		this.slYn = slYn;
	}

	public String getBirthDay() {
		return this.birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public Short getAge() {
		return this.age;
	}

	public void setAge(Short age) {
		this.age = age;
	}

	public String getCellPhone() {
		return this.cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Character getEmailYn() {
		return this.emailYn;
	}

	public void setEmailYn(Character emailYn) {
		this.emailYn = emailYn;
	}

	public String getImageFile() {
		return this.imageFile;
	}

	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	public Date getRegDate() {
		return this.regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

}
