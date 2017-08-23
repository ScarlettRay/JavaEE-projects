package com.inxedu.os.edu.service.impl.user;

import com.inxedu.os.common.cache.EHCacheUtil;
import com.inxedu.os.common.constants.CacheConstans;
import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.common.exception.BaseException;
import com.inxedu.os.common.util.MD5;
import com.inxedu.os.common.util.ObjectUtils;
import com.inxedu.os.common.util.StringUtils;
import com.inxedu.os.common.util.WebUtils;
import com.inxedu.os.edu.dao.user.UserDao;
import com.inxedu.os.edu.entity.user.QueryUser;
import com.inxedu.os.edu.entity.user.User;
import com.inxedu.os.edu.service.user.UserService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 前台学员
 * @author www.inxedu.com
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;

	public int createUser(User user) {
		return userDao.createUser(user);
	}

	
	public User queryUserById(int userId) {
		return userDao.queryUserById(userId);
	}

	
	public boolean checkMobile(String mobile) {
		int count = userDao.checkMobile(mobile);
		if(count>0){
			return true;
		}
		return false;
	}

	
	public boolean checkEmail(String email) {
		int count = userDao.checkEmail(email);
		if(count>0){
			return true;
		}
		return false;
	}

	
	public User getLoginUser(String account,String password) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("password", password);
		return userDao.getLoginUser(map);
	}

	
	public void updateUserPwd(User user) {
		userDao.updateUserPwd(user);
		
	}

	
	public List<User> queryUserListPage(QueryUser query, PageEntity page) {
		return userDao.queryUserListPage(query, page);
	}

	
	public void updateUserStates(User user) {
		userDao.updateUserStates(user);
		
	}

	
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	
	public void updateImg(User user) {
		userDao.updateImg(user);
	}

	
	public int queryAllUserCount() {
		return userDao.queryAllUserCount();
	}

	
	public User queryUserByEmailOrMobile(String emailOrMobile) {
		return userDao.queryUserByEmailOrMobile(emailOrMobile);
	}


	public Map<String, User> queryCustomerInCusIds(List<Long> cusIds) throws Exception {
		if(ObjectUtils.isNotNull(cusIds)){
            Map<String, User> map = new HashMap<String, User>();
            // 通过传入的cusIds 查询customer
            List<User> queryCustomerList = userDao.queryUsersByIds(cusIds);
            // 整理数据放回map
            if (ObjectUtils.isNotNull(queryCustomerList)) {
                for (User queryCustomer : queryCustomerList) {
                    map.put(String.valueOf(queryCustomer.getUserId()), queryCustomer);
                }
            }
            return map;
        }else{
            return null;
        }
	}


	public Map<String, User> getUserExpandByUids(String uids) throws Exception {
		String [] arrays=uids.split(",");
        List<Long> list = new ArrayList<Long>();
        for(String lo:arrays){
            if(StringUtils.isNotEmpty(lo)&&!"null".equals(lo)){
                list.add(Long.valueOf(lo));
            }
        }
        return queryCustomerInCusIds(list);
	}


	public void updateUnReadMsgNumAddOne(String falg, Long cusId) {
		userDao.updateUnReadMsgNumAddOne(falg, cusId);
	}

	public void updateUnReadMsgNumReset(String falg, Long cusId) {
		userDao.updateUnReadMsgNumReset(falg, cusId);
	}


	public void updateCusForLST(Long cusId, Date date) {
		userDao.updateCusForLST(cusId, date);
	}


	public String updateImportExcel(HttpServletRequest request,MultipartFile myFile,Integer mark) throws Exception {
		String msg="";
 		HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
		HSSFSheet sheet = wookbook.getSheet("Sheet1");
		
		int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
		if(rows==0){
			throw new BaseException("请填写数据");
		}
		for (int i = 1; i <= rows+1; i++) {
			// 读取左上端单元格
			HSSFRow row = sheet.getRow(i);
			// 行不为空
			if (row != null) {
				// **读取cell**
				String email = getCellValue(row.getCell((short) 0));//邮箱
				String mobile = getCellValue(row.getCell((short) 1));//手机
				String pwd=getCellValue(row.getCell((short) 2));//获得密码

				//邮箱
				if(ObjectUtils.isNull(email) || email.equals("")){
					if(mark==1){
						msg+="第" + i + "行邮箱错误<br/>";
						continue;
					}else{
						throw new BaseException("第" + i + "行邮箱错误<br/>");
					}
				}
				if(ObjectUtils.isNotNull(email) && StringUtils.isNotEmpty(email)){
					if (WebUtils.checkEmail(email, 50)==false) {
						if(mark==1){
							msg+="第"+i+"行邮箱格式错误<br/>";
							continue;
						}else{
							throw new BaseException("第"+i+"行邮箱格式错误<br/>");
						}
					}
				}
				boolean b = checkEmail(email.toLowerCase());
				if (b==true) {
					if(mark==1){
						msg+="第"+i+"行邮箱已存在<br/>";
						continue;
					}else{
						throw new BaseException("第"+i+"行邮箱已存在<br/>");
					}
				}
				//手机
				if(ObjectUtils.isNull(mobile) || mobile.equals("")){
					if(mark==1){
						msg+="第"+i+"行手机错误<br/>";
						continue;
					}else{
						throw new BaseException("第"+i+"行手机错误<br/>");
					}
				}
				if(ObjectUtils.isNotNull(mobile) && StringUtils.isNotEmpty(mobile)){
					if(!WebUtils.checkMobile(mobile)){
						if(mark==1){
							msg+="第"+i+"行手机格式错误<br/>";
							continue;
						}else{
							throw new BaseException("第"+i+"行手机格式错误<br/>");
						}
					}
				}
				//密码
				if(ObjectUtils.isNotNull(pwd) && !pwd.equals("")){
					if(pwd.length()<6 || pwd.length()>20){
						if(mark==1){
							msg+="第"+i+"行密码错误<br/>";
							continue;
						}else{
							throw new BaseException("第"+i+"行密码错误<br/>");
						}
					}
				}
				if(ObjectUtils.isNull(pwd) || pwd.equals("")){
					pwd="111111";
				}
				User user = new User();
				user.setEmail(email);//用户学员邮箱
				user.setMobile(mobile);//用户学员手机
				user.setPassword(MD5.getMD5(pwd));//用户学员密码
				user.setCreateTime(new Date());//注册时间
				user.setLastSystemTime(new Date());//上传统计系统消息时间
				userDao.createUser(user);//添加学员
			}
		}
		return msg;
	}
	
	/**
	 * 获得Hsscell内容
	 * 
	 * @param cell
	 * @return
	 */
	public String getCellValue(HSSFCell cell) {
		String value = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_FORMULA:
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				DecimalFormat df = new DecimalFormat("0");    
				value = df.format(cell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_STRING:
				value = cell.getStringCellValue().trim();
				break;
			default:
				value = "";
				break;
			}
		}
		return value.trim();
	}

	/**
	 * 缓存用户信息
	 */
	public void setLoginInfo(HttpServletRequest request, int userId,String autoThirty){
		User user =this.queryUserById(userId);
		//用户密码不能让别人看到
		user.setPassword("");
		//用户cookie key
		String uuid = WebUtils.getCookie(request, CacheConstans.WEB_USER_LOGIN_PREFIX);
		//缓存用户的登录时间
		user.setLoginTimeStamp(Long.parseLong( EHCacheUtil.get(CacheConstans.USER_CURRENT_LOGINTIME+user.getUserId()).toString()));
		if(autoThirty!=null&&autoThirty.equals("true")){//自动登录
			EHCacheUtil.set(uuid, user, CacheConstans.USER_TIME);
		}else{
			EHCacheUtil.set(uuid, user, 86400);
		}
	}

	/**
	 * 根据条件获取User列表  带课程名称
	 * @param user  用户
	 * @param page   分页参数
	 * @return
	 */
	public List<User> getUserListPage(User user, PageEntity page) {
		return userDao.getUserListPage(user, page);
	}

}
