package com.ukefu.webim.web.handler.admin.callcenter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ukefu.util.Menu;
import com.ukefu.webim.service.repository.ExtentionRepository;
import com.ukefu.webim.service.repository.PbxHostRepository;
import com.ukefu.webim.service.repository.RouterRulesRepository;
import com.ukefu.webim.service.repository.SkillExtentionRepository;
import com.ukefu.webim.web.handler.Handler;
import com.ukefu.webim.web.model.PbxHost;
import com.ukefu.webim.web.model.RouterRules;

@Controller
@RequestMapping("/admin/callcenter")
public class CallCenterRouterController extends Handler{
	
	@Autowired
	private PbxHostRepository pbxHostRes ;
	
	@Autowired
	private ExtentionRepository extentionRes;
	
	@Autowired
	private RouterRulesRepository routerRulesRes ;
	
	@Autowired
	private SkillExtentionRepository skillExtentionRes;
	
	@RequestMapping(value = "/router")
    @Menu(type = "callcenter" , subtype = "callcenterresource" , access = false , admin = true)
    public ModelAndView skill(ModelMap map , HttpServletRequest request , @Valid String hostid) {
		List<PbxHost> pbxHostList = pbxHostRes.findByOrgi(super.getOrgi(request)) ;
		map.addAttribute("pbxHostList" , pbxHostList);
		if(pbxHostList.size() > 0){
			map.addAttribute("pbxHost" , pbxHostRes.findByIdAndOrgi(hostid, super.getOrgi(request)));
			map.addAttribute("routerRulesList" , routerRulesRes.findByHostidAndOrgi(hostid, super.getOrgi(request)));
		}
		return request(super.createRequestPageTempletResponse("/admin/callcenter/router/index"));
    }
	
	@RequestMapping(value = "/router/add")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView extentionadd(ModelMap map , HttpServletRequest request , @Valid String hostid) {
		map.put("pbxHost", pbxHostRes.findByIdAndOrgi(hostid, super.getOrgi(request))) ;
    	return request(super.createRequestPageTempletResponse("/admin/callcenter/router/add"));
    }
	
	@RequestMapping(value = "/router/save")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView extentionsave(ModelMap map , HttpServletRequest request , @Valid RouterRules router) {
		if(!StringUtils.isBlank(router.getName())){
			int count = routerRulesRes.countByNameAndOrgi(router.getName(), super.getOrgi(request)) ;
			if(count == 0){
				router.setOrgi(super.getOrgi(request));
				router.setCreater(super.getUser(request).getId());
				routerRulesRes.save(router) ;
			}
		}
		return request(super.createRequestPageTempletResponse("redirect:/admin/callcenter/router.html?hostid="+router.getHostid()));
    }
	
	@RequestMapping(value = "/router/edit")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView routeredit(ModelMap map , HttpServletRequest request , @Valid String id , @Valid String hostid) {
		map.addAttribute("routerRules" , routerRulesRes.findByIdAndOrgi(id, super.getOrgi(request)));
		map.put("pbxHost", pbxHostRes.findByIdAndOrgi(hostid, super.getOrgi(request))) ;
    	return request(super.createRequestPageTempletResponse("/admin/callcenter/router/edit"));
    }
	
	@RequestMapping(value = "/router/update")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView pbxhostupdate(ModelMap map , HttpServletRequest request , @Valid RouterRules router) {
		if(!StringUtils.isBlank(router.getId())){
			RouterRules oldRouter = routerRulesRes.findByIdAndOrgi(router.getId(), super.getOrgi(request)) ;
			if(oldRouter!=null){
				oldRouter.setName(router.getName());
				oldRouter.setField(router.getField());
				oldRouter.setRegex(router.getRegex());
				oldRouter.setRouterinx(router.getRouterinx());
				oldRouter.setFalsebreak(router.isFalsebreak());
				routerRulesRes.save(oldRouter);
			}
		}
		return request(super.createRequestPageTempletResponse("redirect:/admin/callcenter/router.html?hostid="+router.getHostid()));
    }
	
	@RequestMapping(value = "/router/code")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView routercode(ModelMap map , HttpServletRequest request , @Valid String id , @Valid String hostid) {
		map.addAttribute("routerRules" , routerRulesRes.findByIdAndOrgi(id, super.getOrgi(request)));
		map.put("pbxHost", pbxHostRes.findByIdAndOrgi(hostid, super.getOrgi(request))) ;
    	return request(super.createRequestPageTempletResponse("/admin/callcenter/router/code"));
    }
	
	@RequestMapping(value = "/router/code/update")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView routercodeupdate(ModelMap map , HttpServletRequest request , @Valid RouterRules router) {
		if(!StringUtils.isBlank(router.getId())){
			RouterRules oldRouter = routerRulesRes.findByIdAndOrgi(router.getId(), super.getOrgi(request)) ;
			if(!StringUtils.isBlank(router.getRoutercontent())){
				oldRouter.setRoutercontent(router.getRoutercontent());
				routerRulesRes.save(oldRouter);
			}
		}
		return request(super.createRequestPageTempletResponse("redirect:/admin/callcenter/router.html?hostid="+router.getHostid()));
    }
	
	@RequestMapping(value = "/router/delete")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView extentiondelete(ModelMap map , HttpServletRequest request , @Valid String id , @Valid String hostid) {
		if(!StringUtils.isBlank(id)){
			routerRulesRes.delete(id);
		}
		return request(super.createRequestPageTempletResponse("redirect:/admin/callcenter/router.html?hostid="+hostid));
    }
}
