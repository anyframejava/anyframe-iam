package anyframe.iam.admin.dataupload.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import anyframe.common.Page;
import anyframe.core.generic.service.impl.GenericServiceImpl;
import anyframe.core.idgen.IIdGenerationService;
import anyframe.core.properties.IPropertiesService;
import anyframe.iam.admin.common.IAMDataBinder;
import anyframe.iam.admin.dataupload.dao.DataUploadDao;
import anyframe.iam.admin.dataupload.service.DataUploadService;
import anyframe.iam.admin.domain.DataUpload;
import anyframe.iam.admin.domain.TempGroups;
import anyframe.iam.admin.domain.TempRestrictedTimes;
import anyframe.iam.admin.domain.TempRoles;
import anyframe.iam.admin.domain.TempSecuredResources;
import anyframe.iam.admin.domain.TempUsers;
import anyframe.iam.admin.domain.TempViewResources;
import anyframe.iam.admin.groups.service.GroupsService;
import anyframe.iam.admin.ids.service.IdsService;
import anyframe.iam.admin.restrictedtimes.service.RestrictedTimesService;
import anyframe.iam.admin.roles.service.RolesService;
import anyframe.iam.admin.securedresources.service.SecuredResourcesService;
import anyframe.iam.admin.users.service.UsersService;
import anyframe.iam.admin.viewresources.service.ViewResourcesService;
import anyframe.iam.admin.vo.CommonSearchVO;

public class DataUploadServiceImpl extends GenericServiceImpl<DataUpload, String> implements DataUploadService{

	private DataUploadDao dataUploadDao;
	
	private IPropertiesService propertiesService; 
	
	private IIdGenerationService idGenerationServiceSnapshot;
	
	private GroupsService groupsService;
	
	private RolesService rolesService;
	
	private UsersService usersService;
	
	private SecuredResourcesService securedResourcesService;
	
	private RestrictedTimesService restrictedTimesService;
	
	private ViewResourcesService viewResourcesService;
	
	private IdsService idsService;
	
	public void setIdsService(IdsService idsService) {
		this.idsService = idsService;
	}

	public DataUploadServiceImpl(DataUploadDao dataUploadDao){
		super(dataUploadDao);
		this.dataUploadDao = dataUploadDao;
	}
	
	public void setDataUploadDao(DataUploadDao dataUploadDao){
		this.dataUploadDao = dataUploadDao;
	}
	
	public void setIPropertiesService(IPropertiesService propertiesService){
		this.propertiesService = propertiesService;
	}
	
	public IPropertiesService getPropertiesService() {
		return propertiesService;
	}

	public void setPropertiesService(IPropertiesService propertiesService) {
		this.propertiesService = propertiesService;
	}

	public IIdGenerationService getIdGenerationServiceSnapshot() {
		return idGenerationServiceSnapshot;
	}

	public void setIdGenerationServiceSnapshot(
			IIdGenerationService idGenerationServiceSnapshot) {
		this.idGenerationServiceSnapshot = idGenerationServiceSnapshot;
	}

	public GroupsService getGroupsService() {
		return groupsService;
	}

	public void setGroupsService(GroupsService groupsService) {
		this.groupsService = groupsService;
	}

	public RolesService getRolesService() {
		return rolesService;
	}

	public void setRolesService(RolesService rolesService) {
		this.rolesService = rolesService;
	}

	public UsersService getUsersService() {
		return usersService;
	}

	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	public SecuredResourcesService getSecuredResourcesService() {
		return securedResourcesService;
	}

	public void setSecuredResourcesService(
			SecuredResourcesService securedResourcesService) {
		this.securedResourcesService = securedResourcesService;
	}

	public RestrictedTimesService getRestrictedTimesService() {
		return restrictedTimesService;
	}

	public void setRestrictedTimesService(
			RestrictedTimesService restrictedTimesService) {
		this.restrictedTimesService = restrictedTimesService;
	}

	public ViewResourcesService getViewResourcesService() {
		return viewResourcesService;
	}

	public void setViewResourcesService(ViewResourcesService viewResourcesService) {
		this.viewResourcesService = viewResourcesService;
	}

	public DataUpload save(DataUpload dataUpload) throws Exception{
		return dataUploadDao.save(dataUpload);
	}
	
