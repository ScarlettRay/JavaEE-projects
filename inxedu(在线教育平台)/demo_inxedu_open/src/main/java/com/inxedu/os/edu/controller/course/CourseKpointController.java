package com.inxedu.os.edu.controller.course;

import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.util.ObjectUtils;
import com.inxedu.os.common.util.StringUtils;
import com.inxedu.os.common.util.inxeduvideo.InxeduVideo;
import com.inxedu.os.edu.constants.enums.WebSiteProfileType;
import com.inxedu.os.edu.entity.course.Course;
import com.inxedu.os.edu.entity.kpoint.CourseKpoint;
import com.inxedu.os.edu.service.course.CourseKpointService;
import com.inxedu.os.edu.service.course.CourseService;
import com.inxedu.os.edu.service.website.WebsiteProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * CourseKpoint 课程章节管理
 * @author www.inxedu.com
 */
@Controller
public class CourseKpointController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(CourseKpointController.class);


	private static final String getKopintHtml = getViewPath("/web/course/videocode");// 课程播放
	private static final String callBack56Uploading = getViewPath("/course/callBack56_uploading");//56视频上传回调
	private static final String playTxtAjax=getViewPath("/web/playCourse/play_txt_ajax");//加载播放大厅文本
	@Autowired
	private CourseKpointService courseKpointService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private WebsiteProfileService websiteProfileService;

	/**
	 * 获得视频播放的html
	 * @return
	 */
	@RequestMapping("/front/ajax/getKopintHtml")
	public String getKopintHtml(Model model, HttpServletRequest request,@RequestParam("kpointId")int kpointId, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");

		try {
			PrintWriter out=response.getWriter();
			CourseKpoint courseKpoint = courseKpointService.queryCourseKpointById(kpointId);
			// 当传入数据不正确时直接返回
			if (ObjectUtils.isNull(courseKpoint)) {
				out.println("<script>var noCover=true; dialog dialog('提示','参数错误！',1);</script>");
				return null;
			}

			//获取课程
			Course course = courseService.queryCourseById(courseKpoint.getCourseId());
			if (course==null) {
				logger.info("++isok:" + false);
				return getKopintHtml;
			}
			model.addAttribute("courseKpoint",courseKpoint);
			model.addAttribute("course",course);
			//视频
			if("VIDEO".equals(courseKpoint.getFileType())){
				// 视频url
				String videourl = courseKpoint.getVideoUrl();
				// 播放类型
				String videotype = courseKpoint.getVideoType();
				//查询inxeduVideo的key
				if(courseKpoint.getVideoType().equalsIgnoreCase(WebSiteProfileType.inxeduVideo.toString())){
					Map<String,Object> map=(Map<String,Object>)websiteProfileService.getWebsiteProfileByType(WebSiteProfileType.inxeduVideo.toString()).get(WebSiteProfileType.inxeduVideo.toString());
					String play_url = "http://vod.baofengcloud.com/" + map.get("UserId") + "/player/cloud.swf";
					String url = "servicetype=1&uid="+map.get("UserId")+"&fid="+videourl;
					play_url += "?" + url;
					//如果因酷云的key不为空则按加密播放如果为空则不加密
					if(StringUtils.isNotEmpty(map.get("SecretKey").toString())&&StringUtils.isNotEmpty(map.get("AccessKey").toString())){
						String token = InxeduVideo.createPlayToken(videourl,map.get("SecretKey").toString(),map.get("AccessKey").toString());
						play_url += "&tk=" + token;
					}
					play_url += "&auto=" + 1;
					videourl=play_url;
				}
				//查询cc的appId key
				if(courseKpoint.getVideoType().equalsIgnoreCase(WebSiteProfileType.cc.toString())){//如果cc
					Map<String,Object> map=websiteProfileService.getWebsiteProfileByType(WebSiteProfileType.cc.toString());
					model.addAttribute("ccwebsitemap", map);
				}
				// 放入数据
				model.addAttribute("videourl", videourl);
				model.addAttribute("videotype", videotype);
				return getKopintHtml;
			}
			//文本
			if("TXT".equals(courseKpoint.getFileType())){
				return playTxtAjax;
			}

            //判断是否为手机浏览器
            boolean isMoblie = JudgeIsMoblie(request);
            model.addAttribute("isMoblie", isMoblie);

			return getKopintHtml;
		} catch (Exception e) {
			logger.error("CourseKpointController.getKopintHtml", e);
			return setExceptionRequest(request, e);
		}
	}


    //判断是否为手机浏览器
    public boolean JudgeIsMoblie(HttpServletRequest request) {
        boolean isMoblie = false;
        String[] mobileAgents = { "iphone", "android","ipad", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
                "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
                "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
                "docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
                "techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
                "wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
                "pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
                "240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
                "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
                "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
                "mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
                "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
                "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
                "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
                "Googlebot-Mobile" };
        if (request.getHeader("User-Agent") != null) {
            System.out.println("User-Agent:"+request.getHeader("User-Agent"));
            for (String mobileAgent : mobileAgents) {
                if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {
                    isMoblie = true;
                    break;
                }
            }
        }
        return isMoblie;
    }

	/**
     * 获得56上传成功之后的回调
     */
    @RequestMapping("/api/56/callBack")
    public String callBack56Uploading(){
        return callBack56Uploading;
    }
}