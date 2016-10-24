package org.anyframe.iam.audit.aspect;

import java.net.InetAddress;

import org.anyframe.iam.audit.domain.AuditLog;
import org.anyframe.iam.audit.service.AuditLogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.WebAuthenticationDetails;
import org.springframework.security.userdetails.UserDetails;


public class AuditLoggingAspect implements ApplicationContextAware {
    private ApplicationContext applicationContext;
	Log LOGGER = LogFactory.getLog(AuditLoggingAspect.class);

    public void auditLogging(JoinPoint thisJoinPoint) {
        String methodName = thisJoinPoint.getSignature().getName();
		String itemId = thisJoinPoint.getTarget().getClass().getSimpleName();
		String itemName = thisJoinPoint.getTarget().getClass().getName() + "." + methodName;

		try {
			// SpringSecurity Resources
			Authentication authentication = (Authentication)SecurityContextHolder.getContext().getAuthentication(); 
			Object obj = (authentication != null) ? (Object) authentication.getPrincipal():null;
			WebAuthenticationDetails details = (authentication != null) ? (WebAuthenticationDetails) authentication.getDetails():null;

			String username = (obj != null) ? ((obj instanceof UserDetails) ? ((UserDetails)obj).getUsername():obj.toString()):"anonymous";
			String clientIp = (details != null) ? details.getRemoteAddress() : null;
			InetAddress addr = InetAddress.getLocalHost();
			String hostName = addr.getHostName();
			
			// Audit Log Message
			AuditLogService auditLogService = (AuditLogService)applicationContext.getBean("auditLogService");
			AuditLog auditLog = new AuditLog();
			auditLog.setLogMessage(methodName);
			auditLog.setLogonName(username);
			auditLog.setActionType("Service");
			auditLog.setItemType("Method");
			auditLog.setActionStatus("Success");
			auditLog.setItemId(itemId);
			auditLog.setItemName(itemName);
			auditLog.setHostName(hostName);
			auditLog.setClientIP(clientIp);

			// Audit Log Write
			auditLogService.insertAuditLog(auditLog);
		} catch(Exception e) {
			LOGGER.error("Error occured during audit logging.\n Cause : " + e.getMessage());
		}
    }

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;	
	}
}
