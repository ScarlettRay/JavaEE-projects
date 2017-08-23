package com.inxedu.os.app.controller.user;

import com.inxedu.os.common.cache.EHCacheUtil;
import com.inxedu.os.common.constants.CacheConstans;
import com.inxedu.os.common.constants.CommonConstants;
import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.common.util.MD5;
import com.inxedu.os.common.util.StringUtils;
import com.inxedu.os.common.util.WebUtils;
import com.inxedu.os.edu.constants.enums.WebSiteProfileType;
import com.inxedu.os.edu.entity.course.CourseDto;
import com.inxedu.os.edu.entity.course.CourseFavorites;
import com.inxedu.os.edu.entity.course.FavouriteCourseDTO;
import com.inxedu.os.edu.entity.user.User;
import com.inxedu.os.edu.entity.user.UserLoginLog;
import com.inxedu.os.edu.service.course.CourseFavoritesService;
import com.inxedu.os.edu.service.course.CourseService;
import com.inxedu.os.edu.service.letter.MsgReceiveService;
import com.inxedu.os.edu.service.user.UserLoginLogService;
import com.inxedu.os.edu.service.user.UserService;
import com.inxedu.os.edu.service.website.WebsiteProfileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author www.inxedu.com
 */
@Controller
@RequestMapping("/webapp")
public class AppUserController extends BaseController{
	private static Logger logger=Logger.getLogger(AppUserController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserLoginLogService userLoginLogService;
	@Autowired
	private WebsiteProfileService websiteProfileService;
	@Autowired
	private MsgReceiveService msgReceiveService;
	@Autowired
	private CourseFavoritesService courseFavoritesService;
	@Autowired
	private CourseService courseService;
	
	/**
	 * 登录
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Map<String,Object> userLogin(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			if(!StringUtils.isNotEmpty(account)){
				json = this.setJson(false, "请输入登录帐号", null);
				return json;
			}
			if(!StringUtils.isNotEmpty(password)){
				json = this.setJson(false, "请输入登录密码", null);
				return json;
			}
			User user = userService.getLoginUser(account, MD5.getMD5(password));
			if(user==null){
				json = this.setJson(false, "帐号或密码错误", null);
				return json;
			}
			EHCacheUtil.remove(CacheConstans.WEB_USER_LOGIN_PREFIX+user.getUserId());
			if(user.getIsavalible()==2){
				json = this.setJson(false, "帐号已被禁用", null);
				return json;
			}
		
			
			
			UserLoginLog loginLog =new UserLoginLog();
			loginLog.setIp(WebUtils.getIpAddr(request));
			loginLog.setLoginTime(new Date());
			String userAgent = WebUtils.getUserAgent(request);
			if(StringUtils.isNotEmpty(userAgent)){
				loginLog.setOsName(userAgent.split(";")[1]);
				loginLog.setUserAgent(userAgent.split(";")[0]);
			}
			loginLog.setUserId(user.getUserId());
			userLoginLogService.createLoginLog(loginLog);
			json = this.setJson(true, "登录成功", user);
		}catch (Exception e) {
			json=this.setJson(false, "异常", null);
			logger.error("userLogin()--error",e);
		}
		return json;
	}
	
	/**
	 * 注册
	 */
	@RequestMapping("/createuser")
	@ResponseBody
	public Map<String,Object> createUser(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			String email=request.getParameter("email");//邮箱
			String mobile=request.getParameter("mobile");//手机
			String password=request.getParameter("password");//密码
			String confirmPwd = request.getParameter("confirmPwd");//再次输入的密码
			
			if(email==null || email.trim().length()==0 || !WebUtils.checkEmail(email, 50)){
				json = this.setJson(false, "请输入正确的邮箱号", null);
				return json;
			}
			if(userService.checkEmail(email.trim())){
				json = this.setJson(false, "该邮箱号已被使用", null);
				return json;
			}
			if(mobile==null || mobile.trim().length()==0 || !WebUtils.checkMobile(mobile)){
				json = this.setJson(false, "请输入正确的手机号", null);
				return json;
			}
			if(userService.checkMobile(mobile)){
				json = this.setJson(false, "该手机号已被使用", null);
				return json;
			}
			if(password==null || password.trim().length()==0 || !WebUtils.isPasswordAvailable(password)){
				json = this.setJson(false, "密码有字母和数字组合且≥6位≤16位", null);
				return json;
			}
			if(!password.equals(confirmPwd)){
				json = this.setJson(false, "两次密码不一致", null);
				return json;
			}
			User user=new User();
			user.setEmail(email);
			user.setMobile(mobile);
			user.setPassword(password);
			user.setCreateTime(new Date());
			user.setIsavalible(1);
			user.setPassword(MD5.getMD5(user.getPassword()));
			user.setMsgNum(0);
			user.setSysMsgNum(0);
			user.setLastSystemTime(new Date());
			userService.createUser(user);
			request.getSession().removeAttribute(CommonConstants.RAND_CODE);
			json = this.setJson(true, "注册成功", user);
			
			// 注册时发送系统消息
			Map<String, Object> websitemap = websiteProfileService.getWebsiteProfileByType(WebSiteProfileType.web.toString());
			Map<String, Object> web = (Map<String, Object>) websitemap.get("web");
			String company = web.get("company").toString();
			String conent = "欢迎来到" + company + ",希望您能够快乐的学习";
			msgReceiveService.addSystemMessageByCusId(conent, Long.valueOf(user.getUserId()));
			
			
		}catch (Exception e) {
			json = this.setJson(false, "异常", null);
			logger.error("createUser()--eror",e);
		}
		return json;
	}
	
