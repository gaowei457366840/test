package com.ringtop.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ringtop.common.dao.impl.AbstractDAO;
import com.ringtop.common.util.Assert;
import com.ringtop.common.util.Parameters;

public class DISInitListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {

	}

	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent sce) {

		Map<String, String> map001 = new HashMap<String, String>();
		
		String sql = " select t.serverCode,t.serverIP,t.serverPort,t.actionPath from sc_backconfig t where t.serverCode=?";
		AbstractDAO abstractDAO = new AbstractDAO();
		
		@SuppressWarnings("unused")
		List<Map<String, Object>> list = abstractDAO.execQuery(sql, new Object[] {"DossierCamion"});
		
		if(Assert.notEmpty(list)) {
			
			@SuppressWarnings("unused")
			Map<String, Object> map = list.get(0);
			@SuppressWarnings("unused")
			String httpImageSaveUrl = "http://" + map.get("serverIP").toString() + ":" + map.get("serverPort") + "/" + map.get("actionPath");
			map001.put("httpImageSaveUrl", httpImageSaveUrl);
		} 
		
		Parameters.map = map001;
	}

}
