package org.anyframe.iam.audit.dao;

import java.util.Collection;

import org.anyframe.iam.audit.domain.AuditLog;


public interface AuditLogDao{
	public Collection<AuditLog> selectAuditLogs();

	public int insertAuditLog(AuditLog auditLog) throws Exception ;
}
