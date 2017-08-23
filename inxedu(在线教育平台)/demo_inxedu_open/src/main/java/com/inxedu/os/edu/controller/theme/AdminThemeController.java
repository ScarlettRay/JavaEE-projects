package com.inxedu.os.edu.controller.theme;

import com.asual.lesscss.LessEngine;
import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.common.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * 主题颜色管理
 * @author www.inxedu.com
 */
@Controller
@RequestMapping("/admin")
public class AdminThemeController extends BaseController {

    /** 到主题色修改页面 */
    @RequestMapping("/theme/toupdate")
    public String toUpdateTheme(HttpServletRequest request) {
        return getViewPath("/admin/theme/update_theme");//讲师添加页面
    }
    /** 修改主题色 */
    @RequestMapping("/theme/update")
    public String updateTheme(HttpServletRequest request,@RequestParam String color) {
    	//获得项目根目录
    	String strDirPath = request.getSession().getServletContext().getRealPath("/");     	
    	//读取字符串
    	StringBuffer sb = new StringBuffer();
    	//当前读取行数
    	int rowcount = 1 ;
    	//要修改的行数
    	int updaterowcount = 2 ;
    	FileReader fr;
		try {
			String path = strDirPath+"/static/inxweb/css/less/theme.less";
			fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null) {
				if(rowcount==updaterowcount&&StringUtils.isNotEmpty(color)){
					sb.append("@mColor:"+color+";");
				}else{
					sb.append(line);
				}
				line = br.readLine();
				//System.out.println(line);
				rowcount++;
			}
			br.close();
			fr.close();
			LessEngine engine = new LessEngine();
			FileWriter fw = new FileWriter(strDirPath+"/static/inxweb/css/theme.css");
			fw.write(engine.compile(sb.toString()));
			fw.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/theme/toupdate";
    }
}