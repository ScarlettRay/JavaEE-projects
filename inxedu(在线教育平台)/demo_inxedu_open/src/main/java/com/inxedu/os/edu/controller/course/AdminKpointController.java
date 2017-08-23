package com.inxedu.os.edu.controller.course;


import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.util.MD5;
import com.inxedu.os.common.util.ObjectUtils;
import com.inxedu.os.edu.constants.enums.WebSiteProfileType;
import com.inxedu.os.edu.entity.kpoint.CourseKpoint;
import com.inxedu.os.edu.entity.kpoint.CourseKpointDto;
import com.inxedu.os.edu.service.course.CourseKpointService;
import com.inxedu.os.edu.service.website.WebsiteProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CourseKpoint 课程章节 管理
 * @author www.inxedu.com
 */
@Controller
@RequestMapping("/admin")
public class AdminKpointController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(AdminKpointController.class);

	// 章节列表
	private static final String kpointList = getViewPath("/admin/kpoint/kpoint_list");// 章节列表

    // 绑定变量名字和属性，参数封装进类
    @InitBinder("courseKpoint")
    public void initBinderCourseKpoint(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("courseKpoint.");
    }

    @Autowired
    private CourseKpointService courseKpointService;
    @Autowired
	private WebsiteProfileService websiteProfileService;


    /**
	 * 修改视频节点的父节点
	 * @param parentId
	 * @param kpointId
	 * @return
     */
    @RequestMapping("/kpoint/updateparentid/{parentId}/{kpointId}")
    public Map<String,Object> updateKpointParentId(@PathVariable("parentId") int parentId,@PathVariable("kpointId") int kpointId){
    	Map<String,Object> json = new HashMap<String,Object>();
    	try{
    		courseKpointService.updateKpointParentId(kpointId, parentId);
    		json = this.setJson(true, null, null);
    	}catch (Exception e) {
    		this.setAjaxException(json);
			logger.error("updateKpointParentId()---error",e);
		}
    	return json;
    }
    
    /**
     * 删除视频节点
     * @param kpointIds 视频ID串
     * @return Map<String,Object>
     */
    @RequestMapping("/kpoint/deletekpoint/{kpointIds}")
    @ResponseBody
    public Map<String,Object> deleteKpoint(@PathVariable("kpointIds") String kpointIds){
    	Map<String,Object> json = new HashMap<String,Object>();
    	try{
    		courseKpointService.deleteKpointByIds(kpointIds);
    		json = this.setJson(true, null, null);
    	}catch (Exception e) {
    		this.setAjaxException(json);
			logger.error("deleteKpoint()---error",e);
		}
    	return json;
    }
    
    /**
     * 修改视频节点
     * @param courseKpoint 
     * @return Map<String,Object>
     */
    @RequestMapping("/kpoint/updateKpoint")
    @ResponseBody
    public Map<String,Object> updateKpoint(@ModelAttribute("courseKpoint") CourseKpoint courseKpoint){
    	Map<String,Object> json = new HashMap<String,Object>();
    	try{
    		courseKpointService.updateKpoint(courseKpoint);
    		json = this.setJson(true, null, courseKpoint);
    	}catch (Exception e) {
    		this.setAjaxException(json);
			logger.error("updateKpoint()---error",e);
		}
    	return json;
    }
    
    /**
     * 通过过ID，查询章节详情
     * @param kpointId 章节ID
     * @return Map<String,Object>
     */ 
    @RequestMapping("/kpoint/getkpoint/{kpointId}")
    @ResponseBody
    public Map<String,Object> queryCourseKpointById(@PathVariable("kpointId") int kpointId){
    	Map<String,Object> json = new HashMap<String,Object>();
    	try{
    		CourseKpointDto kpoint = courseKpointService.queryCourseKpointById(kpointId);
    		json = this.setJson(true, null, kpoint);
    	}catch (Exception e) {
			this.setAjaxException(json);
			logger.error("queryCourseKpointById()--error",e);
		}
    	return json;
    }
    
    /**
     * 创建节点视频
     * @param courseKpoint
     * @return Map<String,Object>
     */
    @RequestMapping("/kpoint/addkpoint")
    @ResponseBody
    public Map<String,Object> addKpoint(@ModelAttribute("courseKpoint") CourseKpoint courseKpoint){
    	Map<String,Object> json = new HashMap<String,Object>();
    	try{
    		courseKpoint.setAddTime(new Date());
    		courseKpoint.setFree(1);//1免费
    		courseKpointService.addCourseKpoint(courseKpoint);
    		json = this.setJson(true, null, courseKpoint);
    	}catch (Exception e) {
    		this.setAjaxException(json);
			logger.error("addKpoint()--error",e);
		}
    	return json;
    }

    /**
     * 课程的视频列表
     */
    @RequestMapping("/kpoint/list/{courseId}")
    public ModelAndView showKpointList(HttpServletRequest request, @PathVariable("courseId") int courseId) {
    	ModelAndView model = new ModelAndView();
    	try {
    		model.setViewName(kpointList);
    		List<CourseKpoint> kpointList = courseKpointService.queryCourseKpointByCourseId(courseId);
			if(ObjectUtils.isNotNull(kpointList)){
				for(CourseKpoint ck:kpointList){
					ck.setContent("");
				}
			}
    		model.addObject("kpointList", gson.toJson(kpointList));
    		model.addObject("courseId", courseId);
    	} catch (Exception e) {
            logger.error("showKpointList()---error", e);
            model.setViewName(this.setExceptionRequest(request, e));
        }
        return model;
    }
    
    /**
     *  生成cc视频参数按照 THQS 算法处理
     */
    @RequestMapping("/ajax/kpoint/ccVideoTHQSData")
    @ResponseBody
    public Object ccVideoTHQSData(HttpServletRequest request){
    	Map<String,Object> json = new HashMap<String,Object>();
    	try{
    		//cc视频配置
    		Map<String,Object> ccConfigMap=(Map<String,Object>)websiteProfileService.getWebsiteProfileByType(WebSiteProfileType.cc.toString()).get("cc");
    		//上传视频名
    		String filename=request.getParameter("filename");
    		filename=URLEncoder.encode(filename,"UTF-8");
    		String infoStr="description="+"inxedu_upload_"+filename//视频描述
					+"&tag=null"//视频标签 	可选
					+"&title="+"inxedu_upload_"+filename//视频标题
					+"&userid="+(String)ccConfigMap.get("ccappID")//用户ID 	必选
					+"&time="+System.currentTimeMillis()/1000L//当前时间的 Unix  时间戳
					;
			String infoStrSalt=infoStr+"&salt="+(String)ccConfigMap.get("ccappKEY");// Spark API Key 值
																																	   
			String hash=MD5.getMD5(infoStrSalt);
			infoStr+="&hash="+hash;
    		json = this.setJson(true, infoStr, null);
    	}catch (Exception e) {
    		this.setAjaxException(json);
			logger.error("deleteKpoint()---error",e);
			json = this.setJson(false, "", null);
		}
    	return json;
    }


}