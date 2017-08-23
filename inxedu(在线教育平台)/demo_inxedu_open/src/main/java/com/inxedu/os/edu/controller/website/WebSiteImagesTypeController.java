package com.inxedu.os.edu.controller.website;

import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.edu.entity.website.WebSiteImagesType;
import com.inxedu.os.edu.service.website.WebSiteImagesTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 广告图类型
 * @author www.inxedu.com
 */
@Controller
@RequestMapping("/admin/imagetype")
public class WebSiteImagesTypeController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(WebSiteImagesTypeController.class);

	@Autowired
	private WebSiteImagesTypeService webSiteImagesTypeService;
	
	@InitBinder({"type"})
	public void intitType(WebDataBinder binder){
		binder.setFieldDefaultPrefix("type.");
	}
	
	/**
	 * 查询图片类型列表
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/getlist")
	public ModelAndView queryImageList(HttpServletRequest request){
		ModelAndView model =new ModelAndView();
		try{
			model.setViewName(getViewPath("/admin/website/images/imageType-list"));
			List<WebSiteImagesType> typeList = webSiteImagesTypeService.queryAllTypeList();
			model.addObject("typeList", typeList);
		}catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("queryImageList()--error",e);
		}
		return model;
	}
	
	/**
	 * 删除图片类型
	 * @param request
	 * @param typeId 图片类型ID
	 * @return ModelAndView
	 */
	@RequestMapping("/deletetype/{typeId}")
	public ModelAndView deleteType(HttpServletRequest request,@PathVariable("typeId") int typeId){
		ModelAndView model =new ModelAndView();
		try{
			model.setViewName("redirect:/admin/imagetype/getlist");
			webSiteImagesTypeService.deleteTypeById(typeId);
		}catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("deleteType()--error",e);
		}
		return model;
	}
	
	/**
	 * 修改图片类型
	 * @param type
	 * @return Map<String,Object>
	 */
	@RequestMapping("/updateType")
	@ResponseBody
	public Map<String,Object> updateType(@ModelAttribute("type") WebSiteImagesType type){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			webSiteImagesTypeService.updateType(type);
			json = this.setJson(true, null, null);
		}catch (Exception e) {
			this.setAjaxException(json);
			logger.error("updateType()--error",e);
		}
		return json;
	}
	
	/**
	 * 添加图片类型
	 * @param request
	 * @param type 
	 * @return ModelAndView
	 */
	@RequestMapping("/addtype")
	public ModelAndView addType(HttpServletRequest request,@ModelAttribute("type") WebSiteImagesType type){
		ModelAndView model =new ModelAndView();
		try{
			model.setViewName("redirect:/admin/imagetype/getlist");
			type.setTypeName("新建图片类型");
			webSiteImagesTypeService.createImageType(type);
		}catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("addType()--error",e);
		}
		return model;
	}
}
