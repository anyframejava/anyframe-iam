package anyframe.iam.admin.common.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import anyframe.core.properties.IPropertiesService;

/**
 * Annotation TopLayer Controller 
 * @author youngmin.jo
 *
 */
@Controller
public class TopLayerController {
	
	@Resource(name = "propertiesService")
	private IPropertiesService propteriesService; 

	/**
	 * set systemName value at session
	 * @param session HttpSession
	 * @param model Model
	 * @param systemName system name
	 * @return jsonView
	 * @throws Exception fail to set system name
	 */
	@RequestMapping("/common/changeSystemName.do")
	public String changeSystemName(HttpSession session, Model model, 
			@RequestParam(value = "systemName") String systemName) throws Exception{
		session.setAttribute("systemName", systemName);
		
		model.addAttribute("data", systemName);
		
		return "jsonView";
	}
	
	/**
	 * set system name at session and move to top layer
	 * @param model Model
	 * @param session HttpSession
	 * @return move to /common/top.jsp
	 * @throws Exception fail to set session or move to top layer
	 */
	@RequestMapping("/common/goToplayer.do")
	public String goTopLayer(Model model, HttpSession session) throws Exception{
		
		String[] systemNames = this.propteriesService.getStringArray("SYSTEM_NAMES");
		model.addAttribute("systemNames", systemNames);
		
		if(session.getAttribute("systemName") == null)
			session.setAttribute("systemName", systemNames[0]);
		
		return "/common/top";
	}
}
