package com.inxedu.os.edu.controller.system;

import com.inxedu.os.common.controller.BaseController;
import com.inxedu.os.edu.entity.system.SysFunction;
import com.inxedu.os.edu.service.system.SysFunctionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统权限
 * @author www.inxedu.com
 */
@Controller
@RequestMapping("/admin/sysfunctioin")
public class SysFunctionController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(SysFunctionController.class);

	@InitBinder({"sysFunction"})
	public void initBinderSysFunction(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("sysFunction.");
	}
	
	@Autowired
	private SysFunctionService sysFunctionService;
	
	/**
	 * 显示权限树结构
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/showfunctionztree")
	public ModelAndView showSysFunctonZtree(HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		try{
			//后台系统权限list页面
			model.setViewName(getViewPath("/admin/system/sys-function-list"));
			List<SysFunction> functionList = sysFunctionService.queryAllSysFunction();
			if(functionList!=null){
				model.addObject("jsonFunction", gson.toJson(functionList));
			}
		}catch (Exception e) {
			model.setViewName(this.setExceptionRequest(request, e));
			logger.error("showSysFunctonZtree()--error",e);
		}
		return model;
	}
	
	/**
	 * 添加权限
	 * @return
	 */
	@RequestMapping("/addfunction")
	@ResponseBody
	public Map<String,Object> addFunction(@ModelAttribute("sysFunction") SysFunction sysFunction){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			sysFunction.setCreateTime(new Date());
			sysFunction.setFunctionType(1);
			sysFunction.setFunctionName("新建的权限");
			sysFunctionService.cresateSysFunction(sysFunction);
			json = this.setJson(true, null, gson.toJson(sysFunction));
		}catch (Exception e) {
			this.setAjaxException(json);
			logger.error("addFunction()--error",e);
		}
		return json;
	}
	
	/**
	 * 修改权限信息
	 * @param sysFunction 权限实体
	 * @return Map<String,Object>
	 */
	@RequestMapping("/updatefunction")
	@ResponseBody
	public Map<String,Object> udpateFunction(@ModelAttribute("sysFunction") SysFunction sysFunction){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			if(sysFunction.getFunctionId()<=0){
				json = this.setJson(false, "请选择要修改的权限", null);
				return json;
			}
			if(sysFunction.getFunctionName()==null || sysFunction.getFunctionName().trim().equals("")){
				json = this.setJson(false, "请填写权限名!", null);
				return json;
			}
			sysFunctionService.updateFunction(sysFunction);
			json = this.setJson(true, "保存成功", sysFunction);
		}catch (Exception e) {
			this.setAjaxException(json);
			logger.error("udpateFunction()---error",e);
		}
		return json;
	}
	
	/**
	 * 修改父节点
	 * @param parentId 父ID
	 * @param functionId 权限ID
	 * @return Map<String,Object>
	 */
	@RequestMapping("/updateparentid/{parentId}/{functionId}")
	@ResponseBody
	public Map<String,Object> updateParnetid(@PathVariable("parentId") int parentId,@PathVariable("functionId") int functionId){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			sysFunctionService.updateFunctionParentId(parentId, functionId);
			json = this.setJson(true, "操作成功", null);
		}catch (Exception e) {
			this.setAjaxException(json);
			logger.error("updateParnetid()--error",e);
		}
		return json;
	}
	
	/**
	 * 删除权限功能
	 * @param ids 要删除的权限ID串
	 * @return Map<String,Object>
	 */
	@RequestMapping("/deletefunction/{ids}")
	@ResponseBody
	public Map<String,Object> deleteFunction(@PathVariable("ids") String ids){
		Map<String,Object> json = new HashMap<String,Object>();
		try{
			sysFunctionService.deleteFunctionByIds(ids);
			json = this.setJson(true, null, null);
		}catch (Exception e) {
			this.setAjaxException(json);
			logger.error("deleteFunction()--error",e);
		}
		return json;
	}
}
