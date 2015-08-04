package com.ringtop.common.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.ringtop.common.util.ExceptionToString;
import com.ringtop.common.util.Parameters;

/**
 * 获取数据连接
 * @author Administrator
 *
 */
public class JdbcUtil {

	private Connection connection;
	private static Map<String,DataSource> dsMap = new HashMap<String,DataSource>(3);
	
	Logger logger = Logger.getLogger(JdbcUtil.class);

	/*****************************************************
	 * 获取Connection连接
	 * @return
	 * @throws SQLException
	 */
	public synchronized Connection openConnection() throws SQLException {
		Connection conn = null;
		DataSource dataSource = dsMap.get(Parameters.jndiName);
		if (dataSource == null) {
			try {
				Context initCtx = new InitialContext();
				Context envCtx = (Context) initCtx.lookup("java:comp/env");
				dataSource = (DataSource) envCtx.lookup(Parameters.jndiName);
				if(dataSource!=null){
					dsMap.remove(Parameters.jndiName);
					dsMap.put(Parameters.jndiName, dataSource);
				}
			} catch (Exception e) {
				throw new ExceptionInInitializerError(e.getMessage());
			}
		}
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		return conn;
	}
	
	
	public DataSource getDataSource() {
		
		DataSource dataSource = dsMap.get(Parameters.jndiName);;
		try {
			
			if(dataSource == null) {
				Context initCtx = new InitialContext();
				Context envCtx = (Context) initCtx.lookup("java:comp/env");
				dataSource = (DataSource) envCtx.lookup(Parameters.jndiName);
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
			logger.info(ExceptionToString.getTrace(e));
		}
		
		return dataSource;
	}
	
	
	/*****************************************************
	 * 开启session
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		if (connection != null) {
			return;
		}
		try {
			connection = this.openConnection();
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	
	/*****************************************************
	 * 判断session是否开启
	 * @return
	 */
	public boolean isOpen() {
		if (connection != null) {
			return true;
		}
		return false;
	}

	
	/*****************************************************
	 * 获取Connection连接
	 * @return
	 */
	public synchronized Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				connection = null;
				connection = openConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	
	/*****************************************************
	 * 关闭connection连接
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
				throw new SQLException(ex.getMessage()); 
			}
		}
		connection = null;
	}

	
	/*****************************************************
	 * 关闭资源，断开connection连接
	 */
	public void free() {
		try {
			if (connection != null) {
				if (!connection.isClosed()) {
					connection.close();
				}

			}
		} catch (SQLException ex) {
			ex.printStackTrace();

		} finally {
			connection = null;
		}
	}

	/*****************************************************
	 * 关闭打开的资源
	 * @param rs
	 * @param st
	 * @param conn
	 */
	public void free(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null)
					try {
						if (!conn.isClosed()) {
							conn.close();
						}
						conn = null;
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
	}
}
