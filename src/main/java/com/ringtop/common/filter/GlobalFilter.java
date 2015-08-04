package com.ringtop.common.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

public class GlobalFilter extends StrutsPrepareAndExecuteFilter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		request.setCharacterEncoding("UTF-8");
		
		HttpServletResponse response = (HttpServletResponse) res;
		response.setCharacterEncoding("UTF-8");
		
	
		super.doFilter(request, response, chain);
	}
}
