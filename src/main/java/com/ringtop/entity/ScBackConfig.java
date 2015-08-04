package com.ringtop.entity;

import com.ringtop.common.entity.POJO;

/**
 * 后台服务实体
 * @author Administrator
 *
 */
public class ScBackConfig implements POJO {

	private static final long serialVersionUID = 1L;
	//服务主键
	private String id;
	//服务名称
	private String serverName;
	//服务所在路径
	private String serverPath;
	//服务IP
	private String serverIP;
	//服务端口
	private Integer serverPort;
	//服务编号
	private String serverCode;
	//服务访问路径
	private String actionPath;
	//运行状态
	private Integer status;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getServerPath() {
		return serverPath;
	}
	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}
	public String getServerIP() {
		return serverIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	public String getServerCode() {
		return serverCode;
	}
	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}
	public String getActionPath() {
		return actionPath;
	}
	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
	}
	public Integer getServerPort() {
		return serverPort;
	}
	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
