package com.ringtop.service;

/**
 * 封装前台一些公共的方法
 * @author Administrator
 *
 */
public interface FrontCommonService {

	/***********************************************************
	 * 更新赞信息
	 * @param id		主键Id
	 * @param type		对应的类型：0-话题；1-话题评论；2-段子；3-段子评论；
	 * @param operate	取消赞（踩）或者添加赞（踩）的标识；0-增加；1 减少；
	 * @return
	 */
	public String updateAgree(String id, String type, String operate);
	
	
	/***********************************************************
	 * 更新踩信息
	 * @param id	主键Id
	 * @param type	对应的类型：0-话题；1-话题评论；2-段子；3-段子评论；
	 * @param operate	取消赞（踩）或者添加赞（踩）的标识；0-增加；1 减少；
	 * @return
	 */
	public String updateOppose(String id, String type, String operate);
}
