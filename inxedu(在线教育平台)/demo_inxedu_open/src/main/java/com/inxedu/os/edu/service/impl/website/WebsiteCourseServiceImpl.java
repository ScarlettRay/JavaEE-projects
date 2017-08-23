package com.inxedu.os.edu.service.impl.website;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inxedu.os.edu.dao.website.WebsiteCourseDao;
import com.inxedu.os.edu.entity.website.WebsiteCourse;
import com.inxedu.os.edu.service.website.WebsiteCourseService;

/**
 * 推荐课程分类管理接口
 * @author www.inxedu.com
 */
@Service("websiteCourseService")
public class WebsiteCourseServiceImpl implements WebsiteCourseService {
	@Autowired
	private WebsiteCourseDao websiteCourseDao;
	 /**
     * 推荐课程分类列表
     */
    public List<WebsiteCourse> queryWebsiteCourseList(){
    	return websiteCourseDao.queryWebsiteCourseList();
    }
    /**
     * 查询推荐课程分类
     */
    public WebsiteCourse queryWebsiteCourseById(int id){
    	return websiteCourseDao.queryWebsiteCourseById(id);
    }
    /**
     * 修改推荐课程分类
     */
    public void updateWebsiteCourseById(WebsiteCourse websiteCourse){
    	websiteCourseDao.updateWebsiteCourseById(websiteCourse);
    }
    /**
     * 添加推荐课程分类
     */
    public void addWebsiteCourse(WebsiteCourse websiteCourse){
    	websiteCourseDao.addWebsiteCourse(websiteCourse);
    }
    /**
     * 删除推荐课程分类及分类下推荐课程
     */
    public void deleteWebsiteCourseDetailById(int id){
    	websiteCourseDao.deleteWebsiteCourseDetailById(id);
    }
}