package anyframe.iam.admin.dataupload.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import anyframe.common.Page;
import anyframe.core.idgen.IIdGenerationService;
import anyframe.core.properties.IPropertiesService;
import anyframe.iam.admin.common.web.IAMDomainValidator;
import anyframe.iam.admin.dataupload.service.DataUploadService;
import anyframe.iam.admin.domain.DataUpload;
import anyframe.iam.admin.vo.CommonSearchVO;

@Controller
public class AnnotationDataUploadController {

	@Resource(name = "dataUploadService")
	private DataUploadService dataUploadService;
	
	@Resource(name = "propertiesService")
	private IPropertiesService propteriesService; 
	
	@Resource(name = "idGenerationServiceDataUpload")
	private IIdGenerationService idGenerationServiceDataUpload;
	
	@Resource(name = "iAMDomainValidation")
	private IAMDomainValidator iAMDomainValidation;
	
	@RequestMapping("/dataupload/dataupload.do")
	public String dataUpload() throws Exception{
		return "dataupload/datauploadlist";
	}
	
	@RequestMapping("/dataupload/datauploaddetail.do")
	public String dataUploadDetail(HttpSession session) throws Exception{
		session.removeAttribute("validationResult");
		return "dataupload/datauploaddetail";
	}
	
	@RequestMapping("/dataupload/aftervalidation.do")
	public String afterValidation() throws Exception{
		return "dataupload/datauploaddetail";
	}
	
	@RequestMapping("/dataupload/listData.do")
	public String listData(CommonSearchVO searchVO, Model model) throws Exception{
		
		Page resultPage = dataUploadService.getList(searchVO);

		model.addAttribute("page", resultPage.getCurrentPage());
		model.addAttribute("total", resultPage.getMaxPage() + "");
		model.addAttribute("records", resultPage.getTotalCount());
		model.addAttribute("rows", resultPage.getList());
		return "jsonView";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/dataupload/uploadfile.do")
	public String uploadFile(@RequestParam("filedata") MultipartFile file, Model model, HttpSession session) throws Exception{
		String destinationDir = this.propteriesService.getString("DESTINATION_DIR");
		if("".equals(destinationDir) || destinationDir == null){
			destinationDir = "\\TEMP_EXCEL_FILE_DIR";
		}
		File destination = new File(destinationDir);
		if (!destination.exists())
			destination.mkdir();
		
		String fileName = file.getOriginalFilename();
		
		File tempFile = File.createTempFile("temp", fileName, destination);
		FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(tempFile));
		
		List resultList = iAMDomainValidation.doValidator(tempFile);
		tempFile.delete();
		
		if(resultList.size() > 0){
			session.setAttribute("validationResult", resultList);
			return "forward:/dataupload/aftervalidation.do";
		}

		String nextFileId = idGenerationServiceDataUpload.getNextStringId();
		
		File savedFile = new File(destination, nextFileId + fileName);
		savedFile.createNewFile();
		
		FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(
				savedFile));
		
		DataUpload dataUpload = new DataUpload();
		dataUpload.setFileId(nextFileId);
		dataUpload.setFileName(fileName);
		dataUpload.setPath(destinationDir);
		String createDate = anyframe.common.util.DateUtil.getCurrentTime("yyyyMMdd");
		dataUpload.setCreateDate(createDate);
		
		dataUploadService.save(dataUpload);
		
		return "dataupload/datauploadlist";
	}
	
	@RequestMapping("/dataupload/validationlist.do")
	@SuppressWarnings("unchecked")
	public String validationList(
			@RequestParam("page") int page,
			@RequestParam("rows") int rows,
			Model model, HttpSession session) throws Exception{
		
		List resultList = (List)session.getAttribute("validationResult");
		
		if(session.getAttribute("validationResult") == null){
			resultList = new ArrayList();
			
			model.addAttribute("page", page);
			model.addAttribute("total", 0);
			model.addAttribute("records", resultList.size());
			model.addAttribute("rows", resultList);
			
			return "jsonView";
		}
		
		List pageList = new ArrayList();
		for(int i = ((page-1) * rows) ; i < page * rows ; i++){
			if(i < resultList.size())
				pageList.add(resultList.get(i));
		}
		
		model.addAttribute("page", page);
		model.addAttribute("total", (int)(resultList.size() / rows) + 1);
		model.addAttribute("records", resultList.size());
		model.addAttribute("rows", pageList);
		
		return "jsonView";
	}
	
	@RequestMapping("/dataupload/dosnapshot.do")
	public String doSnapshot(SessionStatus status) throws Exception{
		dataUploadService.doSnapshot();
		status.setComplete();
		
		return "forward:/dataupload/listData.do";
	}
	
	@RequestMapping("/dataupload/doapply.do")
	public String doApply(@RequestParam(value = "fileId") String[] fileId, Model model) throws Exception{
		
		boolean result = true;
		
		dataUploadService.applyExcel(fileId[0]);
		
		model.addAttribute("msg", result);
		
		return "jsonView";
	}
	
	@RequestMapping("/dataupload/downloadexcel.do")
	public void downloadExcel(@RequestParam("fileId") String fileId, HttpServletResponse response) throws Exception{
		DataUpload dataUpload = dataUploadService.get(fileId);
		
		String fileName = dataUpload.getFileId() + dataUpload.getFileName();
		String dir = dataUpload.getPath();
		
		File targetDir = new File(dir);
		File targetFile = new File(targetDir, fileName);
		
		if(targetFile.exists()){

			byte buff[] = new byte[2048]; 
			int bytesRead;

			try {
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition","attachment; filename="+ fileName); 
				FileInputStream fin = new java.io.FileInputStream(targetFile); 
				BufferedInputStream bis = new BufferedInputStream(fin);
				ServletOutputStream fout = response.getOutputStream(); 
				BufferedOutputStream bos = new BufferedOutputStream(fout);

				while((bytesRead = bis.read(buff)) != -1) { 
					  bos.write(buff, 0, bytesRead); 
				  }
				bos.flush();

				fin.close();
				fout.close(); 
				bis.close();
				bos.close();

			} catch(IOException e){
				response.setContentType("text/html");
			}
		} else {
			throw new Exception("File Not Exist");
		}
	}
	
}