	public Page getList(CommonSearchVO searchVO) throws Exception{
		return dataUploadDao.getList(searchVO);
	}
	
	public void doSnapshot() throws Exception{
		
		// File Name, Dir 지정
		String nextId = idGenerationServiceSnapshot.getNextStringId();
		String destinationDir = this.propertiesService.getString("DESTINATION_DIR");
		if("".equals(destinationDir) || destinationDir == null){
			destinationDir = "\\TEMP_EXCEL_FILE_DIR";
		}
		File targetDir = new File(destinationDir);
		if(!targetDir.exists()){
			targetDir.mkdir();
		}
		String workDate = anyframe.common.util.DateUtil.getCurrentTime("yyyyMMdd");
		File resultFile = new File(targetDir, nextId + "workDate" + workDate + ".xls");
		
		resultFile.createNewFile();
		WritableWorkbook workbook = Workbook.createWorkbook(resultFile);
		
		// Groups Data
		List<TempGroups> groupsList = groupsService.makeAllTempGroupsList();
		WritableSheet groupsSheet = workbook.createSheet("Groups", 0);
		groupsSheet = makeGroupsSheet(groupsSheet, groupsList);
		
		// Roles Data
		List<TempRoles> rolesList = rolesService.makeAllTempRolesList();
		WritableSheet roleSheet = workbook.createSheet("Roles", 1);
		roleSheet = makeRolesSheet(roleSheet, rolesList);
		
		// Users Data
		List<TempUsers> usersList = usersService.makeAllTempUsersList();
		WritableSheet userSheet = workbook.createSheet("Users", 2);
		userSheet = makeUsersSheet(userSheet, usersList);
		
		// SecuredResources Data
		List<TempSecuredResources> resourcesList = securedResourcesService.makeAllTempResourcesList();
		WritableSheet resourceSheet = workbook.createSheet("SecuredResources", 3);
		resourceSheet = makeResourcesSheet(resourceSheet, resourcesList);
		
		// RestrictedTimes Data
		List<TempRestrictedTimes> timesList = restrictedTimesService.makeAllTempRestrictedTimesList();
		WritableSheet timesSheet = workbook.createSheet("RestrictedTimes", 4);
		timesSheet = makeTimesSheet(timesSheet, timesList);
		
		// ViewResource Data
		List<TempViewResources> viewList = viewResourcesService.makeAllTempViewList();
		WritableSheet viewSheet = workbook.createSheet("ViewResources", 5);
		viewSheet = makeViewSheet(viewSheet, viewList);
		
		workbook.write();
		workbook.close();
		
		DataUpload dataUpload = new DataUpload();
		dataUpload.setFileId(nextId);
		dataUpload.setFileName("workDate" + workDate + ".xls");
		dataUpload.setPath(destinationDir);
		dataUpload.setCreateDate(workDate);
		dataUpload.setWorkDate(workDate);
		
		dataUploadDao.save(dataUpload);
	}
	
	@SuppressWarnings("unchecked")
	public void applyExcel(String fileId) throws Exception{
		
		doSnapshot();
		
		DataUpload dataUpload = get(fileId);
		
		String fileName = dataUpload.getFileId() + dataUpload.getFileName();
		String dir = dataUpload.getPath();
		
		File targetDir = new File(dir);
		File targetFile = new File(targetDir, fileName);
		
		if(targetFile.exists()){
			Workbook workbook = Workbook.getWorkbook(targetFile);

			String sheetNames[] = workbook.getSheetNames();
			Sheet sheets[] = workbook.getSheets();
			
			for (int i = 0; i < sheetNames.length; i++) {
				List objectList = IAMDataBinder.bindSheet(sheets[i]);
				List resultList = new ArrayList();
				
				if(sheetNames[i].equals("Groups")){
					groupsService.removeAllGroups();
					resultList = groupsService.save(objectList);
					idsService.updateNextId(resultList, "GROUPS");
				} else if(sheetNames[i].equals("Roles")){
					rolesService.removeAllRoles();
					resultList = rolesService.save(objectList);
					idsService.updateNextId(resultList, "ROLES");
				} else if(sheetNames[i].equals("Users")){
					usersService.removeAllUsers();
					usersService.save(objectList);
				} else if(sheetNames[i].equals("SecuredResources")){
					securedResourcesService.removeAllSecuredResources();
					resultList = securedResourcesService.save(objectList);
					idsService.updateNextId(resultList, "RESOURCE_METHOD");
				} else if(sheetNames[i].equals("ViewResources")){
					viewResourcesService.removeAllViewResources();
					resultList = viewResourcesService.save(objectList);
					idsService.updateNextId(resultList, "VIEW_RESOURCE");
				} else if(sheetNames[i].equals("RestrictedTimes")){
					restrictedTimesService.removeAllRestrictedTimes();
					resultList = restrictedTimesService.save(objectList);
					idsService.updateNextId(resultList, "TIMES");
				}
			}

		}
		String workDate = anyframe.common.util.DateUtil.getCurrentTime("yyyyMMdd");
		dataUpload.setWorkDate(workDate);
		save(dataUpload);
		
		updateIdsTable();
	}
	
