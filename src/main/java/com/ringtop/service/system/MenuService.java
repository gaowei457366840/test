package com.ringtop.service.system;

import java.util.List;

import com.ringtop.entity.ScMenu;

/**
 * 系统菜单信息管理service接口
 * @author Administrator
 *
 */
public interface MenuService {

	/*****************************************
	 * 查找用户菜单信息列表
	 * @return
	 */
	public List<ScMenu> findMenuList();
}
