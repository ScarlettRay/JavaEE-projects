package com.ukefu.webim.web.handler.apps.callcenter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ukefu.util.Menu;
import com.ukefu.util.UKTools;
import com.ukefu.webim.service.repository.AclRepository;
import com.ukefu.webim.service.repository.CallCenterSkillRepository;
import com.ukefu.webim.service.repository.ExtentionRepository;
import com.ukefu.webim.service.repository.PbxHostRepository;
import com.ukefu.webim.service.repository.RouterRulesRepository;
import com.ukefu.webim.service.repository.SipTrunkRepository;
import com.ukefu.webim.service.repository.SkillExtentionRepository;
import com.ukefu.webim.service.repository.TemplateRepository;
import com.ukefu.webim.web.handler.Handler;
import com.ukefu.webim.web.model.Extention;
import com.ukefu.webim.web.model.PbxHost;
import com.ukefu.webim.web.model.SystemConfig;
import com.ukefu.webim.web.model.Template;

@Controller
@RequestMapping("/apps/callcenter")
public class ExtentionController extends Handler{
	
	@Autowired
	private PbxHostRepository pbxHostRes ;
	
	@Autowired
	private ExtentionRepository extentionRes;
	
	@Autowired
	private AclRepository aclRes;
	
	@Autowired
	private RouterRulesRepository routerRes;
	
	@Autowired
	private SkillExtentionRepository skillExtentionRes ;
	
	@Autowired
	private CallCenterSkillRepository skillRes ;
	
	@Autowired
	private SipTrunkRepository sipTrunkRes ;
	
	@Autowired
	private TemplateRepository templateRes ;
	
	@RequestMapping(value = "/extention")
    @Menu(type = "callcenter" , subtype = "extention" , access = true)
    public ModelAndView index(ModelMap map , HttpServletRequest request , @Valid String hostname , @Valid String key_value) {
		ModelAndView view = request(super.createRequestPageTempletResponse("/apps/business/callcenter/extention/index")) ;
		List<PbxHost> pbxHostList = pbxHostRes.findByHostnameOrIpaddrAndOrgi(hostname, hostname, super.getOrgi(request)) ;
		PbxHost pbxHost = null ;
		SystemConfig systemConfig = UKTools.getSystemConfig() ;
		if(pbxHostList!=null && pbxHostList.size() > 0){
			pbxHost = pbxHostList.get(0) ;
			map.addAttribute("pbxHost" , pbxHost);
			map.addAttribute("skillList" , skillRes.findByHostidAndOrgi(pbxHost.getId() , super.getOrgi(request)));
			map.addAttribute("extentionList" , extentionRes.findByHostidAndOrgi(pbxHost.getId() , super.getOrgi(request)));
		}
		if(systemConfig!=null && systemConfig.isCallcenter()){
			if(!StringUtils.isBlank(systemConfig.getCc_extention())){
				Template template = UKTools.getTemplate(systemConfig.getCc_extention()) ;
				if(template!=null){
					map.addAttribute("template" , template);
					view = request(super.createRequestPageTempletResponse("/apps/business/callcenter/template")) ;
				}
			}
		}
    	return view ;
    }
	
