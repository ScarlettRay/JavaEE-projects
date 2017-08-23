package com.inxedu.os.edu.controller.website;

import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.common.util.WebUtils;
import com.inxedu.os.edu.entity.website.WebsiteCourse;
import com.inxedu.os.edu.entity.website.WebsiteCourseDetail;
import com.inxedu.os.edu.entity.website.WebsiteCourseDetailDTO;
import com.inxedu.os.edu.service.website.WebsiteCourseDetailService;
import com.inxedu.os.edu.service.website.WebsiteCourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推荐课程  Controller
 * @author www.inxedu.com
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminWebsiteCourseDetailController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(AdminWebsiteCourseDetailController.class);

	@Autowired
	private WebsiteCourseDetailService websiteCourseDetailService;
	@Autowired
	private WebsiteCourseService websiteCourseService;

	@InitBinder({"dto"})
	public void initBinder(WebDataBinder binder){
		binder.setFieldDefaultPrefix("dto.");
	} 
	/**
	 * 查询推荐课程列表
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/detail/list")
	public ModelAndView queryDetail(HttpServletRequest request,@ModelAttribute("dto") WebsiteCourseDetailDTO dto,@ModelAttribute("page") PageEntity page){
		ModelAndView model =new ModelAndView();
		try{
			model.setViewName(getViewPath("/admin/website/course/websiteCourseDetail_list"));
			//推荐课程列表
			List<WebsiteCourseDetailDTO> dtoList = websiteCourseDetailService.queryCourseDetailPage(dto, page);
			model.addObject("dtoList", dtoList);
			model.addObject("page", page);
			//推荐课程分类
			List<WebsiteCourse> websiteCourseList = websiteCourseService.queryWebsiteCourseList();
			model.addObject("websiteCourseList", websiteCourseList);
			request.getSession().setAttribute("detailListUri", WebUtils.getServletRequestUriParms(request));
		}catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("queryDetail()---error",e);
		}
		return model;
	}
	
	/**
	 * 删除推荐课程
	 * @param id 记录ID
	 * @return ModelAndView
	 */
	@RequestMapping("/detail/deletedeail/{id}")
	public ModelAndView deleteDetail(HttpServletRequest request,@PathVariable("id") int id){
		ModelAndView model =new ModelAndView();
		try{
			websiteCourseDetailService.deleteDetailById(id);
			Object uri = request.getSession().getAttribute("detailListUri");
			if(uri!=null){
				model.setViewName("redirect:"+uri.toString());
			}else{
				model.setViewName("redirect:/admin/detail/list");
			}
		}catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("deleteDetail()---error",e);
		}
		return model;
	}
	
	/**
	 * 修改推荐课程排序数
	 * @param id 推荐课程ID
	 * @param sort 排序值
	 * @return Map<String,Object>
	 */
	@RequestMapping("/detail/updatesort/{id}/{sort}")
	@ResponseBody
	public Map<String,Object> updateSort(@PathVariable("id") int id,@PathVariable("sort") int sort){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			websiteCourseDetailService.updateSort(id, sort);
			json = this.setJson(true, null, null);
		}catch (Exception e) {
			this.setAjaxException(json);
			logger.error("updateSort()---error",e);
		}
		return json;
	}
	
	/**
	 * 添加推荐课程
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/detail/addrecommendCourse")
	@ResponseBody
	public Map<String,Object> addrecommendCourse(HttpServletRequest request){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			//课程ID串
			String courseIds = request.getParameter("courseIds");
			//推荐类型ID
			int recommendId = request.getParameter("recommendId")==null?0:Integer.parseInt(request.getParameter("recommendId"));
			//查询类型实体
			WebsiteCourse websiteCourse = websiteCourseService.queryWebsiteCourseById(recommendId);
			//查询该类型所属的推荐课程
			List<WebsiteCourseDetail> detailList = websiteCourseDetailService.queryDetailListByrecommendId(recommendId);
			//得到原有数量
			int count = detailList==null?0:detailList.size();
			if(courseIds==null || courseIds.trim().length()==0){
				json = this.setJson(false, "请选择课程", null);
				return json;
			}
			//得到没有重复的课程ID
			String[] courseArr = handleCourseId(detailList,courseIds);
			count = courseArr.length+count;
			
			if(count>websiteCourse.getCourseNum()){
				json = this.setJson(false, "["+websiteCourse.getName()+"]推荐类型最多只能添加["+websiteCourse.getCourseNum()+"]个课程", null);
				return json;
			}
			if(courseArr.length>0){
				StringBuffer val =new StringBuffer();
				for(int i=0;i<courseArr.length;i++){
					if(i<courseArr.length-1){
						val.append("(0,"+recommendId+","+courseArr[i]+",0),");
					}else{
						val.append("(0,"+recommendId+","+courseArr[i]+",0)");
					}
				}
				websiteCourseDetailService.createWebsiteCourseDetail(val.toString());
			}
			Object uri = request.getSession().getAttribute("detailListUri");
			if(uri!=null){
				json = this.setJson(true, null, uri.toString());
			}else{
				json = this.setJson(true, null, "/admin/detail/list");
			}
		}catch (Exception e) {
			this.setAjaxException(json);
			logger.error("addrecommendCourse()--error",e);
		}
		return json;
	}
	
	/**
	 * 处理重复课程ID
	 * @param detailList 原有推荐课程
	 * @param courseIds 当前要添加的课程ID串
	 * @return String[]课程ID数组
	 */
	private String[] handleCourseId(List<WebsiteCourseDetail> detailList,String courseIds){
		if(courseIds.trim().startsWith(",")){
			courseIds = courseIds.trim().substring(1,courseIds.trim().length());
		}
		if(courseIds.trim().endsWith(",")){
			courseIds = courseIds.trim().substring(0,courseIds.trim().length()-1);
		}
		String[] _arr = courseIds.split(",");
		
		List<String> arr=new ArrayList<String>();
		if(detailList!=null && detailList.size()>0){
			for(String id:_arr){
				int _index=0;
				for(WebsiteCourseDetail wcd : detailList){
					if(Integer.parseInt(id)==wcd.getCourseId()){
						_index=1;
					}
				}
				if(_index==0){
					arr.add(id);
				}
			}
			String[] courseArr =new String[arr.size()];
			arr.toArray(courseArr);
			return courseArr;
		}else{
			return _arr;
		}
	}
}
