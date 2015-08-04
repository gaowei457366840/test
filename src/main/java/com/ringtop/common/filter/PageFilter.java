package com.ringtop.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.ringtop.common.util.PageConfig;
import com.ringtop.common.util.PageUtil;


/**
 * 处理分页时用来从页面上收集pageNo
 * 和pageSize的过虑器
 * @author 
 *
 */
public class PageFilter implements Filter {

	public void destroy() {

	}

	//在这里来收集pageNo
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		
		String pageNoStr = request.getParameter("page")==null ? "" : request.getParameter("page");
		String pageSizeStr = request.getParameter("rows")==null ? "" : request.getParameter("rows");
		
		int pageNo = 1;
		
		if(pageNoStr != null && !"".equals(pageNoStr) && !"0".equals(pageNoStr)){
			pageNo = Integer.parseInt(pageNoStr);
		}
		
		//是一个常量指定
		int pageSize = PageConfig.PAGE_SIZE ; 
		if(pageSizeStr != null && !"".equals(pageSizeStr)){
			pageSize = Integer.parseInt(pageSizeStr);
		}
		
		//放到ThreadLocal里面去
		PageUtil.setPageNo(pageNo);
		PageUtil.setPageSize(pageSize);
		
		filterChain.doFilter(request, resp);
		
		//回来的时候把收集到的值删除
		PageUtil.removePageNo();
		PageUtil.removePageSize();
		
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
