package anyframe.iam.admin.common.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import anyframe.common.util.StringUtil;
import anyframe.iam.admin.domain.DataValidation;
import anyframe.iam.admin.groups.service.GroupsService;
import anyframe.iam.admin.restrictedtimes.service.RestrictedTimesService;
import anyframe.iam.admin.roles.service.RolesService;
import anyframe.iam.admin.securedresources.service.SecuredResourcesService;
import anyframe.iam.admin.users.service.UsersService;
import anyframe.iam.admin.viewresources.service.ViewResourcesService;

@SuppressWarnings("unchecked")
public class IAMDomainValidator {

	@Resource(name = "usersService")
	private UsersService usersService;

	@Resource(name = "groupsService")
	private GroupsService groupsService;

	@Resource(name = "rolesService")
	private RolesService rolesService;

	@Resource(name = "restrictedTimesService")
	private RestrictedTimesService restrictedTimesService;

	@Resource(name = "viewResourcesService")
	private ViewResourcesService viewResourcesService;
	
	@Resource(name = "securedResourcesService")
	private SecuredResourcesService securedResourcesService;
	
	private List roleIds = new ArrayList();
	private List groupIds = new ArrayList();
	private List userIds = new ArrayList();
	private List resourceIds = new ArrayList();
	private List viewIds = new ArrayList();
	private List timeIds = new ArrayList();
	
