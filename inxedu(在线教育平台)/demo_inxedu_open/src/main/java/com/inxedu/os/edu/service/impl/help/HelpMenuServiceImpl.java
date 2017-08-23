package com.inxedu.os.edu.service.impl.help;

import com.inxedu.os.common.cache.EHCacheUtil;
import com.inxedu.os.common.constants.CacheConstans;
import com.inxedu.os.edu.dao.help.HelpMenuDao;
import com.inxedu.os.edu.entity.help.HelpMenu;
import com.inxedu.os.edu.service.help.HelpMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 帮助菜单
 * @author http://www.inxedu.com
 */
@Service("helpMenuService")
public class HelpMenuServiceImpl implements HelpMenuService {
	@Autowired
	private HelpMenuDao helpMenuDao;
	
	/**
	 * 查询所有菜单 
	 * @return HelpMenu
	 */
    public List<List<HelpMenu>> getHelpMenuAll(){
    	@SuppressWarnings("unchecked")
		List<List<HelpMenu>> helpMenus=(List<List<HelpMenu>>) EHCacheUtil.get(CacheConstans.HELP_CENTER);
    	if(helpMenus!=null){
    		return helpMenus;
    	}
    	helpMenus=new ArrayList<List<HelpMenu>>();//用于前台显示的菜单集合
		List<HelpMenu> HelpMenuOnes=getHelpMenuOne();//一级菜单集合
		for(HelpMenu HelpMenuOne:HelpMenuOnes)
		{
			List<HelpMenu> helpMenuOneAndTwo=new ArrayList<HelpMenu>();//一级菜单+该菜单的二级菜单集合
			List<HelpMenu> helpMenuTwos=getHelpMenuTwoByOne(HelpMenuOne.getId());//二级菜单集合
			helpMenuOneAndTwo.add(HelpMenuOne);
			for(HelpMenu helpMenuTwo:helpMenuTwos)//组装一级菜单和二级菜单
			{
				helpMenuOneAndTwo.add(helpMenuTwo);
			}
			helpMenus.add(helpMenuOneAndTwo);
		}
		if(helpMenus!=null){
			EHCacheUtil.set(CacheConstans.HELP_CENTER, helpMenus, CacheConstans.HELP_CENTER_TIME);
		}
		return helpMenus;
    }
	/**
	 * 查询所有一级菜单 
	 * @return helpMenu
	 */
    public List<HelpMenu> getHelpMenuOne(){
    	return helpMenuDao.getHelpMenuOne();
    }
    /**
	 * 根据一级菜单ID查询二级菜单 
	 * @return helpMenu
	 */
	public List<HelpMenu> getHelpMenuTwoByOne(Long id){
		return helpMenuDao.getHelpMenuTwoByOne(id);
	}
    /**
     * 删除菜单
     * @param id
     */
    public void delHelpMenuById(Long id){
    	helpMenuDao.delHelpMenuById(id);
    	EHCacheUtil.remove(CacheConstans.HELP_CENTER);
    }
    /**
     * 更新菜单
     * @param helpMenu
     */
    public void updateHelpMenuById(HelpMenu helpMenu){
    	helpMenuDao.updateHelpMenuById(helpMenu);
		EHCacheUtil.remove(CacheConstans.HELP_CENTER);
    }
    /**
     *  添加菜单
     * @param helpMenu
     * @return id
     */
    public Long createHelpMenu(HelpMenu helpMenu){
		EHCacheUtil.remove(CacheConstans.HELP_CENTER);
    	return helpMenuDao.createHelpMenu(helpMenu);
    	
    }
    /**
     * 根据ID查找菜单
     * @param id
     * @return
     */
    public HelpMenu getHelpMenuById(Long id){
    	return helpMenuDao.getHelpMenuById(id);
    }
    
}

