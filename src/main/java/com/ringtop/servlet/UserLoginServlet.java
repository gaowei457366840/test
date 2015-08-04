package com.ringtop.servlet;

import javax.servlet.http.HttpServlet;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ringtop.common.util.Assert;
import com.ringtop.common.util.DateUtil;
import com.ringtop.common.util.MD5Util;
import com.ringtop.entity.ScUser;
import com.ringtop.service.system.UserService;
import com.ringtop.service.system.UserServiceImpl;


/**
 * 后台用户登录管理servlet
 * @author Administrator
 *
 */
public class UserLoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");	
		@SuppressWarnings("unused")
		String userCode = (String)request.getParameter("userCode");
		@SuppressWarnings("unused")
		String userPass = (String)request.getParameter("userPass");
		
	}
	
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");	
		response.setCharacterEncoding("UTF-8");
		
		String userCode = (String)request.getParameter("userCode");
		String userPass = (String)request.getParameter("userPass");
		String path = this.getServletContext().getContextPath();
		
		HttpSession session = request.getSession();
		
		if(Assert.hasTextNull(userCode)) {
			this.setMessage(session, "请输入用户登录帐号！");
			response.sendRedirect(path + "/pages/system/userLogin.jsp");
			return;
		}
		
		if(Assert.hasTextNull(userPass)) {
			this.setMessage(session, "请输入用户登录密码！");
			response.sendRedirect(path + "/pages/system/userLogin.jsp");
			return;
		}
		
		UserService userService = new UserServiceImpl();
		
		ScUser user = userService.findScUserLoginByCode(userCode);
		if(user == null) {
			this.setMessage(session, "没有找到用户登录帐号！");
			response.sendRedirect(path + "/pages/system/userLogin.jsp");
			return;
		}
		
		if(!MD5Util.comparePassword(user.getEncryptpara(), userPass, user.getPassword())) {
			this.setMessage(session, "用户登录密码错误！");
			response.sendRedirect(path + "/pages/system/userLogin.jsp");
			return;
		}
		
		setSessionCookie(request, response, user);
		
		String sql = "update sc_user s set s.lastLoginDate=? where s.userCode=?";
		userService.excSql(sql, new Object[] { DateUtil.getDate(DateUtil.FMT_YY_MM_DD_HH_MM_SS), userCode} );
		
		session.removeAttribute("message");
		
		response.sendRedirect(path+ "/pages/system/nmain.jsp");
	}

	/**
	 * Returns information about the servlet, such as 
	 * author, version, and copyright. 
	 *
	 * @return String information about this servlet
	 */
	public String getServletInfo() {
		return "This is my default servlet created by Eclipse";
	}
	
	//写入登陆后用户信息
	public final static boolean setSessionCookie(HttpServletRequest request,HttpServletResponse response,ScUser scUser) {
		
		if(request==null || response==null || scUser==null || scUser.getUserCode()==null){
		    System.err.println("setSessionCookie null");
			return false;
		}
		HttpSession session=request.getSession();
		if(session==null){
			System.err.println("setSessionCookie session= null");
			return false;
		}
		
		session.setAttribute("disUser", scUser);
		
		return true;
	}

	
	private void setMessage(HttpSession session,String message) {
		
		if(Assert.hasText(message)) {
			session.setAttribute("message", message);
		}
	}
}
