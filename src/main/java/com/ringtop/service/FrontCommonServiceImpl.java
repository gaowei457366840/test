package com.ringtop.service;

import java.util.List;
import java.util.Map;

import com.ringtop.common.dao.impl.AbstractDAO;
import com.ringtop.common.util.Assert;

public class FrontCommonServiceImpl implements FrontCommonService {
	
	private AbstractDAO abstractDAO = new AbstractDAO();

	/***********************************************************
	 * 更新赞信息
	 * @param id		主键Id
	 * @param type		对应的类型：0-话题；1-话题评论；2-段子；3-段子评论；
	  * @param operate	取消赞（踩）或者添加赞（踩）的标识；0-增加；1 减少；
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String updateAgree(String id, String type, String operate) {
		
		String returnValue = "";
		String tableName = "";
		
		if(Assert.hasText(id)) {
			
			if("0".equals(type)) {
				
				tableName = "sc_topic";
				
			} else if("1".equals(type)) {
				
				tableName = "sc_topic_comment";
				
			} else if("2".equals(type)) {
				
				tableName = "sc_paragraph";
				
			} else if("3".equals(type)) {
				
				tableName = "sc_paragraph_comment";
				
			} else {
				
				return returnValue;
			}
			
			String sqlUpdate = " update " + tableName;
			if("0".equals(operate)) {
				sqlUpdate += " t set t.agree=t.agree+1 where t.id=?";
			} else if("1".equals(operate)) {
				sqlUpdate += " t set t.agree=t.agree-1 where t.id=?";
			} else {
				sqlUpdate += " t set t.agree=t.agree where t.id=?";
			}
			
			String sqlSelect = " select t.agree from " + tableName + " t where t.id=?";
			
			abstractDAO.execSql(sqlUpdate, new Object[] {id});
			List<Map<String, Object>> list = abstractDAO.execQuery(sqlSelect, new Object[] {id});
			returnValue = list.get(0).get("agree").toString();
		}
		
		return returnValue;
	}
	
	
	
	/***********************************************************
	 * 更新踩信息
	 * @param id		主键Id
	 * @param type		对应的类型：0-话题；1-话题评论；2-段子；3-段子评论；
	 * @param operate	取消赞（踩）或者添加赞（踩）的标识；0-增加；1 减少；
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String updateOppose(String id, String type, String operate) {
		
		String returnValue = "";
		String tableName = "";
		
		if(Assert.hasText(id)) {
			
			if("0".equals(type)) {
				
				tableName = "sc_topic";
				
			} else if("1".equals(type)) {
				
				tableName = "sc_topic_comment";
				
			} else if("2".equals(type)) {
				
				tableName = "sc_paragraph";
				
			} else if("3".equals(type)) {
				
				tableName = "sc_paragraph_comment";
				
			} else {
				
				return returnValue;
			}
			
			String sqlUpdate = " update " + tableName;
			if("0".equals(operate)) {
				sqlUpdate += " t set t.oppose=t.oppose+1 where t.id=?";
			} else if("1".equals(operate)) {
				sqlUpdate += " t set t.oppose=t.oppose-1 where t.id=?";
			} else {
				sqlUpdate += " t set t.oppose=t.oppose where t.id=?";
			}
			
			String sqlSelect = " select t.oppose from " + tableName + " t where t.id=?";
			
			abstractDAO.execSql(sqlUpdate, new Object[] {id});
			List<Map<String, Object>> list = abstractDAO.execQuery(sqlSelect, new Object[] {id});
			returnValue = list.get(0).get("oppose").toString();
		}
		
		return returnValue;
	}

}