	public List doValidator(File excelFile) throws Exception {

		Workbook workbook = Workbook.getWorkbook(excelFile);
		Sheet sheets[] = workbook.getSheets();
		List resultList = new ArrayList();
		int idCount = 1;
		
		File file = new File(getClass().getClassLoader().getResource(
				"domain-validation-rule.xml").getFile());

		Document document = getDocument(file);
		NodeList domainList = document.getDocumentElement().getElementsByTagName("domain");
			try{
			// for statement to get all sheets
			for (int sheetCount = 0; sheetCount < sheets.length; sheetCount++) {
				String sheetName = sheets[sheetCount].getName();
	
				// for statement to get all domain validation rules
				for (int domainCount = 0; domainCount < domainList.getLength(); domainCount++) {
	
					Element domain = (Element)domainList.item(domainCount);
					if (domain.getAttributes().getNamedItem("name").getNodeValue().equals(sheetName)) {
						int rowTotal = sheets[sheetCount].getRows();
	
						if (rowTotal > 0) {
							String[] cellName = new String[sheets[sheetCount].getRow(0).length];
	
							// for statement to get all rows from excel sheet
							for (int rowCount = 0; rowCount < rowTotal; rowCount++) {
								Cell[] cellList = sheets[sheetCount].getRow(rowCount);
	
								// for statement to get all cell values from excel rows 
								for (int cellCount = 0; cellCount < cellList.length; cellCount++) {
									if (rowCount == 0) {
										cellName[cellCount] = cellList[cellCount].getContents();
									} else {
										String cellValue = cellList[cellCount].getContents();
	
										NodeList fieldList = domain.getElementsByTagName("field");
										// for statement to get all field validation rules
										for (int fieldCount = 0; fieldCount < fieldList.getLength(); fieldCount++) {
											
											Element field = (Element)fieldList.item(fieldCount);
											
											NamedNodeMap fieldAttributes = field.getAttributes();
											
											if (fieldAttributes.getNamedItem("name").getNodeValue().equals(cellName[cellCount])) {
												
												if(fieldAttributes.getNamedItem("maxlength") != null){
													int maxlength = Integer.parseInt(fieldAttributes.getNamedItem("maxlength").getNodeValue());
													String hasError = checkMaxLength(maxlength, cellValue, cellName[cellCount]);
													
													if(!"".equals(hasError) && hasError != null){
														DataValidation dataValidation = new DataValidation(idCount + "", sheetName, rowCount + "", hasError);
														resultList.add(dataValidation);
														idCount++;
													}
												}
												
												if(fieldAttributes.getNamedItem("minlength") != null){
													int minlength = Integer.parseInt(fieldAttributes.getNamedItem("minlength").getNodeValue());
													String hasError = checkMinLength(minlength, cellValue, cellName[cellCount]);
													
													if(!"".equals(hasError) && hasError != null){
														DataValidation dataValidation = new DataValidation(idCount + "", sheetName, rowCount + "", hasError);
														resultList.add(dataValidation);
														idCount++;
													}
												}
												
												if(fieldAttributes.getNamedItem("key") != null){
													String name = fieldAttributes.getNamedItem("name").getNodeValue();
													
													if("RoleId".equals(name))
														roleIds.add(cellValue);
													
													if("GroupId".equals(name))
														groupIds.add(cellValue);
													
													if("UserId".equals(name))
														userIds.add(cellValue);
													
													if("ResourceId".equals(name))
														resourceIds.add(cellValue);
													
													if("TimeId".equals(name))
														timeIds.add(cellValue);
													
													if("ViewId".equals(name))
														viewIds.add(cellValue);
												}
												
												if(fieldAttributes.getNamedItem("foreignKey") != null){
													String foreignKey = fieldAttributes.getNamedItem("foreignKey").getNodeValue();
													String hasError = checkForeignKey(foreignKey, cellValue);
													
													if(!"".equals(hasError) && hasError != null){
														DataValidation dataValidation = new DataValidation(idCount + "", sheetName, rowCount + "", hasError);
														resultList.add(dataValidation);
														idCount++;
													}
												}
												
												if(fieldAttributes.getNamedItem("unique") != null){
													String hasError = isUnique(sheetName, cellValue);
													
													if(!"".equals(hasError) && hasError != null){
														DataValidation dataValidation = new DataValidation(idCount + "", sheetName, rowCount + "", hasError);
														resultList.add(dataValidation);
														idCount++;
													}
												}
												
												if(fieldAttributes.getNamedItem("required") != null){
													String hasError = isRequired(cellValue, cellName[cellCount]);
													
													if(!"".equals(hasError) && hasError != null){
														DataValidation dataValidation = new DataValidation(idCount + "", sheetName, rowCount + "", hasError);
														resultList.add(dataValidation);
														idCount++;
													}
												}
												
												if(fieldAttributes.getNamedItem("option") != null){
													String options = fieldAttributes.getNamedItem("option").getNodeValue();
													String hasError = checkOption(cellValue, options, cellName[cellCount]);
													
													if(!"".equals(hasError) && hasError != null){
														DataValidation dataValidation = new DataValidation(idCount + "", sheetName, rowCount + "", hasError);
														resultList.add(dataValidation);
														idCount++;
													}
												}
												
												if(fieldAttributes.getNamedItem("numeric") != null){
													String hasError = isNumeric(cellValue, cellName[cellCount]);
													
													if(!"".equals(hasError) && hasError != null){
														DataValidation dataValidation = new DataValidation(idCount + "", sheetName, rowCount + "", hasError);
														resultList.add(dataValidation);
														idCount++;
													}
												}
											}
										}
									}
								}
							}
						}
	
					}
					// invoke object save method
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return resultList;
	}

	public String isUnique(String sheetName, String Id) throws Exception{
		String hasError = "";
		boolean exist = false;
		
		
		if ("Users".equals(sheetName)) {
			if (usersService.exists(Id)) {
				exist = true;
			}
		} else if ("Groups".equals(sheetName)) {
			if (groupsService.exists(Id)) {
				exist = true;
			}
		} else if("Roles".equals(sheetName) ){
			if(rolesService.exists(Id)){
				exist = true;
			}
		} else if("RestrictedTimes".equals(sheetName)){
			if(restrictedTimesService.exists(Id)){
				exist = true;
			}
		} else if("SecuredResources".equals(sheetName)){
			if(securedResourcesService.exists(Id)){
				exist = true;
			}
		} else if("View".equals(Id)){
			if(viewResourcesService.exists(Id)){
				exist = true;
			}
		}
		
		if(exist){
			hasError += "ID " + Id + " already exist.";
		}
		
		return hasError;
	}

	public String checkMaxLength(int maxlength, String cellValue, String cellName){
		String hasError = "";
		
		if(cellValue.length() > maxlength){
			hasError += "Max length of " + cellName + " is " + maxlength + ".";
		}
		
		return hasError;
	}

	public String checkMinLength(int minlength, String cellValue, String cellName){
		String hasError = "";
		
		if(cellValue.length() < minlength){
			hasError += "Minimun length of " + cellName + " is " + minlength + ".";
		}
		
		return hasError;
	}

	public String isRequired(String cellValue, String cellName){
		String hasError = "";
		
		if("".equals(cellValue) || cellValue == null){
			hasError += cellName + " is required field. ";
		}
		
		return hasError;
	}

	public String checkOption(String cellValue, String options, String cellName){
		String hasError = "";
		String[] optionList = options.split(",");
		boolean isMatching = false;
		
		for(int i = 0 ; i < optionList.length ; i++){
			if(optionList[i].equals(cellValue)){
				isMatching = true;
				break;
			}
		}
		
		if(!isMatching && !"".equals(cellValue)){
			hasError += "Invalid valus in " + cellName;
		}
		
		return hasError;
	}

	public String isNumeric(String cellValue, String cellName){
		String hasError = "";
		
		if(!StringUtil.isNumeric(cellValue) && !"".equals(cellValue)){
			hasError += cellName + " must be number format. ";
		}
		
		return hasError;
	}

	private String checkForeignKey(String foreignKey, String cellValue){
		if("".equals(cellValue)){
			return "";
		}
		
		String hasError = "";
		String[] valueList = cellValue.split(",");
		Boolean[] isMatched = new Boolean[valueList.length];
		List foreignKeyList;
		
		for(int i = 0 ; i < isMatched.length ; i++){
			isMatched[i] = false;
		}
		
		for(int i = 0 ; i < valueList.length ; i++){
			if("RoleId".equals(foreignKey)){
				foreignKeyList = roleIds;
			}
			else if("GroupId".equals(foreignKey)){
				foreignKeyList = groupIds;
			}
			else if("UserId".equals(foreignKey)){
				foreignKeyList = userIds;
			}
			else if("TimeId".equals(foreignKey)){
				foreignKeyList = timeIds;
			}
			else if("ResourceId".equals(foreignKey)){
				foreignKeyList = resourceIds;
			}
			else if("ViewId".equals(foreignKey)){
				foreignKeyList = viewIds;
			} else{
				foreignKeyList = new ArrayList();
			}
			
			for(int j = 0 ; j < foreignKeyList.size() ; j++){
				String foreignKeyValue = (String)foreignKeyList.get(j);
				
				if(foreignKeyValue.equals(valueList[i])){
					isMatched[i] = true;
				}
			}
		}
		
		for(int i = 0 ; i < isMatched.length ; i++){
			if(!isMatched[i]){
				hasError += "The foreign key value of \"" + cellValue + "\" not exists. ";
				break;
			}
		}
		
		return hasError;
	}

	private Document getDocument(File file) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document document = null;

		builder = factory.newDocumentBuilder();
		document = builder.parse(file);

		return document;
	}

}
