package anyframe.iam.admin.ids.dao.impl;

import org.springframework.stereotype.Repository;

import anyframe.iam.admin.common.IamGenericDaoHibernate;
import anyframe.iam.admin.domain.Ids;
import anyframe.iam.admin.ids.dao.IdsDao;

@Repository("idsDao")
public class IdsDaoHibernateImpl extends IamGenericDaoHibernate<Ids, String>
		implements IdsDao {

	public IdsDaoHibernateImpl(){
		super(Ids.class);
	}
}
