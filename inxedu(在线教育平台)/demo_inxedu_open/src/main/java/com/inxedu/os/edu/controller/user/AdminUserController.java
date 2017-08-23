package com.inxedu.os.edu.controller.user;

import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.common.exception.BaseException;
import com.inxedu.os.common.util.*;
import com.inxedu.os.edu.entity.letter.MsgSystem;
import com.inxedu.os.edu.entity.user.QueryUser;
import com.inxedu.os.edu.entity.user.User;
import com.inxedu.os.edu.entity.user.UserLoginLog;
import com.inxedu.os.edu.service.letter.MsgReceiveService;
import com.inxedu.os.edu.service.letter.MsgSystemService;
import com.inxedu.os.edu.service.user.UserLoginLogService;
import com.inxedu.os.edu.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 前台学员
 * @author www.inxedu.com
 */
@Controller
@RequestMapping("/admin/user")
public class AdminUserController extends BaseController{
	
	private static Logger logger = LoggerFactory.getLogger(AdminUserController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private UserLoginLogService userLoginLogService;
	@Autowired
	private MsgSystemService msgSystemService;
	@Autowired
	private MsgReceiveService msgReceiveService;

	@InitBinder({"user"})
	public void initUser(WebDataBinder dinder){
		dinder.setFieldDefaultPrefix("user.");
	}
	@InitBinder({"queryUser"})
	public void initQueryUser(WebDataBinder dinder){
		dinder.setFieldDefaultPrefix("queryUser.");
	}
	
	/**
	 * 查询用户登录目录
	 * @param request
	 * @param userId 用户ID
	 * @param page 分页条件
	 * @return
	 */
	@RequestMapping("/lookuserlog/{userId}")
	public ModelAndView lookUserLog(HttpServletRequest request,@PathVariable("userId") int userId,@ModelAttribute("page") PageEntity page){
		ModelAndView model = new ModelAndView();
		try{
			model.setViewName(getViewPath("/admin/user/user-loginlog"));//学员用户登录日志
			page.setPageSize(20);
			List<UserLoginLog> logList = userLoginLogService.queryUserLogPage(userId, page);
			model.addObject("logList", logList);
			model.addObject("page", page);
			model.addObject("userId", userId);
		}catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("lookLog()---error",e);
		}
		return model;
	}
	
