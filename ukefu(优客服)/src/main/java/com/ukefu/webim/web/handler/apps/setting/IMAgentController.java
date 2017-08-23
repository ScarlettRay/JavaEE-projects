package com.ukefu.webim.web.handler.apps.setting;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ukefu.core.UKDataContext;
import com.ukefu.util.Menu;
import com.ukefu.util.UKTools;
import com.ukefu.webim.service.cache.CacheHelper;
import com.ukefu.webim.service.repository.BlackListRepository;
import com.ukefu.webim.service.repository.ConsultInviteRepository;
import com.ukefu.webim.service.repository.QuickReplyRepository;
import com.ukefu.webim.service.repository.SessionConfigRepository;
import com.ukefu.webim.service.repository.TagRepository;
import com.ukefu.webim.web.handler.Handler;
import com.ukefu.webim.web.model.BlackEntity;
import com.ukefu.webim.web.model.SessionConfig;
import com.ukefu.webim.web.model.SysDic;
import com.ukefu.webim.web.model.Tag;
import com.ukefu.webim.web.model.UKeFuDic;

@Controller
@RequestMapping("/setting")
public class IMAgentController extends Handler{
	
	@Autowired
	private ConsultInviteRepository invite;
	
	@Autowired
	private SessionConfigRepository sessionConfigRes ;
	
	@Autowired
	private TagRepository tagRes ;
	
	@Autowired
	private QuickReplyRepository quickReplyRes ;
	
	@Autowired
	private BlackListRepository blackListRes;
	
	@Value("${web.upload-path}")
    private String path;

    @RequestMapping("/agent/index")
    @Menu(type = "setting" , subtype = "sessionconfig" , admin= true)
    public ModelAndView index(ModelMap map , HttpServletRequest request) {
    	SessionConfig sessionConfig = sessionConfigRes.findByOrgi(super.getOrgi(request)) ;
    	if(sessionConfig == null){
    		sessionConfig = new SessionConfig() ;
    	}
    	map.put("sessionConfig", sessionConfig) ;
    	
    	map.put("tagTypeList", UKeFuDic.getInstance().getDic("com.dic.tag.type")) ;
    	
        return request(super.createAppsTempletResponse("/apps/setting/agent/index"));
    }
    
    @RequestMapping("/agent/sessionconfig/save")
    @Menu(type = "setting" , subtype = "sessionconfig" , admin= true)
    public ModelAndView sessionconfig(ModelMap map , HttpServletRequest request , @Valid SessionConfig sessionConfig) {
    	SessionConfig tempSessionConfig = sessionConfigRes.findByOrgi(super.getOrgi(request)) ;
    	if(tempSessionConfig == null){
    		tempSessionConfig = sessionConfig;
    		tempSessionConfig.setCreater(super.getUser(request).getId());
    	}else{
    		UKTools.copyProperties(sessionConfig, tempSessionConfig);
    	}
    	tempSessionConfig.setOrgi(super.getOrgi(request));
    	sessionConfigRes.save(tempSessionConfig) ;
    	
    	CacheHelper.getSystemCacheBean().put(UKDataContext.SYSTEM_CACHE_SESSION_CONFIG,tempSessionConfig, super.getOrgi(request)) ;
    	
    	map.put("sessionConfig", tempSessionConfig) ;
        return request(super.createAppsTempletResponse("/apps/setting/agent/index"));
    }
    
    @RequestMapping("/blacklist")
    @Menu(type = "setting" , subtype = "blacklist" , admin= true)
    public ModelAndView blacklist(ModelMap map , HttpServletRequest request) {
    	map.put("blackList", blackListRes.findByOrgi(super.getOrgi(request), new PageRequest(super.getP(request), super.getPs(request), Sort.Direction.DESC, "endtime"))) ;
    	map.put("tagTypeList", UKeFuDic.getInstance().getDic("com.dic.tag.type")) ;
    	return request(super.createAppsTempletResponse("/apps/setting/agent/blacklist"));
    }
    
    @RequestMapping("/blacklist/delete")
    @Menu(type = "setting" , subtype = "tag" , admin= true)
    public ModelAndView blacklistdelete(ModelMap map , HttpServletRequest request , @Valid String id) {
    	if(!StringUtils.isBlank(id)){
    		BlackEntity tempBlackEntity = blackListRes.findByIdAndOrgi(id, super.getOrgi(request)) ;
    		if(tempBlackEntity!=null){
		    	blackListRes.delete(tempBlackEntity);
		    	CacheHelper.getSystemCacheBean().delete(tempBlackEntity.getUserid(), UKDataContext.SYSTEM_ORGI) ;
    		}
    	}
    	return request(super.createRequestPageTempletResponse("redirect:/setting/blacklist.html"));
    }
    
