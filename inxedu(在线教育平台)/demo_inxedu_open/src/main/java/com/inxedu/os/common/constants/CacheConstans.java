package com.inxedu.os.common.constants;

import com.inxedu.os.common.util.PropertyUtil;

/**
 * @description cache缓存相关常量
 * @author www.inxedu.com
 */
public class CacheConstans {
	public static PropertyUtil webPropertyUtil = PropertyUtil.getInstance("memtimes");
	public static final String MEMFIX = webPropertyUtil.getProperty("memfix");
	public static final String RECOMMEND_COURSE = MEMFIX + "recommend_course";
	public static final int RECOMMEND_COURSE_TIME = Integer.parseInt(webPropertyUtil.getProperty("RECOMMEND_COURSE_TIME"));
	public static final String BANNER_IMAGES = MEMFIX + "banner_images";
	public static final int BANNER_IMAGES_TIME = Integer.parseInt(webPropertyUtil.getProperty("BANNER_IMAGES_TIME"));
	public static final String WEBSITE_PROFILE = MEMFIX + "website_profile";
	public static final int WEBSITE_PROFILE_TIME = Integer.parseInt(webPropertyUtil.getProperty("WEBSITE_PROFILE_TIME"));
	public static final String WEBSITE_NAVIGATE = MEMFIX + "website_navigate";
	public static final int WEBSITE_NAVIGATE_TIME = Integer.parseInt(webPropertyUtil.getProperty("WEBSITE_NAVIGATE_TIME"));
	public static final String INDEX_STUDENT_DYNAMIC = MEMFIX + "index_student_dynamic";

	/** 前台登录用户ehcache前缀 */
	public static final String WEB_USER_LOGIN_PREFIX = MEMFIX + "web_user_login_";
	public static final int USER_TIME = Integer.parseInt(webPropertyUtil.getProperty("USER_TIME"));//前台登录用户缓存6小时
	public static final String USER_CURRENT_LOGINTIME = MEMFIX+"USER_CURRENT_LOGINTIME_";//记录当前用户当前的登录时间，下次登录时会更新此缓存

	/** 缓存后台登录用户ehcache前缀 */
	public static final String LOGIN_MEMCACHE_PREFIX = MEMFIX + "login_sys_user_";
	/** 后台所有用户权限缓存名前缀 **/
	public static final String SYS_ALL_USER_FUNCTION_PREFIX = MEMFIX + "SYS_USER_ALL_FUNCTION_";
	/** 登录用户权限缓存名前缀 **/
	public static final String USER_FUNCTION_PREFIX = MEMFIX + "USER_ALL_FUNCTION";
	/** 前台首页 网校名师 缓存 **/
	public static final String INDEX_TEACHER_RECOMMEND = MEMFIX + "INDEX_TEACHER_RECOMMEND";
	/** 文章 好文推荐 缓存 **/
	public static final String ARTICLE_GOOD_RECOMMEND = MEMFIX + "ARTICLE_GOOD_RECOMMEND";
	/** 问答 热门问答推荐 缓存 **/
	public static final String QUESTIONS_HOT_RECOMMEND = MEMFIX + "QUESTIONS_HOT_RECOMMEND";

	/** 网站统计 */
	public static final String WEB_STATISTICS = MEMFIX + "web_statistics";
	/** 网站最近30条活跃统计 */
	public static final String WEB_STATISTICS_THIRTY = MEMFIX + "web_statistics_thirty";
	/** 缓存1小时 */
	public static final int WEB_STATISTICS_TIME = Integer.parseInt(webPropertyUtil.getProperty("WEB_STATISTICS_TIME"));

	/** 后台统计 */
	public static final String WEB_COUNT = MEMFIX + "WEB_COUNT";
	/** 缓存1小时 */
	public static final int WEB_COUNT_TIME = Integer.parseInt(webPropertyUtil.getProperty("WEB_STATISTICS_TIME"));

	public static final String HELP_CENTER = MEMFIX + "help_center";//帮助页面左侧菜单
	public static final int HELP_CENTER_TIME = Integer.parseInt(webPropertyUtil.getProperty("HELP_CENTER_TIME"));//缓存1小时;
}
