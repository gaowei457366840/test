package com.ringtop.entity;

import com.ringtop.common.entity.POJO;

/**
 * 段子实体信息
 * @author Administrator
 *
 */
public class Paragraph implements POJO {

	private static final long serialVersionUID = 1L;

	private String id;
	
	//内容
	private String context;
	//审核人
	private String auditorCode;
	//审核时间
	private String auditorDate;
	//发起人
	private String initiateCode;
	//发起时间
	private String initiateDate;
	//赞同数-赞
	private int agree;
	//反对数-踩
	private int oppose;
	//状态 0-表示待审核；1-表示审核通过；2-表示审核未通过；3-删除；
	private int status;
	//置顶状态：0-表示默认；1-表示置顶
	private int topStatus;
	
	
	//临时字段，不用映射入数据库
	private String initiateBeginDate;
	private String initiateEndDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getAuditorCode() {
		return auditorCode;
	}
	public void setAuditorCode(String auditorCode) {
		this.auditorCode = auditorCode;
	}
	public String getAuditorDate() {
		return auditorDate;
	}
	public void setAuditorDate(String auditorDate) {
		this.auditorDate = auditorDate;
	}
	public String getInitiateCode() {
		return initiateCode;
	}
	public void setInitiateCode(String initiateCode) {
		this.initiateCode = initiateCode;
	}
	public String getInitiateDate() {
		return initiateDate;
	}
	public void setInitiateDate(String initiateDate) {
		this.initiateDate = initiateDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getTopStatus() {
		return topStatus;
	}
	public void setTopStatus(int topStatus) {
		this.topStatus = topStatus;
	}
	public String getInitiateBeginDate() {
		return initiateBeginDate;
	}
	public void setInitiateBeginDate(String initiateBeginDate) {
		this.initiateBeginDate = initiateBeginDate;
	}
	public String getInitiateEndDate() {
		return initiateEndDate;
	}
	public void setInitiateEndDate(String initiateEndDate) {
		this.initiateEndDate = initiateEndDate;
	}
}
