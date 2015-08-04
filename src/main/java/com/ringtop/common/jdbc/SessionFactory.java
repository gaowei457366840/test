package com.ringtop.common.jdbc;

import java.sql.SQLException;

/**
 * 定制的sessionFactory 工厂
 * @author Administrator
 *
 */
public class SessionFactory {
	
	private static final ThreadLocal<JdbcUtil> threadLocal = new ThreadLocal<JdbcUtil>();
	
	private SessionFactory(){
	}
	
	public static JdbcUtil getSession() {
		JdbcUtil session = (JdbcUtil) threadLocal.get();
	    if (session == null || !session.isOpen()) {
	    	session = openSession();
    		threadLocal.set(session);
	    }
	    return session;
	}
	  
	public static void closeSession() {  
		JdbcUtil session = (JdbcUtil) threadLocal.get();
		threadLocal.set(null);
		if (session != null) {
			session.free();
		}
	}
	public static JdbcUtil openSession() {
		JdbcUtil session = new JdbcUtil();
		try {
			session.open();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return session;
	}
	
}
