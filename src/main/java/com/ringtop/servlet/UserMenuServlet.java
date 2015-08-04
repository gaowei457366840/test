package com.ringtop.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ringtop.entity.ScMenu;
import com.ringtop.service.system.MenuService;
import com.ringtop.service.system.MenuServiceImpl;

/**
 * 获取用户菜单信息
 * @author Administrator
 *
 */
public class UserMenuServlet extends HttpServlet {

	private static final long serialVersionUID = -8979744338303638258L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		MenuService menuService = new MenuServiceImpl();
		List<ScMenu> menuList = menuService.findMenuList();
		request.setAttribute("modules", menuList);
		
		request.getRequestDispatcher("../pages/system/frame/menu.jsp").forward(request, response);
	}

}
