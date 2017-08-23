package com.inxedu.os.edu.service.website;


import java.util.Map;

import com.inxedu.os.edu.entity.website.WebsiteProfile;

/**
 * service
 * @author www.inxedu.com
 */
public interface WebsiteProfileService {
	/**
	 * 修改WebsiteProfile
	 */
	public void updateWebsiteProfile(WebsiteProfile websiteProfile) throws Exception;

	/**
	 * 查询所有网站配置
	 */
	public Map<String, Object> getWebsiteProfileList() throws Exception;
	/**
	 * 根据type查询网站配置
	 */
	public Map<String,Object> getWebsiteProfileByType(String type);

}