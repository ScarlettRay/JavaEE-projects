package com.inxedu.os.edu.service.impl.websiteehcache;

import com.inxedu.os.common.entity.PageEntity;
import com.inxedu.os.edu.dao.websiteehcache.WebsiteEhcacheDao;
import com.inxedu.os.edu.entity.websiteehcache.WebsiteEhcache;
import com.inxedu.os.edu.service.websiteehcache.WebsiteEhcacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 缓存管理
 * @author www.inxedu.com
 *
 */
@Service("websiteEhcacheService")
public class WebsiteEhcacheServiceImpl implements WebsiteEhcacheService {
	
	@Autowired
	private WebsiteEhcacheDao websiteEhcacheDao;

	@Override
	public void addWebsiteEhcache(WebsiteEhcache websiteEhcache) {
		websiteEhcacheDao.addWebsiteEhcache(websiteEhcache);
	}
	
	@Override
	public List<WebsiteEhcache> queryWebsiteEhcacheList(WebsiteEhcache websiteEhcache, PageEntity page) {
		return websiteEhcacheDao.queryWebsiteEhcacheList(websiteEhcache, page);
	}

	@Override
	public Long delWebsiteEhcache(Long id) {
		return websiteEhcacheDao.delWebsiteEhcache(id);
	}

	@Override
	public WebsiteEhcache getWebsiteEhcacheById(Long id) {
		return websiteEhcacheDao.getWebsiteEhcacheById(id);
	}

	@Override
	public boolean queryWebsiteEhcacheIsExsit(String key) {
		if(websiteEhcacheDao.queryWebsiteEhcacheIsExsit(key)>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Long updateWebsiteEhcache(WebsiteEhcache websiteEhcache) {
		return websiteEhcacheDao.updateWebsiteEhcache(websiteEhcache);
	}

}
