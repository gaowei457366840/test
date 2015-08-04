package com.ringtop.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/****
 * session过滤器
 * @author Administrator
 *
 */
public class SessionFilter implements Filter{

	//超时时长1小时（毫秒）
	private static final long OUTTIME = 3600000; 
	

	public void doFilter(ServletRequest sRequest, ServletResponse sResponse,
			FilterChain chain) throws IOException, ServletException {

		
		HttpServletRequest request = (HttpServletRequest) sRequest;
		HttpServletResponse response = (HttpServletResponse) sResponse;		
		
		HttpSession session = request.getSession(); 
		
		Object user  = session.getAttribute("disUser");
		String uriTmp = request.getRequestURI().toLowerCase();
		
		try {
			if (System.currentTimeMillis() - session.getLastAccessedTime() > OUTTIME ||
					(
						uriTmp.indexOf("temp.jsp") == -1					&&
						uriTmp.indexOf("logincancellation.jsp") == -1		&&
						uriTmp.indexOf("import.jsp") == -1					&&
						uriTmp.indexOf("userlogin.jsp") == -1				&&
						uriTmp.indexOf("/js/") == -1						&&
						uriTmp.indexOf("/images/") == -1					&&
						uriTmp.indexOf("/css/") == -1						&&
						uriTmp.indexOf("/share/") == -1						&&
						uriTmp.indexOf("/images/") == -1					&&
						uriTmp.indexOf("/style/") == -1						&&
						uriTmp.indexOf("/widgets/") == -1					&&
						!uriTmp.endsWith("rtdiscoverybackstage01")			&&
						!uriTmp.endsWith("rtdiscoverybackstage01/")			&&
						uriTmp.indexOf("/servlet/userloginservlet") == -1   &&
						uriTmp.indexOf("/servlet/scuserpweditservlet".toLowerCase()) == -1   &&
						user == null 
					)
				) {
				
				response.sendRedirect(request.getContextPath() + "/loginCancellation.jsp" );
				
			} else {
				
				chain.doFilter(request, response);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		
	}
	
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
