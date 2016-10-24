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
package anyframe.iam.admin.common;

import java.io.Serializable;

import anyframe.core.generic.dao.hibernate.GenericDaoHibernate;

/**
 * This class serves as the Base class for all other DAOs - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 * <p/>
 * 
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 * @author modified by SungRyong Kim
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public class IamGenericDaoHibernate<T, PK extends Serializable> extends GenericDaoHibernate<T, PK> implements
		IamGenericDao<T, PK> {

	public IamGenericDaoHibernate(final Class<T> persistentClass) {
		super(persistentClass);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public T save(T object) throws Exception {
		return (T) this.getHibernateTemplate().merge(object);
	}
}
