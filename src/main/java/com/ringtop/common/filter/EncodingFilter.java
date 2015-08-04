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
public class EncodingFilter implements Filter{

	private FilterConfig filterConfig;

	private String encoding = null;

	public void destroy() {

	}

	public void doFilter(ServletRequest srequest, ServletResponse sresponse,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) srequest;
		HttpServletResponse response = (HttpServletResponse) sresponse;
		
		if (encoding == null) {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
		} else {
			request.setCharacterEncoding(encoding);
			response.setContentType("text/html;charset=" + encoding);
			
		}
		String requestURL = ((HttpServletRequest) request).getRequestURL().toString();

		if (requestURL.toLowerCase().indexOf(".htm") == -1      &&
				requestURL.toLowerCase().indexOf(".gif") == -1  && 
				requestURL.toLowerCase().indexOf(".lpk") == -1  && 
				requestURL.toLowerCase().indexOf(".jpg") == -1) {
			
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
		}
		
		chain.doFilter(request, response);

	}

	public void init(FilterConfig config) throws ServletException {
		
		filterConfig = config;
		encoding = filterConfig.getInitParameter("encoding");

	}


}