	private void updateIdsTable() throws Exception{
		
	}
	
	private WritableSheet makeGroupsSheet(WritableSheet sheet, List<TempGroups> groupsList) throws Exception{
		
		Label label = null;
		
		// Make Group sheet label
		label = new Label(0, 0, "GroupId");
		sheet.addCell(label);

		label = new Label(1, 0, "GroupName");
		sheet.addCell(label);
		
		label = new Label(2, 0, "CreateDate");
		sheet.addCell(label);
		
		label = new Label(3, 0, "ModifyDate");
		sheet.addCell(label);
		
		label = new Label(4, 0, "ParentGroup");
		sheet.addCell(label);
		
		for(int i = 0 ; i < groupsList.size() ; i++){
			TempGroups tempGroups = groupsList.get(i);
			label = new Label(0, i + 1, tempGroups.getGroupId());
			sheet.addCell(label);
			label = new Label(1, i + 1, tempGroups.getGroupName());
			sheet.addCell(label);
			label = new Label(2, i + 1, tempGroups.getCreateDate());
			sheet.addCell(label);
			label = new Label(3, i + 1, tempGroups.getModifyDate());
			sheet.addCell(label);
			label = new Label(4, i + 1, tempGroups.getParentGroup());
			sheet.addCell(label);
		}
		
		return sheet;
	}
	
	private WritableSheet makeRolesSheet(WritableSheet sheet, List<TempRoles> rolesList) throws Exception{
		Label label = null;
		
		// Make Role sheet label
		label = new Label(0, 0, "RoleId");
		sheet.addCell(label);
		
		label = new Label(1, 0, "RoleName");
		sheet.addCell(label);
		
		label = new Label(2, 0, "Description");
		sheet.addCell(label);
		
		label = new Label(3, 0, "CreateDate");
		sheet.addCell(label);
		
		label = new Label(4, 0, "ModifyDate");
		sheet.addCell(label);
		
		label = new Label(5, 0, "ParentRoles");
		sheet.addCell(label);
		
		for(int i = 0 ; i < rolesList.size() ; i++){
			TempRoles tempRoles = rolesList.get(i);
			label = new Label(0, i + 1, tempRoles.getRoleId());
			sheet.addCell(label);
			label = new Label(1, i + 1, tempRoles.getRoleName());
			sheet.addCell(label);
			label = new Label(2, i + 1, tempRoles.getDescription());
			sheet.addCell(label);
			label = new Label(3, i + 1, tempRoles.getCreateDate());
			sheet.addCell(label);
			label = new Label(4, i + 1, tempRoles.getModifyDate());
			sheet.addCell(label);
			label = new Label(5, i + 1, tempRoles.getParentRoles());
			sheet.addCell(label);
		}
		return sheet;
	}
	
