package anyframe.iam.admin.ids.service;

import java.util.List;

import anyframe.core.generic.service.GenericService;
import anyframe.iam.admin.domain.Ids;

public interface IdsService extends GenericService<Ids, String> {
	Ids save(Ids ids) throws Exception;
	
	long updateNextId(List objectList, String tableName) throws Exception;
}
