package com.inxedu.os.app.controller.websiteImages;

import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.edu.entity.website.WebsiteImages;
import com.inxedu.os.edu.service.website.WebsiteImagesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author www.inxedu.com
 */
@Controller
@RequestMapping("/webapp")
public class AppWebsiteImagesController extends BaseController{
	private static Logger logger=Logger.getLogger(AppWebsiteImagesController.class);
	
	
	@Autowired
	private WebsiteImagesService websiteImagesService;
	
	/**
	 * banner
	 */
	@RequestMapping("/websiteImages")
	@ResponseBody
	public Map<String, Object> websiteImages(HttpServletRequest request){
		Map<String, Object> json=new HashMap<String, Object>();
		try{
			// 获得banner图
			Map<String, List<WebsiteImages>> websiteImages = websiteImagesService.queryImagesByType();
			json=this.setJson(true, "成功", websiteImages);
		}catch(Exception e){
			json=this.setJson(false, "异常", null);
			logger.error("toSubjectList()--error",e);
		}
		return json;
	}
	
}
