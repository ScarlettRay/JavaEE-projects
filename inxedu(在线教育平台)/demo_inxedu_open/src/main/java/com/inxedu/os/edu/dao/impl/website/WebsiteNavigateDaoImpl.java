package com.inxedu.os.edu.dao.impl.website;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.inxedu.os.common.dao.GenericDaoImpl;
import com.inxedu.os.edu.dao.website.WebsiteNavigateDao;
import com.inxedu.os.edu.entity.website.WebsiteNavigate;

/**
 * 网站导航dao
 * @author www.inxedu.com
 */
@Repository("websiteNavigateDao")
public class WebsiteNavigateDaoImpl extends GenericDaoImpl implements WebsiteNavigateDao {

	
	public List<WebsiteNavigate> getWebsiteNavigate(WebsiteNavigate websiteNavigate){
		return this.selectList("WebsiteNavigateMapper.getWebsiteNavigate", websiteNavigate);
	}
	
	
	public void addWebsiteNavigate(WebsiteNavigate websiteNavigate){
		this.insert("WebsiteNavigateMapper.createWebsiteNavigate", websiteNavigate);
	}

	
	public void freezeWebsiteNavigate(WebsiteNavigate websiteNavigate){
		this.update("WebsiteNavigateMapper.freezeWebsiteNavigate", websiteNavigate);
	}
	
	
	public void delWebsiteNavigate(int id){
		this.delete("WebsiteNavigateMapper.delWebsiteNavigate",id);
	}
	
	
	public void updateWebsiteNavigate(WebsiteNavigate websiteNavigate){
		this.update("WebsiteNavigateMapper.updateWebsiteNavigate", websiteNavigate);
	}
	
	
	public WebsiteNavigate getWebsiteNavigateById(int id){
		return this.selectOne("WebsiteNavigateMapper.getWebsiteNavigateById", id);
	}
	
	
	public List<WebsiteNavigate> getWebNavigate(){
		return this.selectList("WebsiteNavigateMapper.getWebNavigate",0);
	}

}