	/**
	 * 启用或禁用学员帐号
	 * @return Map<String,Object>
	 */
	@RequestMapping("/updateuserstate")
	@ResponseBody
	public Map<String,Object> updateState(@ModelAttribute("user") User user){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			userService.updateUserStates(user);
			json = this.setJson(true, null, null);
		}catch (Exception e) {
			this.setAjaxException(json);
			logger.error("updateState()--error",e);
		}
		return json;
	}
	
	/**
	 * 修改用户密码
	 */
	@RequestMapping("/updateUserPwd")
	@ResponseBody
	public Map<String,Object> initUpdateUser(HttpServletRequest request,@ModelAttribute("user") User user){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			if(user.getPassword()==null || user.getPassword().trim().length()==0 || !WebUtils.isPasswordAvailable(user.getPassword())){
				json = this.setJson(false, "密码由字母和数字组成且≥6位≤16位", null);
				return json;
			}
			String password = request.getParameter("passwords")==null?"":request.getParameter("passwords");
			if(!user.getPassword().equals(password)){
				json = this.setJson(false, "两次密码不一至！", null);
				return json;
			}
			user.setPassword(MD5.getMD5(user.getPassword()));
			userService.updateUserPwd(user);
			json = this.setJson(true, "修改成功", null);
		}catch (Exception e) {
			this.setAjaxException(json);
			logger.error("initUpdateUser()--error",e);
		}
		return json;
	}
	/**
	 * 分页查询学员列表
	 */
	@RequestMapping("/getuserList")
	public ModelAndView queryUserList(HttpServletRequest request,@ModelAttribute("queryUser")QueryUser queryUser,@ModelAttribute("page") PageEntity page){
		ModelAndView model = new ModelAndView();
		try{
			model.setViewName(getViewPath("/admin/user/user-list"));//学员列表页面
			page.setPageSize(15);
			List<User> userList = userService.queryUserListPage(queryUser, page);
			model.addObject("userList", userList);
			model.addObject("page", page);
		}catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("queryUserList()--error",e);
		}
		return model;
	}
	
	/**
     * 跳转到发消息页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/letter/toSendSystemMessages")
    public ModelAndView senSystemMessages(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(getViewPath("/admin/user/to_send_systemMessage"));// 发送系统消息页面
        return modelAndView;
    }
    
    /**
     * 跳转到 批量  发消息页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/letter/toSendSystemMessagesBatch")
    public ModelAndView senSystemMessagesBatch(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(getViewPath("/admin/user/to_send_systemMessage_batch"));// 发送系统消息页面
        return modelAndView;
    }
    
    /**
     * 发送系统消息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/letter/sendJoinGroup")
    @ResponseBody
    public Map<String, Object> sendSystemInform(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String content = request.getParameter("content");// 发送系统消息的内容
            MsgSystem msgReceive = new MsgSystem();
            msgReceive.setContent(content);// 添加站内信的内容
            msgReceive.setUpdateTime(new Date());// 更新时间s
            msgReceive.setAddTime(new Date());// 添加时间
            msgSystemService.addMsgSystem(msgReceive);
			//msgReceiveService.addSystemMessage(content);
            map.put("message", "success");
        } catch (Exception e) {
            logger.error("AdminUserController.sendSystemInform", e);
            setExceptionRequest(request, e);
        }
        return map;
    }
    
    /**
     * 批量发送系统消息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/letter/sendJoinGroupBatch")
    @ResponseBody
    public Map<String, Object> sendJoinGroupBatch(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        	String userEmails=request.getParameter("userEmails");// 发送系统消息的用户邮箱
        	String content = request.getParameter("content");// 发送系统消息的内容
        	if (ObjectUtils.isNull(userEmails)) {
        		map.put("message", "用户邮箱不能为空");
        		return map;
			}
        	String userEmailsArr[]=userEmails.split(",");
        	for(int i=0;i<userEmailsArr.length;i++){
        		if(ObjectUtils.isNotNull(userEmailsArr[i].trim())){
        			User user=userService.queryUserByEmailOrMobile(userEmailsArr[i].trim());
            		if (ObjectUtils.isNotNull(user)) {
            			msgReceiveService.addSystemMessageByCusId(content,Long.valueOf(user.getUserId()));
    				}
        		}
        	}
            map.put("message", "success");
        } catch (Exception e) {
            logger.error("AdminUserController.sendJoinGroupBatch", e);
            setExceptionRequest(request, e);
            map.put("message", "系统错误,请稍后重试");
        }
        return map;
    }
    
    /**
	 * 用户导出
	 */
	@RequestMapping("/export")
	public void userListExport(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("queryUser")QueryUser queryUser) {
		try {
			//指定文件生成路径
			String dir = request.getSession().getServletContext().getRealPath("/excelfile/user");
			//文件名
			String expName = "学员信息_" + DateUtils.getStringDateShort();
			//表头信息
	        String[] headName = { "ID","邮箱","手机","用户名","昵称","性别","年龄", "注册时间","状态"};

	        //拆分为一万条数据每Excel，防止内存使用太大
			PageEntity page=new PageEntity();
			page.setPageSize(10000);
			userService.queryUserListPage(queryUser, page);
			int num=page.getTotalPageSize();//总页数
			List<File> srcfile = new ArrayList<File>();//生成的excel的文件的list
			for(int i=1;i<=num;i++){//循环生成num个xls文件
				page.setCurrentPage(i);
				List<User> userList = userService.queryUserListPage(queryUser, page);
				List<List<String>> list=userJoint(userList);
				File file = FileExportImportUtil.createExcel(headName, list, expName+"_"+i,dir);
				srcfile.add(file);
			}
	        FileExportImportUtil.createRar(response, dir, srcfile, expName);//生成的多excel的压缩包
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 学员信息excel格式拼接
	 * @return
	 */
	public List<List<String>> userJoint(List<User> userList){
		List<List<String>> list = new ArrayList<List<String>>();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int i = 0; i < userList.size(); i++) {
			List<String> small = new ArrayList<String>();
			small.add(userList.get(i).getUserId() + "");
			small.add(userList.get(i).getEmail());
			small.add(userList.get(i).getMobile());
			small.add(userList.get(i).getUserName());
			small.add(userList.get(i).getShowName());
			if (userList.get(i).getSex() == 0) {
				small.add("--");
			} else if (userList.get(i).getSex() == 1) {
				small.add("男");
			} else {
				small.add("女");
			}
			small.add(String.valueOf(userList.get(i).getAge()));
			small.add(format.format(userList.get(i).getCreateTime()));
			if (userList.get(i).getIsavalible() == 1) {
				small.add("正常");
			} else if (userList.get(i).getIsavalible() == 2) {
				small.add("冻结");
			}
			list.add(small);
		}
		return list;
	}
	
	// 批量开通
	@RequestMapping("/toBatchOpen")
	public String toBatchOpen() {
		return getViewPath("/admin/user/batchOpen");// 批量开通界面
	}
	
	/**
	 * 批量开通学员账号
	 * 
	 * @param request
	 * @param myfile
	 * @return
	 */
	@RequestMapping("/importExcel")
	public ModelAndView importProcess(HttpServletRequest request, @RequestParam("myFile") MultipartFile myfile,@RequestParam("mark") Integer mark) {
		ModelAndView modelandView = new ModelAndView();
		try {
			logger.info("myFile:" + myfile.getName());
			String msg = userService.updateImportExcel(request,myfile,mark);
			request.setAttribute("msg", msg);
			if(msg==null||msg.equals("")){
				modelandView.setViewName("/common/success");
			}else{
				modelandView.setViewName("/common/msg_error");
			}
		} catch (BaseException e) {
            logger.error("AdminUserController.importExcel", e);
            request.setAttribute("msg", e.getMessage());
            return new ModelAndView("/common/msg_error");
        } catch (Exception e) {
        	logger.error("AdminUserController.importExcel", e);
        	request.setAttribute("msg", e.getMessage());
        	return new ModelAndView("/common/msg_error");
		}
		return modelandView;
	}

	/**
	 * 用户选择页（小页面弹出）
	 */
	@RequestMapping("/select_userlist/{type}")
	public ModelAndView selectUserList(@ModelAttribute User user, @ModelAttribute("page") PageEntity page, @PathVariable("type") int type) {
		ModelAndView modelandView = new ModelAndView();
		// 设置返回页面
		modelandView.setViewName(getViewPath("/admin/user/select_user_list"));
		try {
			// 查询学员列表
			List<User> list = userService.getUserListPage(user, page);
			// 把参数放到modelAndView中
			modelandView.addObject("list", list);
			modelandView.addObject("page", page);
			modelandView.addObject("type", type);// 1 短信 2邮箱
		} catch (Exception e) {
			logger.error("AdminUserController.userList", e);
		}
		return modelandView;
	}

}
