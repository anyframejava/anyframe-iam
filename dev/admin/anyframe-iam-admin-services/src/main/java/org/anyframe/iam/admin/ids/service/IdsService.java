package org.anyframe.iam.admin.ids.service;

import java.util.List;

import org.anyframe.generic.service.GenericService;
import org.anyframe.iam.admin.domain.Ids;

public interface IdsService extends GenericService<Ids, String> {
	Ids save(Ids ids) throws Exception;
	
	long updateNextId(List objectList, String tableName) throws Exception;
}
