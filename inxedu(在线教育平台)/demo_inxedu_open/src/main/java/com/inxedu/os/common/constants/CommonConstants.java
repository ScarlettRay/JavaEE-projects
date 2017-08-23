package com.inxedu.os.common.constants;

import com.inxedu.os.common.util.PropertyUtil;

/**
 * 常量
 * @author www.inxedu.com
 */
public class CommonConstants {

	public static String propertyFile = "project";
	public static PropertyUtil propertyUtil = PropertyUtil.getInstance(propertyFile);
	public static String contextPath = propertyUtil.getProperty("contextPath");
	public static String staticServer = propertyUtil.getProperty("contextPath");
	public static String uploadImageServer = propertyUtil.getProperty("contextPath");
	public static String staticImage = propertyUtil.getProperty("contextPath");
	public static String projectName = propertyUtil.getProperty("projectName");
	public static final String MYDOMAIN = propertyUtil.getProperty("mydomain");

	/** 邮箱正则表达式 */
	public static String emailRegex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	/** 电话号码正则表达式 */
	public static String telRegex = "^1[0-9]{10}$";
	/** 后台用户登录名正则表达式 */
	public static String loginRegex = "^(?=.*[a-zA-Z])[a-zA-Z0-9]{6,20}$";
	/** 图片验证码Session的K */
	public static final String RAND_CODE = "COMMON_RAND_CODE";
}
