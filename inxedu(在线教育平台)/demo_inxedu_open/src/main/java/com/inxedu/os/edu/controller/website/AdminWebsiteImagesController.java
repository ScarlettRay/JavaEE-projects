package com.inxedu.os.edu.controller.website;


import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.common.util.WebUtils;
import com.inxedu.os.edu.entity.website.WebSiteImagesType;
import com.inxedu.os.edu.entity.website.WebsiteImages;
import com.inxedu.os.edu.service.website.WebSiteImagesTypeService;
import com.inxedu.os.edu.service.website.WebsiteImagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *广告图管理
 *@author www.inxedu.com
 */
@Controller
@RequestMapping("/admin")
public class AdminWebsiteImagesController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AdminWebsiteImagesController.class);
    //广告图service
	@Autowired
	private WebsiteImagesService websiteImagesService;
	@Autowired
	private WebSiteImagesTypeService webSiteImagesTypeService;

	// 创建群 绑定变量名字和属性，把参数封装到类
	@InitBinder("websiteImages")
	public void initBinderWebsiteImages(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("websiteImages.");
	}
	/**
	 * 添加广告图
	 */
	@RequestMapping("/website/addImages")
	public ModelAndView addWebsiteImages(@ModelAttribute("websiteImages") WebsiteImages websiteImages,HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		try {
			model.setViewName("redirect:/admin/website/imagesPage");
			// 判断广告图路径和广告图关键是否不为空
			if (websiteImages.getImagesUrl() != null&&!websiteImages.getImagesUrl().equals("")) {
                //如果链接地址为空则 默认为 /
				if(websiteImages.getLinkAddress()==null || websiteImages.getLinkAddress().trim().length()==0){
					websiteImages.setLinkAddress("/");
				}
                // 插入广告图
				websiteImagesService.creasteImage(websiteImages);
			}
		} catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("addWebsiteImages()---error", e);
		}
		return model;
	}
	/**
	 * 跳转添加广告图页面
	 */
	@RequestMapping("/website/doAddImages")
	public ModelAndView doAddWebsiteImages(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		try{
			//添加广告图
			model.setViewName(getViewPath("/admin/website/images/websiteImages_add"));
			List<WebSiteImagesType> typeList = webSiteImagesTypeService.queryAllTypeList();
			model.addObject("typeList", typeList);
		}catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("doAddWebsiteImages()--error",e);
		}
		return model;
	}
	/**
	 * 广告图分页列表
	 */
	@RequestMapping("/website/imagesPage")
	public ModelAndView getWebsiteImagesPage(@ModelAttribute("websiteImages") WebsiteImages websiteImages,@ModelAttribute("page") PageEntity page,HttpServletRequest request){
		ModelAndView model = new ModelAndView();
        //跳转的jsp页面路径//广告图列表
		model.setViewName(getViewPath("/admin/website/images/websiteImages_list"));
		try{
            //查询广告图数据
			List<Map<String,Object>> websiteImagesList = websiteImagesService.queryImagePage(websiteImages, page);
            //存放数据
			model.addObject("websiteImagesList",websiteImagesList);
			model.addObject("page",page);
			//广告图分类
			List<WebSiteImagesType> typeList = webSiteImagesTypeService.queryAllTypeList();
			model.addObject("typeList", typeList);
			request.getSession().setAttribute("imageListUri", WebUtils.getServletRequestUriParms(request));
		}catch (Exception e) {
			logger.error("AdminWebsiteImagesController.getWebsiteImagesPage--广告图分页列表出错", e);
			return new ModelAndView(setExceptionRequest(request, e));
		}
		return model;
	}
	/**
	 * 删除广告图
	 */
	@RequestMapping("/website/delImages")
	public ModelAndView deleteWebsiteImages(HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		try{
			String[] imageIdArr = request.getParameterValues("imageId");
			if(imageIdArr!=null && imageIdArr.length>0){
				String ids="";
				for(int i=0;i<imageIdArr.length;i++){
					if(i<imageIdArr.length-1){
						ids+=imageIdArr[i]+",";
					}else{
						ids+=imageIdArr[i];
					}
				}
				//id删除广告图
				websiteImagesService.deleteImages(ids);
			}
			Object uri = request.getSession().getAttribute("imageListUri");
			if(uri!=null){
				model.setViewName("redirect:"+uri.toString());
			}else{
				model.setViewName("redirect:/admin/website/imagesPage");
			}
		}catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("deleteWebsiteImages()---error",e);
		}
		return model;
	}
	/**
	 * 跳转广告图修改
	 */
	@RequestMapping("/website/doUpdateImages/{id}")
	public ModelAndView doUpdateImages(@PathVariable int id,HttpServletRequest request){
		ModelAndView model = new ModelAndView();
        //跳转的jsp页面路径//修改广告图
		model.setViewName(getViewPath("/admin/website/images/websiteImages_update"));
		try{
			WebsiteImages websiteImages = websiteImagesService.queryImageById(id);
			model.addObject("websiteImages", websiteImages);
			List<WebSiteImagesType> typeList = webSiteImagesTypeService.queryAllTypeList();
			model.addObject("typeList", typeList);
		}catch (Exception e) {
			logger.error("doUpdateImages()--error",e);
			model.setViewName(this.setExceptionRequest(request, e));
		}
		return model;
	}
	/**
	 * 更新广告图
	 */
	@RequestMapping("/website/updateImages")
	public ModelAndView updatewebsiteImages(WebsiteImages websiteImages,HttpServletRequest request){
		ModelAndView model =new ModelAndView();
		try{
			Object uri = request.getSession().getAttribute("imageListUri");
			if(uri!=null){
				model.setViewName("redirect:"+uri.toString());
			}else{
				model.setViewName("redirect:/admin/website/imagesPage");
			}
			if(websiteImages!=null){
				if(websiteImages.getLinkAddress()==null || websiteImages.getLinkAddress().trim().length()==0){
					websiteImages.setLinkAddress("/");
				}
                //更新广告图
				websiteImagesService.updateImage(websiteImages);
			}
		}catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("updatewebsiteImages()--error",e);
		}
		return model;
	}
	
}
