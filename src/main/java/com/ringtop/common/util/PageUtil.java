package com.ringtop.common.util;

/**
* 用来收集pageNo,pageSize由于是用ThreadLocal
* 这样才会线程安全
* @author wl.yang
*
*/
public class PageUtil {
	
	private static ThreadLocal<Integer> pageNo = new ThreadLocal<Integer>();
	
	private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();
	
	public static void setPageNo(int _pageNo){
		pageNo.set(new Integer(_pageNo));
	}
	
	public static void setPageSize(int _pageSize){
		pageSize.set(new Integer(_pageSize));
	}
	
	public static int getPageNo(){
		if(pageNo.get() == null || "".equals(pageNo.get())) {
			return 1;
		} else {
			return ((Integer)pageNo.get()).intValue();
		}
	}
	
	public static int getPageSize(){
		
		if(pageSize.get() == null || "".equals(pageSize.get())) {
			return PageConfig.PAGE_SIZE;
		} else {
			return ((Integer)pageSize.get()).intValue();
		}
	}
	
	public static void removePageSize(){
		pageSize.remove();
	}
	
	public static void removePageNo(){
		pageNo.remove();
	}
}
