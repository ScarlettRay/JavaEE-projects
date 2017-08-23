package com.inxedu.os.edu.dao.impl.website;


import java.util.List;
import java.util.Map;

import com.inxedu.os.common.entity.PageEntity;
import org.springframework.stereotype.Repository;

import com.inxedu.os.common.dao.GenericDaoImpl;
import com.inxedu.os.edu.dao.website.WebsiteImagesDao;
import com.inxedu.os.edu.entity.website.WebsiteImages;

/**
 *广告图Dao层实现
 *@author www.inxedu.com
 */
@Repository("websiteImagesDao")
public class WebsiteImagesDaoImpl extends GenericDaoImpl implements WebsiteImagesDao {

	
	public int creasteImage(WebsiteImages image) {
		this.insert("WebsiteImagesMapper.creasteImage", image);
		return image.getImageId();
	}

	
	public List<Map<String,Object>> queryImagePage(WebsiteImages image, PageEntity page) {
		return this.queryForListPage("WebsiteImagesMapper.queryImagePage", image, page);
	}

	
	public WebsiteImages queryImageById(int imageId) {
		return this.selectOne("WebsiteImagesMapper.queryImageById", imageId);
	}

	
	public void deleteImages(String imageIds) {
		this.delete("WebsiteImagesMapper.deleteImages", imageIds);
	}

	
	public void updateImage(WebsiteImages image) {
		this.update("WebsiteImagesMapper.updateImage", image);
		
	}

	
	public List<WebsiteImages> queryImagesByType() {
		return this.selectList("WebsiteImagesMapper.queryImagesByType", null);
	}
}
