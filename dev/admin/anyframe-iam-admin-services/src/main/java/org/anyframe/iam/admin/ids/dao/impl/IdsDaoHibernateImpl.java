package org.anyframe.iam.admin.ids.dao.impl;

import org.anyframe.iam.admin.common.IamGenericDaoHibernate;
import org.anyframe.iam.admin.domain.Ids;
import org.anyframe.iam.admin.ids.dao.IdsDao;
import org.springframework.stereotype.Repository;


@Repository("idsDao")
public class IdsDaoHibernateImpl extends IamGenericDaoHibernate<Ids, String>
		implements IdsDao {

	public IdsDaoHibernateImpl(){
		super(Ids.class);
	}
}
