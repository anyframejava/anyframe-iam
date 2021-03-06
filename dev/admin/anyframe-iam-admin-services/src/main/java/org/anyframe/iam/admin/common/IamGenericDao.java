/*
 * Copyright 2002-2009 the original author or authors.
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
package org.anyframe.iam.admin.common;

import java.io.Serializable;

import org.anyframe.generic.dao.GenericDao;

/**
 * Generic DAO (Data Access Object) with common methods to CRUD POJOs.
 * <p>
 * Extend this interface if you want typesafe (no casting necessary) DAO's for
 * your domain objects.
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 * @author modified by SungRyong Kim
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public interface IamGenericDao<T, PK extends Serializable> extends GenericDao<T, PK> {

	/**
	 * Generic method to save an object - handles both update and insert.
	 * @param object the object to save
	 * @return the persisted object
	 */
	T save(T object) throws Exception;
}