	private WritableSheet makeUsersSheet(WritableSheet sheet, List<TempUsers> usersList) throws Exception{
		Label label = null;
		
		// Make Role sheet label
		label = new Label(0, 0, "UserId");
		sheet.addCell(label);
		
		label = new Label(1, 0, "UserName");
		sheet.addCell(label);
		
		label = new Label(2, 0, "Password");
		sheet.addCell(label);
		
		label = new Label(3, 0, "Enabled");
		sheet.addCell(label);
		
		label = new Label(4, 0, "CreateDate");
		sheet.addCell(label);
		
		label = new Label(5, 0, "ModifyDate");
		sheet.addCell(label);
		
		label = new Label(6, 0, "GroupId");
		sheet.addCell(label);
		
		label = new Label(7, 0, "RoleId");
		sheet.addCell(label);
		
		for(int i = 0 ; i < usersList.size() ; i++){
			TempUsers tempUsers = usersList.get(i);
			label = new Label(0, i + 1, tempUsers.getUserId());
			sheet.addCell(label);
			label = new Label(1, i + 1, tempUsers.getUserName());
			sheet.addCell(label);
			label = new Label(2, i + 1, tempUsers.getPassword());
			sheet.addCell(label);
			label = new Label(3, i + 1, tempUsers.getEnabled());
			sheet.addCell(label);
			label = new Label(4, i + 1, tempUsers.getCreateDate());
			sheet.addCell(label);
			label = new Label(5, i + 1, tempUsers.getModifyDate());
			sheet.addCell(label);
			label = new Label(6, i + 1, tempUsers.getGroupId());
			sheet.addCell(label);
			label = new Label(7, i + 1, tempUsers.getRoleId());
			sheet.addCell(label);
		}
		
		return sheet;
	}
	
	private WritableSheet makeResourcesSheet(WritableSheet sheet, List<TempSecuredResources> resourceList) throws Exception{
		Label label = null;
		
		// Make Role sheet label
		label = new Label(0, 0, "ResourceId");
		sheet.addCell(label);
		
		label = new Label(1, 0, "ResourceName");
		sheet.addCell(label);
		
		label = new Label(2, 0, "ResourcePattern");
		sheet.addCell(label);
		
		label = new Label(3, 0, "Description");
		sheet.addCell(label);
		
		label = new Label(4, 0, "ResourceType");
		sheet.addCell(label);
		
		label = new Label(5, 0, "SortOrder");
		sheet.addCell(label);
		
		label = new Label(6, 0, "SystemName");
		sheet.addCell(label);
		
		label = new Label(7, 0, "RoleId");
		sheet.addCell(label);
		
		label = new Label(8, 0, "CreateDate");
		sheet.addCell(label);
		
		label = new Label(9, 0, "ModifyDate");
		sheet.addCell(label);
		
		for(int i = 0 ; i < resourceList.size() ; i++){
			TempSecuredResources tempResources = resourceList.get(i);
			label = new Label(0, i + 1, tempResources.getResourceId());
			sheet.addCell(label);
			label = new Label(1, i + 1, tempResources.getResourceName());
			sheet.addCell(label);
			label = new Label(2, i + 1, tempResources.getResourcePattern());
			sheet.addCell(label);
			label = new Label(3, i + 1, tempResources.getDescription());
			sheet.addCell(label);
			label = new Label(4, i + 1, tempResources.getResourceType());
			sheet.addCell(label);
			label = new Label(5, i + 1, tempResources.getSortOrder());
			sheet.addCell(label);
			label = new Label(6, i + 1, tempResources.getSystemName());
			sheet.addCell(label);
			label = new Label(7, i + 1, tempResources.getRoleId());
			sheet.addCell(label);
			label = new Label(8, i + 1, tempResources.getCreateDate());
			sheet.addCell(label);
			label = new Label(9, i + 1, tempResources.getModifyDate());
			sheet.addCell(label);
		}
		
		return sheet;
	}
	
