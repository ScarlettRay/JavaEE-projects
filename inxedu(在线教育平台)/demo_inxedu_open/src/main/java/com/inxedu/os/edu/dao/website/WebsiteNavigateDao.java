package com.inxedu.os.edu.dao.website;


import java.util.List;

import com.inxedu.os.edu.entity.website.WebsiteNavigate;

/**
 * WebsiteNavigateTbl管理接口
 * @author www.inxedu.com
 */
public interface WebsiteNavigateDao {
	
	/**
	 * 导航列表
	 */
	public List<WebsiteNavigate> getWebsiteNavigate(WebsiteNavigate websiteNavigate);
	/**
	 * 添加导航
	 */
	public void addWebsiteNavigate(WebsiteNavigate websiteNavigate);
	/**
	 * 冻结、解冻导航
	 */
	public void freezeWebsiteNavigate(WebsiteNavigate websiteNavigate);
	/**
	 * 删除导航
	 */
	public void delWebsiteNavigate(int id);
	/**
	 * 更新导航
	 */
	public void updateWebsiteNavigate(WebsiteNavigate websiteNavigate);
	/**
	 * id查询导航
	 */
	public WebsiteNavigate getWebsiteNavigateById(int id);
	/**
	 * 查询未冻结的导航列表
	 */
	public List<WebsiteNavigate> getWebNavigate();
	
}