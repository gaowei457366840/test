package com.ringtop.entity;

import com.ringtop.common.entity.POJO;

/**
 * 用户评论
 * 用户评论信息表
 * @author Administrator
 *
 */
public class TopicComment implements POJO {

	private static final long serialVersionUID = 1L;
	
	//主键标识
	private String id;
	//评论用户
	private String userCode;
	//创建时间
	private String createDate;
	//评论内容
	private String context;
	//赞同数-赞
	private int agree;
	//反对数-踩
	private int oppose;
	//所属主题
	private Topic topic;
	//评论状态 0-屏蔽；1-正常；
	private int status;
	
	//临时字段
	private String createBeginDate;
	private String createEndDate;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public int getAgree() {
		return agree;
	}
	public void setAgree(int agree) {
		this.agree = agree;
	}
	public int getOppose() {
		return oppose;
	}
	public void setOppose(int oppose) {
		this.oppose = oppose;
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreateBeginDate() {
		return createBeginDate;
	}
	public void setCreateBeginDate(String createBeginDate) {
		this.createBeginDate = createBeginDate;
	}
	public String getCreateEndDate() {
		return createEndDate;
	}
	public void setCreateEndDate(String createEndDate) {
		this.createEndDate = createEndDate;
	}
}
