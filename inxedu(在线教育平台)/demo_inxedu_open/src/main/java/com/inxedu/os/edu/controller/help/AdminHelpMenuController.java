package com.inxedu.os.edu.controller.help;

import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.edu.entity.help.HelpMenu;
import com.inxedu.os.edu.service.help.HelpMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * AdminHelpMenuController帮助中心
 * @author http://www.inxedu.com
 */
@Controller
@RequestMapping("/admin")
public class AdminHelpMenuController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AdminHelpMenuController.class);
    @Autowired
    private HelpMenuService helpMenuService;

    private static final String getHelpMenu = getViewPath("/admin/help/help_menu_list");//所有帮助中心列表页面
    private static final String addHelpMenu = getViewPath("/admin/help/help_menu_add");//添加帮助菜单页面
    private static final String updateHelpMenu = getViewPath("/admin/help/help_menu_update");//修改帮助中心页面

    // 创建群 绑定变量名字和属性，把参数封装到类
    @InitBinder("helpMenu")
    public void initBinderHelpMenu(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("helpMenu.");
    }

    /**
     * 菜单表
     * @param request
     * @return
     */
    @RequestMapping("/helpMenu/list")
    public ModelAndView getHelpMenu(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(getHelpMenu);
        try {
            List<List<HelpMenu>> helpMenus = helpMenuService.getHelpMenuAll();// 菜单集合
            modelAndView.addObject("helpMenus", helpMenus);
        } catch (Exception e) {
            logger.error("AdminUploadVideoController.getHelpMenu--菜单表出错", e);
            return new ModelAndView(setExceptionRequest(request, e));
        }
        return modelAndView;
    }

    /**
     * 跳转添加菜单
     * @return
     */
    @RequestMapping("/helpMenu/doadd")
    public ModelAndView doAddHelpMenu(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(addHelpMenu);
        try {
            // 一级菜单
            List<HelpMenu> helpMenus = helpMenuService.getHelpMenuOne();
            modelAndView.addObject("helpMenus", helpMenus);
        } catch (Exception e) {
            logger.error("AdminUploadVideoController.doAddHelpMenu", e);
            return new ModelAndView(this.setExceptionRequest(request, e));
        }
        return modelAndView;
    }

    /**
     * 添加菜单
     *
     * @return
     */
    @RequestMapping("/helpMenu/add")
    public String addHelpMenu(HelpMenu helpMenu, HttpServletRequest request) {
        try {
            if (helpMenu != null) {
                helpMenu.setCreateTime(new Date());
                helpMenuService.createHelpMenu(helpMenu);
            }
        } catch (Exception e) {
            logger.error("AdminUploadVideoController.addHelpMenu", e);
            return this.setExceptionRequest(request, e);
        }
        return "redirect:/admin/helpMenu/list";
    }

    /**
     * 跳转更新菜单
     *
     * @return
     */
    @RequestMapping("/helpMenu/doupdate/{id}")
    public ModelAndView doUpdateHelpMenu(@PathVariable Long id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(updateHelpMenu);
        try {
            // 一级菜单
            List<HelpMenu> helpMenus = helpMenuService.getHelpMenuOne();
            modelAndView.addObject("helpMenus", helpMenus);
            HelpMenu helpMenu = helpMenuService.getHelpMenuById(id);
            modelAndView.addObject("helpMenu", helpMenu);
        } catch (Exception e) {
            logger.error("AdminUploadVideoController.doUpdateHelpMenu", e);
            return new ModelAndView(this.setExceptionRequest(request, e));
        }
        return modelAndView;
    }

    /**
     * 更新菜单
     *
     * @return
     */
    @RequestMapping("/helpMenu/update")
    public String updateHelpMenu(HelpMenu helpMenu, HttpServletRequest request) {
        try {
            helpMenuService.updateHelpMenuById(helpMenu);
        } catch (Exception e) {
            logger.error("AdminUploadVideoController.updateHelpMenu", e);
            return this.setExceptionRequest(request, e);
        }
        return "redirect:/admin/helpMenu/list";
    }

    /**
     * 删除菜单
     *
     * @return
     */
    @RequestMapping("/helpMenu/del/{id}")
    @ResponseBody
    public Map<String, Object> delHelpMenu(@PathVariable Long id, HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            helpMenuService.delHelpMenuById(id);
            json = this.setJson(true, "true", null);
        } catch (Exception e) {
            logger.error("AdminUploadVideoController.delHelpMenu", e);
            json = this.setJson(false, "error", null);
        }
        return json;
    }
}