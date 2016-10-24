package org.anyframe.iam.audit.service;

import java.util.Collection;

import org.anyframe.iam.audit.domain.AuditLog;


public interface AuditLogService {
	public Collection<AuditLog> selectAuditLogs();
	
	public int insertAuditLog(AuditLog auditLog) throws Exception;
}
