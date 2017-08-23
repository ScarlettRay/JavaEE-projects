package com.inxedu.os.edu.dao.impl.website;

import com.inxedu.os.common.dao.GenericDaoImpl;
import com.inxedu.os.edu.dao.website.WebsiteProfileDao;
import com.inxedu.os.edu.entity.website.WebsiteProfile;
import org.springframework.stereotype.Repository;

import java.util.List;

/** 网站配置 
 * @author www.inxedu.com
 * */
 @Repository("websiteProfileDao")
public class WebsiteProfileDaoImpl extends GenericDaoImpl implements WebsiteProfileDao {
	
	
	public WebsiteProfile getWebsiteProfileByType(String type) {
		return this.selectOne("WebsiteProfileMapper.getWebsiteProfileByType", type);
	}

	/**
	 * 添加查询网站配置
	 */
	public void addWebsiteProfileByType(WebsiteProfile websiteProfile){
		this.insert("WebsiteProfileMapper.addWebsiteProfileByType",websiteProfile);
	}
	public void updateWebsiteProfile(WebsiteProfile websiteProfile) {
		this.update("WebsiteProfileMapper.updateWebsiteProfile", websiteProfile);
	}

	
	public List<WebsiteProfile> getWebsiteProfileList() {
		return this.selectList("WebsiteProfileMapper.getWebsiteProfileList", null);
	}

}
