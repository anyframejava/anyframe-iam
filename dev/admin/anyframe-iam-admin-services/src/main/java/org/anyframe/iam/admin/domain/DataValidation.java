package org.anyframe.iam.admin.domain;

import java.io.Serializable;

public class DataValidation implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String sheetName;
	
	private String rowNum;
	
	private String errorMessage;
	
	public DataValidation(){
		
	}
	
	public DataValidation(String id, String sheetName, String rowNum, String errorMessage){
		this.id = id;
		this.sheetName = sheetName;
		this.rowNum = rowNum;
		this.errorMessage = errorMessage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer(getClass().getSimpleName());

		sb.append(" [");
		sb.append("id").append("='").append(getId()).append("', ");
		sb.append("sheetName").append("='").append(getSheetName()).append("', ");
		sb.append("rowNum").append("='").append(getRowNum()).append("', ");
		sb.append("errorMessage").append("='").append(getErrorMessage());

		sb.append("]");

		return sb.toString();
	}
	
}
