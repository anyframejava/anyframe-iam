/*
 * Copyright 2002-2007 the original author or authors.
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

package org.anyframe.iam.audit.web.filter;

import java.io.IOException;
import java.net.InetAddress;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.anyframe.iam.audit.domain.AuditLog;
import org.anyframe.iam.audit.service.AuditLogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.WebAuthenticationDetails;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.filter.OncePerRequestFilter;

import anyframe.common.util.StringUtil;

public class AuditLoggingFilter extends OncePerRequestFilter {
	Log LOGGER = LogFactory.getLog(AuditLoggingFilter.class);
	ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();

	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
        String methodName = StringUtil.trim(request.getRequestURI(), request.getContextPath());
		String itemId = StringUtil.trim(request.getRequestURI(), request.getContextPath());
		String itemName = request.getRequestURI();

		try {
			// SpringSecurity Resources
			Authentication authentication = (Authentication)SecurityContextHolder.getContext().getAuthentication();
			Object obj = (authentication != null) ? (Object) authentication.getPrincipal():null;
			WebAuthenticationDetails details = (authentication != null) ? (WebAuthenticationDetails) authentication.getDetails():null;

			String username = (obj != null) ? ((obj instanceof UserDetails) ? ((UserDetails)obj).getUsername():obj.toString()):"anonymous";
			String clientIp = request.getRemoteAddr();
			InetAddress addr = InetAddress.getLocalHost();
			String hostName = addr.getHostName();

			// Audit Log Message
			AuditLogService auditLogService = (AuditLogService)context.getBean("auditLogService");
			AuditLog auditLog = new AuditLog();
			auditLog.setLogMessage(methodName);
			auditLog.setLogonName(username);
			auditLog.setActionType("Web");
			auditLog.setItemType("Url");
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
		filterChain.doFilter(request, response);
	}
}
