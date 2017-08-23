package com.inxedu.os.edu.dao.website;

import java.util.List;
import java.util.Map;

import com.inxedu.os.common.entity.PageEntity;

import com.inxedu.os.edu.entity.website.WebsiteCourseDetail;
import com.inxedu.os.edu.entity.website.WebsiteCourseDetailDTO;

/** 推荐课程关联Dao层 
 * @author www.inxedu.com
 * */
public interface WebsiteCourseDetailDao {
	/**
	 * 创建推荐课程 
	 * @param detail
	 */
	public void createWebsiteCourseDetail(String detail);
	
	/**
	 * 分页查询推荐课程
	 * @param dto 查询条件
	 * @param page 分页条件
	 * @return List<WebsiteCourseDetailDTO>
	 */
	public List<WebsiteCourseDetailDTO> queryCourseDetailPage(WebsiteCourseDetailDTO dto,PageEntity page);
	
	/**
	 * 删除推荐课程
	 * @param id 推荐课程ID
	 */
	public void deleteDetailById(int id);
	
	/**
	 * 修改推荐课程排序
	 * @param map 修改条件
	 */
	public void updateSort(Map<String,Integer> map);
	
	/**
	 * 通过类型ID，查询该类型的推荐课程
	 * @param recommendId 类型ID
	 * @return List<WebsiteCourseDetail>
	 */
	public List<WebsiteCourseDetail> queryDetailListByrecommendId(int recommendId);
	
}