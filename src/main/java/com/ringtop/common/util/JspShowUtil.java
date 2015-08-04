package com.ringtop.common.util;

public class JspShowUtil {

	/**********************************************
	 * 如果是用户，则重点显示
	 * @param Name
	 * @param value
	 * @return
	 */
	public static String tRColorDisplay(String s) {

		if ( s == null)
			return "ffffff";
		if (s.equalsIgnoreCase("Y"))
			return "yellow";

		return "ffffff";
	}
	
	
	/**********************************************
	 * 
	 * @param index
	 * @return
	 */
	public static String tRColorDisplay(int index) {

		if (index%2 == 0)
			return "ffffff";
		if (index%2 == 1)
			return "#ffffff";

		return "ffffff";
	}
	
	
	/**********************************************
	 * 检索菜单
	 * @param menuIds
	 * @param menuCode
	 * @return
	 */
	public static boolean hasTheRoles(String menuIds,String menuCode){
		@SuppressWarnings("unused")
		StringBuffer sb=new StringBuffer();
		return menuIds.indexOf(menuCode)>-1;
	}
	
	
	/**********************************************
	 * 返回第一个菜单编号
	 * @param menuIds
	 * @return
	 */
	public static String getTheFirstMenuCode(String menuIds){
		String temp="";
		if(null!=menuIds&&menuIds.length()>0)
			temp=menuIds.substring(menuIds.indexOf('#')+1,menuIds.indexOf(','));
		
		return temp;
	}
}
