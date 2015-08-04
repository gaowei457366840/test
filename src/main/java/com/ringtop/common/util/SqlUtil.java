package com.ringtop.common.util;

public class SqlUtil {
	/**
	 * 判断何时在sql语句中添�?"where"�?"and"
	 * @return
	 */
	public static boolean appendWhereIfNeed(StringBuffer sql, boolean hasWhere) {
		if (hasWhere == false) {
			sql.append(" where ");
		} else {
			sql.append(" and ");
		}
		return true;
	}
	
	/**
	 * 用于没有子查询的情况
	 * @param sql
	 * @author bm.he
	 * @date 2012-12-20 下午12:01:07
	 */
	public static void appendWhereIfNeed(StringBuffer sql){
		if(sql.toString().indexOf("where") > 0){
			sql.append(" and ");
		}else{
			sql.append(" where ");
		}
	}
	
	/**
	 * oracle分页
	 * @return
	 * @author bm.he
	 * @date 2012-12-19 下午07:44:29
	 */
	public static String toOrcalPageSql(String sql,int page,int pageSize){
		StringBuffer pageSql = new StringBuffer("select * from (");
		pageSql.append("select row_.*,rownum rn from (");
		pageSql.append(sql);
		pageSql.append(") row_ where rownum <= " + page * pageSize);
		pageSql.append(") where rn > " + (page - 1) * pageSize);
		return pageSql.toString();
	}
	
	/**
	 * TreeList   oracle分页
	 * @return
	 * @author bm.he
	 * @date 2012-12-19 下午07:44:29
	 */
	public static String toOrcalPageTreeList(String sql,int start,int pageSize){
		if(start == 1){
			start = 0;
		}
		StringBuffer pageSql = new StringBuffer("select * from (");
		pageSql.append("select row_.*,rownum rn from (");
		pageSql.append(sql);
		pageSql.append(") row_ where rownum <= " + (start + pageSize));
		
		pageSql.append(") where rn > " + start);
		return pageSql.toString();
	}
	

	/**
	 * 判断操作符类�? ，处理参�?
	 * @param operator
	 * @param param
	 * @return
	 * @author bm.he
	 * @date 2012-12-28 下午02:57:54
	 */
	@SuppressWarnings("unused")
	private static Object getProParam(String operator, Object param) {
		if("like".equals(operator)){
			return "%"+param+"%";
		}else{
			return param;
		}
	}
	
	/**
	 * 处理Treelist的分�?
	 */
	public static void formatTreeListPage(){
		//页号
		int start = PageUtil.getPageNo();
		//每页数量
		int pageSize = PageUtil.getPageSize();
		
//		if(start == 1){
//			start =0;
//		}
		//设置页号和每页数�?
		PageUtil.setPageNo(start % pageSize == 0 ? start / pageSize + 1 : start % pageSize);
		PageUtil.setPageSize(pageSize);
	}
	
}
