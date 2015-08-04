package com.ringtop.entity;

import java.io.File;

import com.ringtop.common.entity.POJO;

/**
 * 用户信息实体
 * @author Administrator
 *
 */
public class ScUser implements POJO {

	private static final long serialVersionUID = 1L;
	//主键标识
	private String id;
	//用户帐号
	private String userCode;
	//用户名称
	private String userName;
	//后台管理员密码
	private String password;
	//手机型号
	private String imei;
	//电话号码
	private String phoneCode;
	//QQ号码
	private String qqNum;
	//新浪号码
	private String sinaNum;
	//创建时间
	private String createDate;
	//最后一次登录时间
	private String lastLoginDate;
	//用户备注
	private String remark;
	//用户状态：1-正常；0-停用；
	private int status;	
	//用户注册来源：1-客户端；2-网页；
	private int source;
	//密码盐
	private String encryptpara;
	//用户头像
	private String portrait;
	
	//头像上传临时字段，不用写入数据库
	private File tempFile;
	private String tempFileContentType;
	private String tempFileFileName;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getPhoneCode() {
		return phoneCode;
	}
	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}
	public String getQqNum() {
		return qqNum;
	}
	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}
	public String getSinaNum() {
		return sinaNum;
	}
	public void setSinaNum(String sinaNum) {
		this.sinaNum = sinaNum;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public String getEncryptpara() {
		return encryptpara;
	}
	public void setEncryptpara(String encryptpara) {
		this.encryptpara = encryptpara;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public File getTempFile() {
		return tempFile;
	}
	public void setTempFile(File tempFile) {
		this.tempFile = tempFile;
	}
	public String getTempFileContentType() {
		return tempFileContentType;
	}
	public void setTempFileContentType(String tempFileContentType) {
		this.tempFileContentType = tempFileContentType;
	}
	public String getTempFileFileName() {
		return tempFileFileName;
	}
	public void setTempFileFileName(String tempFileFileName) {
		this.tempFileFileName = tempFileFileName;
	}
}
