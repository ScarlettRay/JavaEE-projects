package com.ukefu.webim.web.handler.admin.callcenter;

import java.util.Date;
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
import com.ukefu.webim.service.repository.CallCenterSkillRepository;
import com.ukefu.webim.service.repository.ExtentionRepository;
import com.ukefu.webim.service.repository.PbxHostRepository;
import com.ukefu.webim.service.repository.SkillExtentionRepository;
import com.ukefu.webim.web.handler.Handler;
import com.ukefu.webim.web.model.CallCenterSkill;
import com.ukefu.webim.web.model.Extention;
import com.ukefu.webim.web.model.PbxHost;
import com.ukefu.webim.web.model.SkillExtention;

@Controller
@RequestMapping("/admin/callcenter")
public class CallCenterSkillController extends Handler{
	
	@Autowired
	private PbxHostRepository pbxHostRes ;
	
	@Autowired
	private ExtentionRepository extentionRes;
	
	@Autowired
	private CallCenterSkillRepository skillRes ;
	
	@Autowired
	private SkillExtentionRepository skillExtentionRes;
	
	@RequestMapping(value = "/skill")
    @Menu(type = "callcenter" , subtype = "callcenterresource" , access = false , admin = true)
    public ModelAndView skill(ModelMap map , HttpServletRequest request , @Valid String hostid) {
		List<PbxHost> pbxHostList = pbxHostRes.findByOrgi(super.getOrgi(request)) ;
		map.addAttribute("pbxHostList" , pbxHostList);
		PbxHost pbxHost = null ;
		if(pbxHostList.size() > 0){
			map.addAttribute("pbxHost" , pbxHost = getPbxHost(pbxHostList, hostid));
			map.addAttribute("skillList" , skillRes.findByHostidAndOrgi(pbxHost.getId() , super.getOrgi(request)));
			map.addAttribute("skillExtentionList" , skillExtentionRes.findByHostidAndOrgi(hostid, super.getOrgi(request)));
		}
		return request(super.createRequestPageTempletResponse("/admin/callcenter/skill/index"));
    }
	
	private PbxHost getPbxHost(List<PbxHost> pbxHostList ,String hostid){
		PbxHost pbxHost = pbxHostList.get(0) ;
		if(!StringUtils.isBlank(hostid)){
			for(PbxHost pbx : pbxHostList){
				if(pbx.getId().equals(hostid)){
					pbxHost = pbx; break ;
				}
			}
		}
		return pbxHost ;
	}
	
	@RequestMapping(value = "/skill/add")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView extentionadd(ModelMap map , HttpServletRequest request , @Valid String hostid) {
		map.put("pbxHost", pbxHostRes.findByIdAndOrgi(hostid, super.getOrgi(request))) ;
    	return request(super.createRequestPageTempletResponse("/admin/callcenter/skill/add"));
    }
	
	@RequestMapping(value = "/skill/save")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView extentionsave(ModelMap map , HttpServletRequest request , @Valid CallCenterSkill skill) {
		if(!StringUtils.isBlank(skill.getSkill())){
			int count = skillRes.countBySkillAndOrgi(skill.getSkill(), super.getOrgi(request)) ;
			if(count == 0){
				skill.setOrgi(super.getOrgi(request));
				skill.setCreater(super.getUser(request).getId());
				skillRes.save(skill) ;
			}
		}
		return request(super.createRequestPageTempletResponse("redirect:/admin/callcenter/skill.html?hostid="+skill.getHostid()));
    }
	
	@RequestMapping(value = "/skill/edit")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView extentionedit(ModelMap map , HttpServletRequest request , @Valid String id , @Valid String hostid) {
		map.addAttribute("extention" , extentionRes.findByIdAndOrgi(id, super.getOrgi(request)));
		map.put("pbxHost", pbxHostRes.findByIdAndOrgi(hostid, super.getOrgi(request))) ;
    	return request(super.createRequestPageTempletResponse("/admin/callcenter/skill/edit"));
    }
	
	@RequestMapping(value = "/skill/update")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView pbxhostupdate(ModelMap map , HttpServletRequest request , @Valid Extention extention) {
		if(!StringUtils.isBlank(extention.getId())){
			Extention ext = extentionRes.findByIdAndOrgi(extention.getId(), super.getOrgi(request)) ;
			if(ext!=null && !StringUtils.isBlank(ext.getExtention()) && ext.getExtention().matches("[\\d]{3,8}")){
				ext.setExtention(extention.getExtention());
				if(!StringUtils.isBlank(extention.getPassword())){
					ext.setPassword(extention.getPassword());
				}
				ext.setUpdatetime(new Date());
				extentionRes.save(ext) ;
			}
		}
		return request(super.createRequestPageTempletResponse("redirect:/admin/callcenter/skill.html?hostid="+extention.getHostid()));
    }
	
	@RequestMapping(value = "/skill/delete")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView extentiondelete(ModelMap map , HttpServletRequest request , @Valid String id , @Valid String hostid) {
		if(!StringUtils.isBlank(id)){
			extentionRes.delete(id);
		}
		return request(super.createRequestPageTempletResponse("redirect:/admin/callcenter/skill.html?hostid="+hostid));
    }
	
	@RequestMapping(value = "/skill/imp")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView skillimp(ModelMap map , HttpServletRequest request , @Valid String hostid) {
		if(!StringUtils.isBlank(hostid)){
			map.put("pbxHost", pbxHostRes.findByIdAndOrgi(hostid, super.getOrgi(request))) ;
			map.put("extentionList", extentionRes.findByHostidAndOrgi(hostid, super.getOrgi(request))) ;
			map.addAttribute("skillList" , skillRes.findByHostidAndOrgi(hostid , super.getOrgi(request)));
		}
    	return request(super.createRequestPageTempletResponse("/admin/callcenter/skill/imp"));
    }
	
	@RequestMapping(value = "/skill/extention/delete")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView skillextentiondelete(ModelMap map , HttpServletRequest request , @Valid String id , @Valid String hostid) {
		if(!StringUtils.isBlank(id)){
			skillExtentionRes.delete(id);
		}
		return request(super.createRequestPageTempletResponse("redirect:/admin/callcenter/skill.html?hostid="+hostid));
    }
	
	@RequestMapping(value = "/skill/extention/save")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView skillextentionsave(ModelMap map , HttpServletRequest request , @Valid SkillExtention skillExtention, @Valid String hostid , @Valid String[] exts) {
		if(exts!=null && exts.length > 0){
			List<SkillExtention> skillExtentionList = skillExtentionRes.findByHostidAndOrgi(hostid, super.getOrgi(request)) ;
			for(String ext :exts){
				SkillExtention skillExt = new SkillExtention() ;
				skillExt.setOrgi(super.getOrgi(request));
				skillExt.setCreater(super.getUser(request).getId());
				skillExt.setCreatetime(new Date());
				skillExt.setExtention(ext);
				skillExt.setHostid(hostid);
				skillExt.setSkillid(skillExtention.getSkillid());
				skillExt.setUpdatetime(new Date());
				boolean ingroup = false;
				for(SkillExtention temp : skillExtentionList){
					if(temp.getSkillid().equals(skillExt.getSkillid()) && temp.getExtention().equals(skillExt.getExtention())){
						ingroup = true ;
					}
				}
				if(ingroup == false){
					skillExtentionRes.save(skillExt) ;
				}
			}
		}
		return request(super.createRequestPageTempletResponse("redirect:/admin/callcenter/skill.html?hostid="+hostid));
    }
	
}