	private WritableSheet makeTimesSheet(WritableSheet sheet, List<TempRestrictedTimes> timesList) throws Exception{
		Label label = null;
		
		// Make Role sheet label
		label = new Label(0, 0, "TimeId");
		sheet.addCell(label);
		
		label = new Label(1, 0, "TimeType");
		sheet.addCell(label);
		
		label = new Label(2, 0, "StartDate");
		sheet.addCell(label);
		
		label = new Label(3, 0, "StartTime");
		sheet.addCell(label);
		
		label = new Label(4, 0, "EndDate");
		sheet.addCell(label);
		
		label = new Label(5, 0, "EndTime");
		sheet.addCell(label);
		
		label = new Label(6, 0, "Description");
		sheet.addCell(label);
		
		label = new Label(7, 0, "SystemName");
		sheet.addCell(label);
		
		label = new Label(8, 0, "RoleId");
		sheet.addCell(label);
		
		label = new Label(9, 0, "ResourceId");
		sheet.addCell(label);
		
		label = new Label(10, 0, "TimesExclusionRoles");
		sheet.addCell(label);

		for(int i = 0 ; i < timesList.size() ; i++){
			TempRestrictedTimes tempRestrictedTimes = timesList.get(i);
			label = new Label(0, i + 1, tempRestrictedTimes.getTimeId());
			sheet.addCell(label);
			label = new Label(1, i + 1, tempRestrictedTimes.getTimeType());
			sheet.addCell(label);
			label = new Label(2, i + 1, tempRestrictedTimes.getStartDate());
			sheet.addCell(label);
			label = new Label(3, i + 1, tempRestrictedTimes.getStartTime());
			sheet.addCell(label);
			label = new Label(4, i + 1, tempRestrictedTimes.getEndDate());
			sheet.addCell(label);
			label = new Label(5, i + 1, tempRestrictedTimes.getEndTime());
			sheet.addCell(label);
			label = new Label(6, i + 1, tempRestrictedTimes.getDescription());
			sheet.addCell(label);
			label = new Label(7, i + 1, tempRestrictedTimes.getSystemName());
			sheet.addCell(label);
			label = new Label(8, i + 1, tempRestrictedTimes.getRoleId());
			sheet.addCell(label);
			label = new Label(9, i + 1, tempRestrictedTimes.getResourceId());
			sheet.addCell(label);
			label = new Label(10, i + 1, tempRestrictedTimes.getTimesExclusionRoles());
			sheet.addCell(label);
		}
		
		return sheet;
	}
	
	private WritableSheet makeViewSheet(WritableSheet sheet, List<TempViewResources> viewList) throws Exception{
		Label label = null;
		
		// Make Role sheet label
		label = new Label(0, 0, "ViewResourceId");
		sheet.addCell(label);
		
		label = new Label(1, 0, "Category");
		sheet.addCell(label);
		
		label = new Label(2, 0, "Description");
		sheet.addCell(label);
		
		label = new Label(3, 0, "ViewInfo");
		sheet.addCell(label);
		
		label = new Label(4, 0, "ViewName");
		sheet.addCell(label);
		
		label = new Label(5, 0, "ViewType");
		sheet.addCell(label);
		
		label = new Label(6, 0, "Visible");
		sheet.addCell(label);
		
		label = new Label(7, 0, "SystemName");
		sheet.addCell(label);
		
		label = new Label(8, 0, "ParentView");
		sheet.addCell(label);
		
		label = new Label(9, 0, "RefId");
		sheet.addCell(label);
		
		label = new Label(10, 0, "RefType");
		sheet.addCell(label);

		label = new Label(11, 0, "Permissions");
		sheet.addCell(label);
		
		for(int i = 0 ; i < viewList.size() ; i++){
			TempViewResources tempViewResources = viewList.get(i);
			label = new Label(0, i + 1, tempViewResources.getViewResourceId());
			sheet.addCell(label);
			label = new Label(1, i + 1, tempViewResources.getCategory());
			sheet.addCell(label);
			label = new Label(2, i + 1, tempViewResources.getDescription());
			sheet.addCell(label);
			label = new Label(3, i + 1, tempViewResources.getViewInfo());
			sheet.addCell(label);
			label = new Label(4, i + 1, tempViewResources.getViewName());
			sheet.addCell(label);
			label = new Label(5, i + 1, tempViewResources.getViewType());
			sheet.addCell(label);
			label = new Label(6, i + 1, tempViewResources.getVisible());
			sheet.addCell(label);
			label = new Label(7, i + 1, tempViewResources.getSystemName());
			sheet.addCell(label);
			label = new Label(8, i + 1, tempViewResources.getParentView());
			sheet.addCell(label);
			label = new Label(9, i + 1, tempViewResources.getRefId());
			sheet.addCell(label);
			label = new Label(10, i + 1, tempViewResources.getRefType());
			sheet.addCell(label);
			label = new Label(11, i + 1, tempViewResources.getPermissions());
			sheet.addCell(label);
		}
		
		return sheet;
	}
}
