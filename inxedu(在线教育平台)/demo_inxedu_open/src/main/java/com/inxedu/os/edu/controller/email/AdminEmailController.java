package com.inxedu.os.edu.controller.email;

import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.common.util.ObjectUtils;
import com.inxedu.os.common.util.SingletonLoginUtils;
import com.inxedu.os.common.util.StringUtils;
import com.inxedu.os.edu.controller.user.AdminUserController;
import com.inxedu.os.edu.entity.email.UserEmailMsg;
import com.inxedu.os.edu.entity.system.SysUser;
import com.inxedu.os.edu.service.email.EmailThread;
import com.inxedu.os.edu.service.email.UserEmailMsgService;
import com.inxedu.os.edu.service.mobile.SmsBatchThread;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 邮件
 * @author www.inxedu.com
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminEmailController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(AdminUserController.class);
	
	private static final String sendEmailMsgList = getViewPath("/admin/email/sendEmail_list");// 邮件管理页面
	private static final String sendEmailMsgInfo = getViewPath("/admin/email/sendEmail_info");// 邮件管理页面
	private static final String toSendEmailMsg = getViewPath("/admin/email/to_sendEmailMsg");// 发送邮件页面
	private static final String progressbar = getViewPath("/admin/email/progressbar");// 进度
	
	@Autowired
	private UserEmailMsgService userEmailMsgService;
	
	@InitBinder("userEmailMsg")
	public void initBinderEmail(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userEmailMsg.");
	}
	
	/**
	 * 邮箱管理
	 */
	@RequestMapping("/email/sendEmaillist")
	public String querySendEmailMsgList(Model model, HttpServletRequest request, @ModelAttribute("userEmailMsg") UserEmailMsg userEmailMsg, @ModelAttribute("page") PageEntity page) {
		try {
			page.setPageSize(10);
			List<UserEmailMsg> list = userEmailMsgService.queryUserEmailMsgList(userEmailMsg, page);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("querySendEmailMsgList", e);
		}
		return sendEmailMsgList;
	}
	
	/**
	 * 查询邮件详情
	 */
	@RequestMapping("/email/sendEmailMsgInfo/{id}")
	public String querySendEmailMsgInfo(Model model, @PathVariable("id") Long id) {
		try {
			UserEmailMsg userEmailMsg = userEmailMsgService.queryUserEmailMsgById(id);
			model.addAttribute("userEmailMsg", userEmailMsg);
		} catch (Exception e) {
			logger.error("querySendMobleMsgInfo", e);
		}
		return sendEmailMsgInfo;
	}
	
	/**
	 * 删除发送邮件记录
	 */
	@RequestMapping("/email/sendEmail/del")
	public String delSendEmailMsg(HttpServletRequest request, @RequestParam("id") Long id) {
		try {
			userEmailMsgService.delUserEmailMsgById(id);
		} catch (Exception e) {
			logger.error("delSendEmailMsg", e);
		}
		return "redirect:/admin/email/sendEmaillist";
	}

	
	/**
	 * 跳转发送邮件页面
	 * 
	 * @return
	 */
	@RequestMapping("/email/toEmailMsg")
	public String toSendEmailMsg() {
		return toSendEmailMsg;
	}
	
	/**
	 * 导入Excel 解析
	 */
	@RequestMapping("/email/importMsgExcel/{type}")
	@ResponseBody
	public Map<String, Object> importExcel(HttpServletRequest request, @RequestParam("myFile") MultipartFile myFile, @PathVariable("type") int type) {
		Map<String, Object> json = null;
		try {
			logger.info("myFile:" + myFile.getName());
			HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
			// 只读取sheet1
			HSSFSheet sheet = wookbook.getSheet("Sheet1");
			int rows = sheet.getLastRowNum();// Excel行数
			String mobileStr = "";
			for (int i = 1; i <= rows; i++) {
				// 读取左上端单元格
				HSSFRow row = sheet.getRow(i);
				// 行不为空
				if (row != null) {
					// **读取cell**
					HSSFCell cell = row.getCell((short) 0);
					if (cell != null) {
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_FORMULA:
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							DecimalFormat df = new DecimalFormat("#");
							mobileStr += df.format(cell.getNumericCellValue()) + ",";
							break;
						case HSSFCell.CELL_TYPE_STRING:
							mobileStr += cell.getStringCellValue().trim() + ",";
							break;
						default:
							break;
						}
					}
				}
			}
			// 获得页面的
			if (request.getParameter("numerStr") != null && request.getParameter("numerStr") != "") {
				mobileStr += request.getParameter("numerStr");
			}
			Map<String, Object> returnMap = null;
			if (type == 1) {// 短信页面
				returnMap = userEmailMsgService.checkMobile(mobileStr);
				if (Boolean.parseBoolean(returnMap.get("flag").toString()) == false) {
					json = this.setJson(false, returnMap.get("errorMobile").toString(), "");
					return json;
				} else {
					json = this.setJson(true, "", returnMap.get("mobileList"));
				}
			} else {// 邮箱页面
				returnMap = userEmailMsgService.checkEmail(mobileStr);
				if (Boolean.parseBoolean(returnMap.get("flag").toString()) == false) {
					json = this.setJson(false, returnMap.get("errorMessage").toString(), "");
					return json;
				} else {
					json = this.setJson(true, "", returnMap.get("returnList"));
				}
			}
		} catch (Exception e) {
			logger.error("importExcel", e);
			json = this.setJson(false, "Excel导入错误", null);
			return json;
		}
		return json;
	}
	
	/**
	 * 发送邮件
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/email/sendEmailMsg")
	@ResponseBody
	public Map<String, Object> sendEmailMsg(HttpServletRequest request) {
		Map<String, Object> json = null;
		try {
			String linksman = request.getParameter("linksman");// 获取联系人
			String title = request.getParameter("title");// 获取标题
			String content = request.getParameter("content");// 获取内容

			int type = Integer.valueOf(request.getParameter("type"));// 邮件类型
			String startTime = request.getParameter("startTime");// 发送时间
			Date starttime = new Date();
			// 如果是定时短信发送时间要大于当前时间
			if (type == 2) {
				if ("".equals(startTime)) {
					json = this.setJson(false, "请输入发送时间", "");
					return json;
				}
				SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				starttime = formatDate.parse(startTime);
				if (!starttime.after(new Date())) {
					json = this.setJson(false, "定时发送的时间要大于当前日期", "");
					return json;
				}
			}

			if (StringUtils.isNotEmpty(linksman) && StringUtils.isNotEmpty(content) && StringUtils.isNotEmpty(title)) {
				// 验证邮箱
				Map<String, Object> returnMap = userEmailMsgService.checkEmail(linksman);
				// 错误信息
				String errorEmail = returnMap.get("errorMessage").toString();
				if (Boolean.parseBoolean(returnMap.get("flag").toString()) == false) {
					json = this.setJson(false, errorEmail, "");
					return json;
				} else {
					SysUser user = SingletonLoginUtils.getLoginSysUser(request);
					List<UserEmailMsg> emailMsgList = new ArrayList<UserEmailMsg>();
					UserEmailMsg userEmailMsg = new UserEmailMsg();
					userEmailMsg.setId(0);
					userEmailMsg.setTitle(title);
					userEmailMsg.setContent(content);
					userEmailMsg.setEmail(returnMap.get("returnList").toString());
					userEmailMsg.setType(type);
					userEmailMsg.setSendTime(starttime);
					if (type == 1) {
						// 发送邮件
						userEmailMsgService.batchSendEmail(returnMap.get("returnList").toString().split(","), content, title, 3);
						// emailService.sendBatchMail(returnMap.get("returnList").toString().split(","),
						// content, title);
						userEmailMsg.setStatus(1);
					} else {
						userEmailMsg.setStatus(2);

					}
					if (ObjectUtils.isNotNull(user)) {
						userEmailMsg.setUserId(Long.parseLong(""+user.getUserId()));
					} else {
						userEmailMsg.setUserId(0L);
					}
					userEmailMsg.setCreateTime(new Date());
					emailMsgList.add(userEmailMsg);
					userEmailMsgService.addUserEmailMsg(emailMsgList);
					errorEmail = "发送成功";
				}
				json = this.setJson(Boolean.parseBoolean(returnMap.get("flag").toString()), errorEmail, "");
			} else {
				json = this.setJson(false, "联系人、标题或内容不能为空", null);
			}
		} catch (Exception e) {
			logger.error("sendEmailMsg", e);
		}
		return json;
	}
	
	/**
	 * 进度条
	 */
	@RequestMapping("/email/progressbar")
	public String progressbar(HttpServletRequest request, @RequestParam("type") int type) {
		try {
			request.setAttribute("type", type);
		} catch (Exception e) {
			logger.error("progressbar", e);
		}
		return progressbar;
	}
	
	/**
	 * 查询进度
	 */
	@RequestMapping("/query/progressbar")
	@ResponseBody
	public Object queryprogressbar(HttpServletRequest request, @RequestParam("type") int type) {
		Map<String, Object> json = null;
		try {
			if (type == 1) {
				EmailThread emailThread = new EmailThread();
				double sumNum = Double.valueOf(emailThread.getSumNum());
				double listNum = Double.valueOf(emailThread.getList().size());
				Map map = new HashMap();
				map.put("sumNum", sumNum);
				map.put("listNum", listNum);
				json = this.setJson(true, "", map);
			} else {
				SmsBatchThread smsBatchThread = new SmsBatchThread();
				double sumNum = Double.valueOf(smsBatchThread.getSumNum());
				double listNum = Double.valueOf(smsBatchThread.getList().size());
				Map map = new HashMap();
				map.put("sumNum", sumNum);
				map.put("listNum", listNum);
				json = this.setJson(true, "", map);
			}
		} catch (Exception e) {
			logger.error("queryprogressbar", e);
		}
		return json;
	}
	
	/**
	 * 修改未发送的邮件
	 */
	@RequestMapping("/email/sendEmailMsg/update")
	@ResponseBody
	public Object updateSendEmailMsgInfo(HttpServletRequest request, UserEmailMsg userEmailMsg) {
		Map<String, Object> json = null;
		try {
			String sendTime = request.getParameter("sendTime");
			if ("".equals(sendTime)) {
				json = this.setJson(false, "请输入发送时间", "");
				return json;
			}
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date sendtime = formatDate.parse(sendTime);
			if (!sendtime.after(new Date())) {
				json = this.setJson(false, "定时发送的时间要大于当前日期", "");
				return json;
			}

			userEmailMsgService.updateUserEmailMsgById(userEmailMsg);
			json = this.setJson(true, "成功", "");
		} catch (Exception e) {
			logger.error("querySendMobleMsgInfo", e);
		}
		return json;
	}
	
}
