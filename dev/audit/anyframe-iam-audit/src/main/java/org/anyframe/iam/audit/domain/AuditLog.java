package org.anyframe.iam.audit.domain;

import java.io.Serializable;

public class AuditLog implements Serializable {

	private static final long serialVersionUID = 1L;

	private String logTime;
	
	private String logMessage;
	
	private String logonName;
	
	private String actionType;
	
	private String itemType;
	
	private String actionStatus;
	
	private String itemId;
	
	private String itemName;
	
	private String hostName;
	
	private String clientIP;
	
	private String logSeq;
	
	public AuditLog(){
		
	}
	
	public AuditLog(String logTime, String logMessage, String logonName, String actionType, 
			String itemType, String actionStatus, String itemId, String itemName, String hostName, 
			String clientIP, String logSeq){
		this.logTime = logTime;
		this.logMessage = logMessage;
		this.logonName = logonName;
		this.actionType = actionType;
		this.itemType = itemType;
		this.actionStatus = actionStatus;
		this.itemId = itemId;
		this.itemName = itemName;
		this.hostName = hostName;
		this.clientIP = clientIP;
		this.logSeq = logSeq;
	}
	
	public String getLogSeq(){
		return this.logSeq;
	}
	
	public void setLogSeq(String logSeq){
		this.logSeq = logSeq;
	}
	
	public String getLogMessage(){
		return this.logMessage;
	}
	
	public void setLogMessage(String logMessage){
		this.logMessage = logMessage;
	}
	
	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	public String getLogonName() {
		return logonName;
	}

	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	@Override
	public boolean equals(Object arg0) {
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("LOG_SEQ").append("='").append(getLogSeq()).append("', ");
		sb.append("LOG_TIME").append("='").append(getLogTime()).append("', ");
		sb.append("LOG_MESSAGE").append("='").append(getLogMessage()).append("', ");
		sb.append("ACTION_TYPE").append("='").append(getActionType()).append("', ");
		sb.append("ITEM_TYPE").append("='").append(getItemType()).append("', ");
		sb.append("ACTION_STATUS").append("='").append(getActionStatus()).append("', ");
		sb.append("ITEM_ID").append("='").append(getItemId()).append("', ");
		sb.append("ITEM_NAME").append("='").append(getItemName()).append("', ");
		sb.append("HOSTNAME").append("='").append(getHostName()).append("', ");
		sb.append("CLIENT_IP").append("='").append(getClientIP()).append("', ");

		sb.append("]");

		return sb.toString();
	}

}
