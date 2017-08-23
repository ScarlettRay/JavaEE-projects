package com.inxedu.os.edu.controller.mobile;

import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.common.util.ObjectUtils;
import com.inxedu.os.common.util.SingletonLoginUtils;
import com.inxedu.os.common.util.StringUtils;
import com.inxedu.os.edu.controller.user.AdminUserController;
import com.inxedu.os.edu.entity.mobile.UserMobileMsg;
import com.inxedu.os.edu.entity.system.SysUser;
import com.inxedu.os.edu.service.email.UserEmailMsgService;
import com.inxedu.os.edu.service.mobile.UserMobileMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author www.inxedu.com
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminMobileController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(AdminUserController.class);
	
	private static final String sendMobileMsgList = getViewPath("/admin/mobile/sendMobile_list");// 短信记录列表
	private static final String sendMobileMsgInfo = getViewPath("/admin/mobile/sendMobile_info");// 短信记录详情页面
	private static final String toSendMobileMsg = getViewPath("/admin/mobile/to_sendMobileMsg");// 发送短信页面
	
	@Autowired
	private UserMobileMsgService userMobileMsgService;
	@Autowired
	private UserEmailMsgService userEmailMsgService;
	
	@InitBinder("userMobileMsg")
	public void initBinderMobile(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userMobileMsg.");
	}
	
	/**
	 * 短信记录列表
	 */
	@RequestMapping("/mobile/sendMsglist")
	public String querySendMobileMsgList(Model model, HttpServletRequest request, @ModelAttribute("userMobileMsg") UserMobileMsg userMobileMsg, @ModelAttribute("page") PageEntity page) {
		try {
			page.setPageSize(10);
			List<UserMobileMsg> list = userMobileMsgService.queryUserMobileMsgList(userMobileMsg, page);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("querySendMobileMsgList", e);
		}
		return sendMobileMsgList;
	}
	
	/**
	 * 查询短信详情
	 */
	@RequestMapping("/mobile/sendMsgInfo/{id}")
	public String querySendMobleMsgInfo(Model model, @PathVariable("id") Long id) {
		try {
			UserMobileMsg userMobileMsg = userMobileMsgService.queryUserMobileMsgById(id);
			model.addAttribute("userMobileMsg", userMobileMsg);
		} catch (Exception e) {
			logger.error("querySendMobleMsgInfo", e);
		}
		return sendMobileMsgInfo;
	}
	
	/**
	 * 删除短信
	 */
	@RequestMapping("/mobile/delMsgById/{id}")
	@ResponseBody
	public Map<String, Object> delUserMobileMsgById(Model model, @PathVariable("id") Long id) {
		Map<String, Object> json = null;
		try {
			userMobileMsgService.delUserMobileMsg(id);
			json = this.setJson(true, "删除成功", null);
		} catch (Exception e) {
			logger.error("delUserMobileMsgById", e);
			json = this.setJson(false, "删除失败！", null);
		}
		return json;
	}
	
	/**
	 * 修改短信
	 * 
	 * @return
	 */
	@RequestMapping("/mobile/updateUserMsg")
	@ResponseBody
	public Map<String, Object> updateUserMsg(HttpServletRequest request) {
		Map<String, Object> json = null;
		try {
			UserMobileMsg userMobileMsg = new UserMobileMsg();
			userMobileMsg.setId(Integer.parseInt(request.getParameter("msgId")));
			userMobileMsg.setContent(request.getParameter("content"));
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			userMobileMsg.setSendTime(df.parse(request.getParameter("sendTime")));
			userMobileMsgService.updateUserMobileMsg(userMobileMsg);
			json = this.setJson(true, "修改成功", null);
		} catch (Exception e) {
			logger.error("updateUserMsg", e);
			json = this.setJson(false, "修改失败", null);
		}
		return json;
	}
	
	/**
	 * 跳转发送短信页面
	 * 
	 * @return
	 */
	@RequestMapping("/mobile/toMsg")
	public String toSendMoblieMsg() {
		return toSendMobileMsg;
	}
	
	/**
	 * 发送短信
	 * @param request
	 * @return
     */
	@RequestMapping("/mobile/sendMsg")
	@ResponseBody
	public Map<String, Object> sendMobileMsg(HttpServletRequest request) {
		Map<String, Object> json = null;
		try {

			List<UserMobileMsg> msgList = new ArrayList<UserMobileMsg>();

			String linksman = request.getParameter("linksman");// 获取联系人
			String content = request.getParameter("content");// 获取内容
			Integer type = Integer.parseInt(request.getParameter("sendType"));// 发送方式

			Date now = new Date();
			Date sendTime = now;
			if (type == 2) {// 定时发送

				if (StringUtils.isEmpty(request.getParameter("sendTime"))) {
					json = this.setJson(false, "定时发送时间不能为空", "");
					return json;
				}
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				sendTime = df.parse(request.getParameter("sendTime"));// 定时发送时间
			}
			if (StringUtils.isNotEmpty(linksman) && StringUtils.isNotEmpty(content)) {
				// 验证手机
				Map<String, Object> returnMap = userEmailMsgService.checkMobile(linksman);
				// 错误信息
				String errorMobile = returnMap.get("errorMobile").toString();
				if (Boolean.parseBoolean(returnMap.get("flag").toString()) == false) {
					json = this.setJson(false, errorMobile, "");
					return json;
				} else {
					// 添加发送记录
					SysUser user = SingletonLoginUtils.getLoginSysUser(request);
					
					UserMobileMsg userMobileMsg = new UserMobileMsg();
					userMobileMsg.setSendTime(sendTime);
					if (type == 1) {// 正常发送
						userMobileMsg.setStatus(1);
					} else {// 定时发送
						userMobileMsg.setStatus(2);
					}
					userMobileMsg.setId(0);
					userMobileMsg.setType(type);

					userMobileMsg.setContent(content);
					userMobileMsg.setMobile(returnMap.get("mobileList").toString());
					if (ObjectUtils.isNotNull(user)) {
						userMobileMsg.setUserId(Long.valueOf(user.getUserId()));
					} else {
						userMobileMsg.setUserId(0L);
					}
					userMobileMsg.setCreateTime(now);
					msgList.add(userMobileMsg);
					// 添加记录 暂不发送
					userMobileMsgService.addUserMobileMsg(msgList);
					if (type == 1) {// 正常发送
						userMobileMsgService.batchSendMobileMsg(content, returnMap.get("mobileList").toString().split(","), 3);
					}
					errorMobile = "发送成功";
				}
				json = this.setJson(Boolean.parseBoolean(returnMap.get("flag").toString()), errorMobile, "");
			} else {
				json = this.setJson(false, "联系人或内容不能为空", null);
			}
		} catch (Exception e) {
			logger.error("sendMobileMsg", e);
		}
		return json;
	}
	
}
