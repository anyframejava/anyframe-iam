package anyframe.iam.admin.common.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import anyframe.core.properties.IPropertiesService;

@Controller
public class TopLayerController {
	
	@Resource(name = "propertiesService")
	private IPropertiesService propteriesService; 

	@RequestMapping("/common/changeSystemName.do")
	public String changeSystemName(HttpSession session, Model model, 
			@RequestParam(value = "systemName") String systemName) throws Exception{
		session.setAttribute("systemName", systemName);
		
		model.addAttribute("data", systemName);
		
		return "jsonView";
	}
	
	@RequestMapping("/common/goToplayer.do")
	public String goTopLayer(Model model, HttpSession session) throws Exception{
		
		String[] systemNames = this.propteriesService.getStringArray("SYSTEM_NAMES");
		model.addAttribute("systemNames", systemNames);
		
		if(session.getAttribute("systemName") == null)
			session.setAttribute("systemName", systemNames[0]);
		
		return "forward:/layouts/top.jsp";
	}
}