	/**
	 * 获得用户
	 */
	@RequestMapping("/queryUserById")
	@ResponseBody
	public Map<String, Object> queryUserById(HttpServletRequest request){
		Map<String, Object> json=new HashMap<String, Object>();
		try{
			String id=request.getParameter("id");
			if(id==null||id.equals("")){
				json = this.setJson(false, "用户Id不能为空", null);
				return json;
			}
			User user = userService.queryUserById(Integer.parseInt(id));
			json=this.setJson(true, "获取用户成功", user);
		}catch(Exception e){
			json = this.setJson(false, "异常", null);
			logger.error("queryUserById()--eror",e);
		}
		return json;
	}
	
	/**
     * 收藏课程
     */
    @RequestMapping("/front/createfavorites")
    @ResponseBody
    public Map<String,Object> createFavorites(HttpServletRequest request){
    	Map<String,Object> json = new HashMap<String,Object>();
    	try{
    		//用户Id
    		String userId = request.getParameter("userId");
    		if(userId==null||userId.trim().equals("")){
    			json = this.setJson(false, "用户Id不能为空", null);
    			return json;
    		}
    		//课程Id
    		String courseId=request.getParameter("courseId");
    		if(courseId==null||courseId.trim().equals("")){
    			json = this.setJson(false, "课程Id不能为空", null);
    			return json;
    		}
    		//判断是否收藏过
    		boolean is = courseFavoritesService.checkFavorites(Integer.parseInt(userId), Integer.parseInt(courseId));
    		if(is){
    			json = this.setJson(false, "该课程你已经收藏过了！", null);
    			return json;
    		}
    		CourseFavorites courseFavorites=new CourseFavorites();
    		courseFavorites.setCourseId(Integer.parseInt(courseId));
    		courseFavorites.setUserId(Integer.parseInt(userId));
    		courseFavorites.setAddTime(new Date());
    		courseFavoritesService.createCourseFavorites(courseFavorites);
    		json = this.setJson(true, "收藏成功", null);
    	}catch (Exception e) {
    		json = this.setJson(false, "异常", null);
			logger.error("createFavorites()--error",e);
		}
    	return json;
    }
    
    /**
	 * 删除收藏
	 */
	@RequestMapping("/deleteFaveorite")
	@ResponseBody
	public Map<String, Object> deleteFavorite(HttpServletRequest request){
		Map<String, Object> json=new HashMap<String, Object>();
		try{
			String id=request.getParameter("id");
			if(id==null||id.trim().equals("")){
				json=setJson(false, "id不能为空", null);
				return json;
			}
			courseFavoritesService.deleteCourseFavoritesById(id);
			json=setJson(true, "取消收藏成功", null);
		}catch (Exception e) {
			json=setJson(false, "异常", null);
			logger.error("deleteFavorite()---error",e);
		}
		return json;
	}
	