	@RequestMapping(value = "/configuration")
    @Menu(type = "callcenter" , subtype = "configuration" , access = true)
    public ModelAndView configuration(ModelMap map , HttpServletRequest request , @Valid String hostname , @Valid String key_value ,  @Valid String profile) {
		
		List<PbxHost> pbxHostList = pbxHostRes.findByHostnameOrIpaddrAndOrgi(hostname, hostname, super.getOrgi(request)) ;
		PbxHost pbxHost = null ;
		SystemConfig systemConfig = UKTools.getSystemConfig() ;
		if(pbxHostList!=null && pbxHostList.size() > 0){
			pbxHost = pbxHostList.get(0) ;
			map.addAttribute("pbxHost" , pbxHost);
			map.addAttribute("skillList" , skillRes.findByHostidAndOrgi(pbxHost.getId() , super.getOrgi(request)));
			map.addAttribute("skillExtentionList" , skillExtentionRes.findByHostidAndOrgi(pbxHost.getId() , super.getOrgi(request)));
			map.addAttribute("extentionList" , extentionRes.findByHostidAndOrgi(pbxHost.getId() , super.getOrgi(request)));
			map.addAttribute("aclList" , aclRes.findByHostidAndOrgi(pbxHost.getId() , super.getOrgi(request)));
			map.addAttribute("sipTrunkList" , sipTrunkRes.findByHostidAndOrgi(pbxHost.getId() , super.getOrgi(request)));
		}
		Template template = null ;
		ModelAndView view = request(super.createRequestPageTempletResponse("/apps/business/callcenter/notfound"));
		if(key_value!=null && key_value.equals("callcenter.conf")){
			view = request(super.createRequestPageTempletResponse("/apps/business/callcenter/configure/callcenter"));
			if(systemConfig!=null && systemConfig.isCallcenter()){
				if(!StringUtils.isBlank(systemConfig.getCc_quene())){
					template = UKTools.getTemplate(systemConfig.getCc_quene()) ;
				}
			}
		}else if(key_value!=null && key_value.equals("acl.conf")){
			view = request(super.createRequestPageTempletResponse("/apps/business/callcenter/configure/acl"));
			if(systemConfig!=null && systemConfig.isCallcenter()){
				if(!StringUtils.isBlank(systemConfig.getCc_acl())){
					template = UKTools.getTemplate(systemConfig.getCc_acl()) ;
				}
			}
		}else if(key_value!=null && key_value.equals("ivr.conf")){
			view = request(super.createRequestPageTempletResponse("/apps/business/callcenter/configure/ivr"));
			if(systemConfig!=null && systemConfig.isCallcenter()){
				if(!StringUtils.isBlank(systemConfig.getCc_ivr())){
					template = UKTools.getTemplate(systemConfig.getCc_ivr()) ;
				}
			}
		}
		if(template!=null){
			map.addAttribute("template" , template);
			view = request(super.createRequestPageTempletResponse("/apps/business/callcenter/template")) ;
		}
    	return view;
    }
	
	@RequestMapping(value = "/dialplan")
    @Menu(type = "callcenter" , subtype = "dialplan" , access = true)
    public ModelAndView dialplan(ModelMap map , HttpServletRequest request , @Valid String hostname , @Valid String key_value) {
		ModelAndView view = request(super.createRequestPageTempletResponse("/apps/business/callcenter/dialplan/index"));
		List<PbxHost> pbxHostList = pbxHostRes.findByHostnameOrIpaddrAndOrgi(hostname, hostname, super.getOrgi(request)) ;
		PbxHost pbxHost = null ;
		SystemConfig systemConfig = UKTools.getSystemConfig() ;
		Template template = null ;
		if(pbxHostList!=null && pbxHostList.size() > 0){
			pbxHost = pbxHostList.get(0) ;
			map.addAttribute("pbxHost" , pbxHost);
			map.addAttribute("routerList" , routerRes.findByHostidAndOrgi(pbxHost.getId() , super.getOrgi(request)));
		}
		if(systemConfig!=null && systemConfig.isCallcenter()){
			if(!StringUtils.isBlank(systemConfig.getCc_siptrunk())){
				template = UKTools.getTemplate(systemConfig.getCc_router()) ;
			}
			if(template!=null){
				map.addAttribute("template" , template);
				view = request(super.createRequestPageTempletResponse("/apps/business/callcenter/template")) ;
			}
		}
		
		return view;
    }
	
	@RequestMapping(value = "/extention/detail")
    @Menu(type = "callcenter" , subtype = "extention" , access = false)
    public ModelAndView detail(ModelMap map , HttpServletRequest request , HttpServletResponse response ,@Valid String extno) {
		List<Extention> extentionList = extentionRes.findByExtentionAndOrgi(extno, super.getOrgi(request)) ;
		if(extentionList!=null && extentionList.size() == 1){
			map.addAttribute("extention" , extentionList.get(0));
		}
		response.setContentType("Content-type: text/json; charset=utf-8"); 
    	return request(super.createRequestPageTempletResponse("/apps/business/callcenter/extention/detail"));
    }
	
}
