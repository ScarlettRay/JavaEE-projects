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
import com.ukefu.webim.service.repository.ExtentionRepository;
import com.ukefu.webim.service.repository.PbxHostRepository;
import com.ukefu.webim.web.handler.Handler;
import com.ukefu.webim.web.model.Extention;
import com.ukefu.webim.web.model.PbxHost;
import com.ukefu.webim.web.model.User;

@Controller
@RequestMapping("/admin/callcenter")
public class CallCenterExtentionController extends Handler{
	
	@Autowired
	private PbxHostRepository pbxHostRes ;
	
	@Autowired
	private ExtentionRepository extentionRes;
	
	@RequestMapping(value = "/extention")
    @Menu(type = "callcenter" , subtype = "callcenterresource" , access = false , admin = true)
    public ModelAndView extention(ModelMap map , HttpServletRequest request , @Valid String hostid) {
		List<PbxHost> pbxHostList = pbxHostRes.findByOrgi(super.getOrgi(request)) ;
		map.addAttribute("pbxHostList" , pbxHostList);
		PbxHost pbxHost = null ;
		if(pbxHostList.size() > 0){
			map.addAttribute("pbxHost" , pbxHost = getPbxHost(pbxHostList, hostid));
			map.addAttribute("extentionList" , extentionRes.findByHostidAndOrgi(pbxHost.getId() , super.getOrgi(request)));
		}
		return request(super.createRequestPageTempletResponse("/admin/callcenter/extention/index"));
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
	
	@RequestMapping(value = "/extention/add")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView extentionadd(ModelMap map , HttpServletRequest request , @Valid String hostid) {
		map.put("pbxHost", pbxHostRes.findByIdAndOrgi(hostid, super.getOrgi(request))) ;
    	return request(super.createRequestPageTempletResponse("/admin/callcenter/extention/add"));
    }
	
	@RequestMapping(value = "/extention/save")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView extentionsave(ModelMap map , HttpServletRequest request , @Valid Extention extention) {
		if(!StringUtils.isBlank(extention.getExtention()) && !StringUtils.isBlank(extention.getPassword())){
			String[] extstr = extention.getExtention().split("[,， ]") ;
			int extnum = 0 ;
			for(String ext : extstr){
				if(ext.matches("[\\d]{3,8}")){	//分机号码最少3位数字
					createNewExtention(ext, super.getUser(request), extention.getHostid(), extention.getPassword(), super.getOrgi(request) , extention) ;
				}else{
					String[] ph = ext.split("[~-]") ;
					if(ph.length == 2 && ph[0].matches("[\\d]{3,8}") && ph[1].matches("[\\d]{3,8}") && ph[0].length() == ph[1].length()){
						int start = Integer.parseInt(ph[0]) ;
						int end = Integer.parseInt(ph[1]) ;
						
						for(int i= start ; i <= end && extnum < 100 ; i++){	//最大一次批量生产的 分机号不超过100个
							createNewExtention(String.valueOf(i), super.getUser(request), extention.getHostid(), extention.getPassword(), super.getOrgi(request) , extention) ;
						}
					}
				}
			}
		}
		return request(super.createRequestPageTempletResponse("redirect:/admin/callcenter/extention.html?hostid="+extention.getHostid()));
    }
	
	private Extention createNewExtention(String num , User user , String hostid , String password , String orgi , Extention src){
		Extention extno = new Extention();
		extno.setExtention(num);
		extno.setOrgi(orgi);
		extno.setCreater(user.getId());
		extno.setHostid(hostid);
		extno.setPassword(password);
		
		extno.setPlaynum(src.isPlaynum());
		extno.setCallout(src.isCallout());
		extno.setRecord(src.isRecord());
		extno.setExtype(src.getExtype());
		
		int count = extentionRes.countByExtentionAndHostidAndOrgi(extno.getExtention() , hostid, orgi) ;
		if(count == 0){	
			extentionRes.save(extno) ;
		}
		return extno ;
	}
	
	@RequestMapping(value = "/extention/edit")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView extentionedit(ModelMap map , HttpServletRequest request , @Valid String id , @Valid String hostid) {
		map.addAttribute("extention" , extentionRes.findByIdAndOrgi(id, super.getOrgi(request)));
		map.put("pbxHost", pbxHostRes.findByIdAndOrgi(hostid, super.getOrgi(request))) ;
    	return request(super.createRequestPageTempletResponse("/admin/callcenter/extention/edit"));
    }
	
	@RequestMapping(value = "/extention/update")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView extentionupdate(ModelMap map , HttpServletRequest request , @Valid Extention extention) {
		if(!StringUtils.isBlank(extention.getId())){
			Extention ext = extentionRes.findByIdAndOrgi(extention.getId(), super.getOrgi(request)) ;
			if(ext!=null && !StringUtils.isBlank(ext.getExtention()) && ext.getExtention().matches("[\\d]{3,8}")){
				ext.setExtention(extention.getExtention());
				if(!StringUtils.isBlank(extention.getPassword())){
					ext.setPassword(extention.getPassword());
				}
				ext.setPlaynum(extention.isPlaynum());
				ext.setCallout(extention.isCallout());
				ext.setRecord(extention.isRecord());
				ext.setExtype(extention.getExtype());
				
				ext.setUpdatetime(new Date());
				extentionRes.save(ext) ;
			}
		}
		return request(super.createRequestPageTempletResponse("redirect:/admin/callcenter/extention.html?hostid="+extention.getHostid()));
    }
	
	@RequestMapping(value = "/extention/delete")
    @Menu(type = "callcenter" , subtype = "extention" , access = false , admin = true)
    public ModelAndView extentiondelete(ModelMap map , HttpServletRequest request , @Valid String id , @Valid String hostid) {
		if(!StringUtils.isBlank(id)){
			extentionRes.delete(id);
		}
		return request(super.createRequestPageTempletResponse("redirect:/admin/callcenter/extention.html?hostid="+hostid));
    }
}