	/**
	 * 我的课程
	 */
	@RequestMapping("/myCourse")
	@ResponseBody
	public Map<String, Object> myCourse(HttpServletRequest request,@ModelAttribute("page") PageEntity page){
		Map<String, Object> json=new HashMap<String, Object>();
		try{
			String currentPage=request.getParameter("currentPage");//当前页
			if(currentPage==null||currentPage.trim().equals("")){
				json=this.setJson(false, "页码不能为空", null);
				return json;
			}
			page.setCurrentPage(Integer.parseInt(currentPage));
			
			page.setPageSize(10);//每页显示多少条数据
			String pageSize=request.getParameter("pageSize");
			if(pageSize!=null){
				page.setPageSize(Integer.parseInt(pageSize));
			}
			
			String userId=request.getParameter("userId");//用户Id
			if(userId==null||userId.trim().equals("")){
				json=this.setJson(false, "用户Id不能为空", null);
				return json;
			}
			
			//查询我的课程
			List<CourseDto> courseList = courseService.queryMyCourseList(Integer.parseInt(userId), page);
			json=this.setJson(true, "查询成功--我的课程", courseList);
		}catch(Exception e){
			json=setJson(false, "异常", null);
			logger.error("myCourse()---error",e);
		}
		return json;
	}
	
	/**
	 * 我的收藏课程列表
	 */
	@RequestMapping("/myFavorites")
	@ResponseBody
	public Map<String, Object> myFavorites(HttpServletRequest request,@ModelAttribute("page") PageEntity page){
		Map<String, Object> json=new HashMap<String,Object>();
		try{
			String currentPage=request.getParameter("currentPage");//当前页
			if(currentPage==null||currentPage.trim().equals("")){
				json=this.setJson(false, "页码不能为空", null);
				return json;
			}
			page.setCurrentPage(Integer.parseInt(currentPage));
			
			page.setPageSize(10);//每页显示多少条数据
			String pageSize=request.getParameter("pageSize");
			if(pageSize!=null){
				page.setPageSize(Integer.parseInt(pageSize));
			}
			
			String userId=request.getParameter("userId");//用户Id
			if(userId==null||userId.trim().equals("")){
				json=this.setJson(false, "用户Id不能为空", null);
				return json;
			}
			List<FavouriteCourseDTO> favoriteList = courseFavoritesService.queryFavoritesPage(Integer.parseInt(userId), page);
			json=this.setJson(true, "查询成功--我的收藏课程列表", favoriteList);
		}catch (Exception e) {
			json=this.setJson(false, "异常", null);
			logger.error("myFavorites()---error",e);
		}
		return json;
	}
	
	/**
	 * 修改用户信息
	 */
	@RequestMapping("/updateUser")
	@ResponseBody
	public Map<String,Object> updateUserInfo(HttpServletRequest request){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			String userName=request.getParameter("userName");//姓名
			if(userName==null||userName.trim().equals("")){
				json=this.setJson(false, "姓名不能为空", null);
				return json;
			}
			String showName=request.getParameter("showName");//昵称
			if(showName==null||showName.trim().equals("")){
				json=this.setJson(false, "昵称不能为空", null);
				return json;
			}
			String sex=request.getParameter("sex");//性别 1男 2女
			if(sex==null||sex.trim().equals("")){
				json=this.setJson(false, "性别不能为空", null);
				return json;
			}
			String age=request.getParameter("age");//年龄
			if(age==null||age.trim().equals("")){
				json=this.setJson(false, "年龄不能为空", null);
				return json;
			}
			String userId=request.getParameter("userId");//用户Id
			if(userId==null||userId.trim().equals("")){
				json=this.setJson(false, "用户Id不能为空", null);
				return json;
			}
			User user=new User();
			user.setUserId(Integer.parseInt(userId));//用户Id
			user.setUserName(userName);//姓名
			user.setShowName(showName);//昵称
			user.setSex(Integer.parseInt(sex));//性别
			user.setAge(Integer.parseInt(age));//年龄
			userService.updateUser(user);//修改基本信息
			json = this.setJson(true, "修改成功", user);
		}catch (Exception e) {
			json=this.setJson(false, "异常", null);
			logger.error("updateUserInfo()---error",e);
		}
		return json;
	}
}
