package com.ukefu.webim.web.handler.apps.setting;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ukefu.core.UKDataContext;
import com.ukefu.util.Menu;
import com.ukefu.webim.service.repository.BlackListRepository;
import com.ukefu.webim.service.repository.ConsultInviteRepository;
import com.ukefu.webim.service.repository.QuickReplyRepository;
import com.ukefu.webim.service.repository.QuickTypeRepository;
import com.ukefu.webim.service.repository.SessionConfigRepository;
import com.ukefu.webim.service.repository.TagRepository;
import com.ukefu.webim.web.handler.Handler;
import com.ukefu.webim.web.model.QuickReply;
import com.ukefu.webim.web.model.QuickType;

@Controller
@RequestMapping("/setting/quickreply")
public class QuickReplyController extends Handler{
	
	@Autowired
	private ConsultInviteRepository invite;
	
	@Autowired
	private SessionConfigRepository sessionConfigRes ;
	
	@Autowired
	private TagRepository tagRes ;
	
	@Autowired
	private QuickReplyRepository quickReplyRes ;
	
	@Autowired
	private QuickTypeRepository quickTypeRes ;
	
	@Autowired
	private BlackListRepository blackListRes;
	
	@Value("${web.upload-path}")
    private String path;

    @RequestMapping("/index")
    @Menu(type = "setting" , subtype = "quickreply" , admin= true)
    public ModelAndView index(ModelMap map , HttpServletRequest request , @Valid String typeid) {
    	List<QuickType> quickTypeList = quickTypeRes.findByOrgiAndQuicktype(super.getOrgi(request) , UKDataContext.QuickTypeEnum.PUB.toString()) ; 
    	map.put("quickReplyList", quickReplyRes.getByOrgi(super.getOrgi(request) , null, new PageRequest(super.getP(request), super.getPs(request)))) ;
    	map.put("pubQuickTypeList", quickTypeList) ;
    	
    	return request(super.createAppsTempletResponse("/apps/setting/quickreply/index"));
    }
    @RequestMapping("/replylist")
    @Menu(type = "setting" , subtype = "quickreply" , admin= true)
    public ModelAndView list(ModelMap map , HttpServletRequest request , @Valid String typeid) {
    	if(!StringUtils.isBlank(typeid) && !typeid.equals("0")){
    		map.put("quickReplyList", quickReplyRes.getByOrgiAndCate(super.getOrgi(request)  , typeid, null, new PageRequest(super.getP(request), super.getPs(request)))) ;
    	}else{
    		map.put("quickReplyList", quickReplyRes.getByOrgi(super.getOrgi(request) , null , new PageRequest(super.getP(request), super.getPs(request)))) ;
    	}
    	map.put("quickType", quickTypeRes.findByIdAndOrgi(typeid, super.getOrgi(request))) ;
    	return request(super.createRequestPageTempletResponse("/apps/setting/quickreply/replylist"));
    }
    
    @RequestMapping("/add")
    @Menu(type = "setting" , subtype = "quickreplyadd" , admin= true)
    public ModelAndView quickreplyadd(ModelMap map , HttpServletRequest request) {
    	map.addAttribute("quickTypeList", quickTypeRes.findByOrgiAndQuicktype(super.getOrgi(request) , UKDataContext.QuickTypeEnum.PUB.toString())) ;
        return request(super.createRequestPageTempletResponse("/apps/setting/quickreply/add"));
    }
    
    @RequestMapping("/save")
    @Menu(type = "setting" , subtype = "quickreply" , admin= true)
    public ModelAndView quickreplysave(ModelMap map , HttpServletRequest request , @Valid QuickReply quickReply) {
    	if(!StringUtils.isBlank(quickReply.getTitle()) && !StringUtils.isBlank(quickReply.getContent())){
	    	quickReply.setOrgi(super.getOrgi(request));
			quickReply.setCreater(super.getUser(request).getId());
			quickReply.setType(UKDataContext.QuickTypeEnum.PUB.toString());
			quickReplyRes.save(quickReply) ;
    	}
        return request(super.createRequestPageTempletResponse("redirect:/setting/quickreply/index.html?typeid="+quickReply.getCate()));
    }
    
    @RequestMapping("/delete")
    @Menu(type = "setting" , subtype = "quickreply" , admin= true)
    public ModelAndView quickreplydelete(ModelMap map , HttpServletRequest request , @Valid String id) {
    	QuickReply quickReply = quickReplyRes.findOne(id) ;
    	if(quickReply!=null){
    		quickReplyRes.delete(id);
    	}
    	return request(super.createRequestPageTempletResponse("redirect:/setting/quickreply/index.html?typeid="+quickReply!=null ? quickReply.getCate() : "0"));
    }
    @RequestMapping("/edit")
    @Menu(type = "setting" , subtype = "quickreply" , admin= true)
    public ModelAndView quickreplyedit(ModelMap map , HttpServletRequest request , @Valid String id) {
    	QuickReply quickReply = quickReplyRes.findOne(id) ; 
    	map.put("quickReply", quickReply) ;
    	if(quickReply!=null){
    		map.put("quickType", quickTypeRes.findByIdAndOrgi(quickReply.getCate(), super.getOrgi(request))) ;
    	}
    	map.addAttribute("quickTypeList", quickTypeRes.findByOrgiAndQuicktype(super.getOrgi(request) , UKDataContext.QuickTypeEnum.PUB.toString())) ;
        return request(super.createRequestPageTempletResponse("/apps/setting/quickreply/edit"));
    }
    
    @RequestMapping("/update")
    @Menu(type = "setting" , subtype = "quickreply" , admin= true)
    public ModelAndView quickreplyupdate(ModelMap map , HttpServletRequest request , @Valid QuickReply quickReply) {
    	if(!StringUtils.isBlank(quickReply.getId())){
    		QuickReply temp = quickReplyRes.findOne(quickReply.getId()) ;
    		quickReply.setOrgi(super.getOrgi(request));
    		quickReply.setCreater(super.getUser(request).getId());
    		if(temp!=null){
    			quickReply.setCreatetime(temp.getCreatetime());
    		}
    		quickReply.setType(UKDataContext.QuickTypeEnum.PUB.toString());
    		quickReplyRes.save(quickReply) ;
    	}
    	return request(super.createRequestPageTempletResponse("redirect:/setting/quickreply/index.html?typeid="+quickReply.getCate()));
    }
    
    @RequestMapping({"/addtype"})
	@Menu(type="apps", subtype="kbs")
	public ModelAndView addtype(ModelMap map , HttpServletRequest request ){
		map.addAttribute("quickTypeList", quickTypeRes.findByOrgiAndQuicktype(super.getOrgi(request) , UKDataContext.QuickTypeEnum.PUB.toString())) ;
		return request(super.createRequestPageTempletResponse("/apps/setting/quickreply/addtype"));
	}
	 
    @RequestMapping("/type/save")
    @Menu(type = "apps" , subtype = "kbs")
    public ModelAndView typesave(HttpServletRequest request ,@Valid QuickType quickType) {
    	int count = quickTypeRes.countByOrgiAndNameAndParentid(super.getOrgi(request),quickType.getName(), quickType.getParentid()) ;
    	if(count == 0){
    		quickType.setOrgi(super.getOrgi(request));
    		quickType.setCreater(super.getUser(request).getId());
    		quickType.setCreatetime(new Date());
    		quickType.setQuicktype(UKDataContext.QuickTypeEnum.PUB.toString());
    		quickTypeRes.save(quickType) ;
    	}
    	return request(super.createRequestPageTempletResponse("redirect:/setting/quickreply/index.html"));
    }
    
}