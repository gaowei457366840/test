package com.ringtop.common.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.ringtop.common.exception.BaseException;
import com.ringtop.common.jdbc.JdbcUtil;
import com.ringtop.common.jdbc.SessionFactory;
import com.ringtop.common.util.Assert;
import com.ringtop.common.util.ExceptionToString;
import com.ringtop.common.util.PageUtil;
import com.ringtop.common.util.ResultPage;

/*********************************************************************
 * jdbc����ִ��sql
 * @author Administrator
 *
 */
public class AbstractDAO {

	private Logger logger = Logger.getLogger(AbstractDAO.class);
	
	/****************************************************************
	 * ��ȡsession
	 * @return
	 * @throws SQLException
	 */
	public JdbcUtil getSession() throws SQLException {
		return SessionFactory.getSession();
	}
	
	/****************************************************************
	 * sql��ҳ��ѯ
	 * @param sql
	 * @param values
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public ResultPage getResultPage(String sql, Object[] values) throws Exception {
		
		//�������PageUtil��������pageNo,pageSize
		int pageNo = PageUtil.getPageNo();
		int pageSize = PageUtil.getPageSize();
		
		//��ѯ�������������ж����û���ͳ���������
		List countlist = this.execQuery(sql, values);
		if(pageSize < 0) {
			if(Assert.notEmpty(countlist)) {
				return new ResultPage(1, countlist.size(), countlist.size(), countlist);
			} else {
				return new ResultPage();
			}
		}
		
		int totalCount = 0;
		if(Assert.notEmpty(countlist)) {
			totalCount = countlist.size();
		}
		
		if(totalCount < 1) {
			return new ResultPage();
		}
		
		// ʵ�ʲ�ѯ���ط�ҳ����
		int startIndex = ResultPage.getStartOfPage(pageNo, pageSize);
		
		String pageSql = this.toOraclePageSql(sql, pageNo*pageSize, startIndex);
		List pageList = this.execQuery(pageSql, values);
		
		return new ResultPage(startIndex, totalCount, pageSize, pageList);
	}

	/****************************************************************
	 * ��ҳ	
	 * @param querySql
	 * @param max
	 * @param min
	 * @return
	 */
	public String toOraclePageSql(String querySql, int max, int min) {
		String sql = "select * from ("
				+ "  select row_. *,rownum rownum_ from (" + querySql
				+ ") row_ " + "   where rownum <= " + max + ") "
				+ " where rownum_ > " + min;
		return sql;
	}

	/****************************************************************
	 * ͳ��
	 * @param metaSQL
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int count(String metaSQL,Object[] values) {
		int rowSum = 0;
		String sql = "select count(*) ROWSUM from (" + metaSQL + ")";
		HashMap temp;
		try {
			temp = (HashMap) (this.execQuery(sql, values).get(0));
			rowSum = Integer.parseInt(temp.get("ROWSUM").toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rowSum;
	}

	
	/****************************************************************
	 * ִ��SQL��ѯ
	 * ������ݿ�����
	 * @param sql String
	 * @param values
	 * @return List
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List execQuery(String sql, Object[] values) {
		
		Connection conn = null;
		List list = null;
		
		try {
			conn = this.getSession().getConnection();
			list = execQuery(conn, sql,values);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			SessionFactory.closeSession();
		}
		
		return list;
	}
	
	/****************************************************************
	 * ���в���ֵ�����
	 * ִ��SQL��ѯ
	 * �Դ���ݿ�����
	 * @param conn
	 * @param sql
	 * @param values
	 * @return ��ѯ�����б�
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List execQuery(Connection conn, String sql, Object[] values) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Vector list = new Vector();
			pstmt = conn.prepareStatement(sql);
			if(values != null && values.length > 0) {
				for(int i=0; i<values.length; i++) {
					pstmt.setString(i+1, values[i].toString());
				}
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {
				HashMap row = new HashMap();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					row.put(rsmd.getColumnName(i), rs.getObject(i));
				}
				list.add(row);
			}
			return list;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new Exception("execQuery:ERROR" + ex.getMessage());
		} finally {
			try {
				this.getSession().free(rs, pstmt, conn);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception("execQuery:ERROR" + e.getMessage());
			}
		}
	}


	/****************************************************************
	 * ִ��sql
	 * @param 				sql
	 * @param				values	
	 * @return ResultSet 	���
	 */
	public void execSql(String sql, Object[] values) {
		
		PreparedStatement ps = null;
		try {
			ps = getPrepareStatement(sql);
			if(values != null && values.length > 0) {
				for(int i=0; i<values.length; i++) {
					ps.setString(i+1, values[i].toString());
				}
			}
			
			ps.execute();
			
		} catch (SQLException e) {
			logger.info(sql);
			logger.info(ExceptionToString.getTrace(e));
		} finally {
			SessionFactory.closeSession();
		}
	}

	/****************************************************************
	 * ����PreparedStatement
	 * @return
	 */
	protected PreparedStatement getPrepareStatement(String sql) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = this.getSession().getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
		} catch (SQLException e) {
			throw new BaseException(e.getMessage());
		}
		return ps;
	}
	
}
