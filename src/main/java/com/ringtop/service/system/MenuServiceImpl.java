package com.ringtop.service.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ringtop.common.dao.impl.AbstractDAO;
import com.ringtop.common.util.Assert;
import com.ringtop.entity.ScMenu;

/**
 * 系统菜单信息管理service接口
 * @author Administrator
 *
 */
public class MenuServiceImpl implements MenuService {


	//sql 封装 dao
	@SuppressWarnings("unused")
	private AbstractDAO abstractDAO = new AbstractDAO();
	
	/*****************************************
	 * 查找用户菜单信息列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ScMenu> findMenuList() {
		
		String sql = " select m.ID,m.PMENU_ID,m.MENU_CODE,m.MENU_NAME,m.MENU_LEVEL,m.MENU_ORDER,m.IS_LEAF,m.URL from SC_MENU m order by m.PMENU_ID,m.MENU_ORDER ";
		@SuppressWarnings("unused")
		List<Map<String, Object>> list = (List<Map<String, Object>>)this.abstractDAO.execQuery(sql, null);
		
		List<ScMenu> menuList = new ArrayList<ScMenu>();
		if(Assert.notEmpty(list)) {
			for(int i=0; i<list.size(); i++) {
				Map<String, Object> map = list.get(i);
				ScMenu menu = new ScMenu();
				menu.setId(map.get("ID").toString());
				menu.setMenuCode(map.get("MENU_CODE").toString());
				menu.setMenuName(map.get("MENU_NAME").toString());
				menu.setPmenuId(map.get("PMENU_ID").toString());
				menu.setMenuLevel(Integer.parseInt(map.get("MENU_LEVEL").toString()));
				menu.setIsLeaf(Integer.parseInt(map.get("IS_LEAF").toString()));
				menu.setUrl(map.get("URL").toString());
				
				menuList.add(menu);
			}
		}
		
		return menuList;
		
	}
	
	
	/*****************************************************
	 * 获取指定菜单下的所有菜单id
	 * @param pmenuId
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private String getId(String pmenuId) {
		
		String ids = pmenuId;
		String sql = " select id from SC_MENU where pmenu_id=?";
		
		List<Map<String, String>> list = (List<Map<String, String>>)this.abstractDAO.execQuery(sql, new Object[] {pmenuId});
		
		if(list != null && list.size() > 0) {
			for(int i=0; i<list.size(); i++) {
				ids = ids + "," +  getId(list.get(i).get("id"));
			}
		}
		
		return ids;
	}

}