    @RequestMapping("/tag")
    @Menu(type = "setting" , subtype = "tag" , admin= true)
    public ModelAndView tag(ModelMap map , HttpServletRequest request , @Valid String code) {
    	SysDic tagType = null ;
    	List<SysDic> tagList = UKeFuDic.getInstance().getDic("com.dic.tag.type") ;
    	if(tagList.size() > 0){
    		
    		if(!StringUtils.isBlank(code)){
    			for(SysDic dic : tagList){
    				if(code.equals(dic.getCode())){
    					tagType = dic ;
    				}
    			}
    		}else{
    			tagType = tagList.get(0) ;
    		}
    		map.put("tagType", tagType) ;
    	}
    	if(tagType!=null){
    		map.put("tagList", tagRes.findByOrgiAndTagtype(super.getOrgi(request) , tagType.getCode() , new PageRequest(super.getP(request), super.getPs(request)))) ;
    	}
    	map.put("tagTypeList", tagList) ;
    	return request(super.createAppsTempletResponse("/apps/setting/agent/tag"));
    }
    
    @RequestMapping("/tag/add")
    @Menu(type = "setting" , subtype = "tag" , admin= true)
    public ModelAndView tagadd(ModelMap map , HttpServletRequest request , @Valid String tagtype) {
    	map.addAttribute("tagtype", tagtype) ;
        return request(super.createRequestPageTempletResponse("/apps/setting/agent/tagadd"));
    }
    
    @RequestMapping("/tag/edit")
    @Menu(type = "setting" , subtype = "tag" , admin= true)
    public ModelAndView tagedit(ModelMap map , HttpServletRequest request , @Valid String id , @Valid String tagtype) {
    	map.put("tag", tagRes.findOne(id)) ;
    	map.addAttribute("tagtype", tagtype) ;
        return request(super.createRequestPageTempletResponse("/apps/setting/agent/tagedit"));
    }
    
    @RequestMapping("/tag/update")
    @Menu(type = "setting" , subtype = "tag" , admin= true)
    public ModelAndView tagupdate(ModelMap map , HttpServletRequest request , @Valid Tag tag , @Valid String tagtype) {
    	Tag temptag = tagRes.findByOrgiAndTag(super.getOrgi(request), tag.getTag()) ;
    	if(temptag == null || tag.getId().equals(temptag.getId())){
    		tag.setOrgi(super.getOrgi(request));
	    	tag.setCreater(super.getUser(request).getId());
	    	tagRes.save(tag) ;
    	}
    	return request(super.createRequestPageTempletResponse("redirect:/setting/tag.html?code="+tagtype));
    }
    
    @RequestMapping("/tag/save")
    @Menu(type = "setting" , subtype = "tag" , admin= true)
    public ModelAndView tagsave(ModelMap map , HttpServletRequest request , @Valid Tag tag , @Valid String tagtype) {
    	if(tagRes.findByOrgiAndTag(super.getOrgi(request), tag.getTag()) == null){
	    	tag.setOrgi(super.getOrgi(request));
	    	tag.setCreater(super.getUser(request).getId());
	    	tagRes.save(tag) ;
    	}
        return request(super.createRequestPageTempletResponse("redirect:/setting/tag.html?code="+tagtype));
    }
    
    @RequestMapping("/tag/delete")
    @Menu(type = "setting" , subtype = "tag" , admin= true)
    public ModelAndView tagdelete(ModelMap map , HttpServletRequest request , @Valid String id , @Valid String tagtype) {
    	tagRes.delete(id);
    	return request(super.createRequestPageTempletResponse("redirect:/setting/tag.html?code="+tagtype));
    }
    
    
    
    @RequestMapping("/acd")
    @Menu(type = "setting" , subtype = "acd" , admin= true)
    public ModelAndView acd(ModelMap map , HttpServletRequest request) {
    	map.put("tagTypeList", UKeFuDic.getInstance().getDic("com.dic.tag.type")) ;
    	return request(super.createAppsTempletResponse("/apps/setting/agent/acd"));
    }
}