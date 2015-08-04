package com.ringtop.entity;

/**
 * 菜单信息实体
 * @author Administrator
 *
 */
public class ScMenu {

	private String id;
	private String pmenuId;
	private String menuCode;
	private String menuName;
	private int menuLevel;
	private int menuOrder;
	private String url;
	private int isLeaf;
	//状态：1-正常；0-停用；
	private int status;

	public int getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(int isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getPmenuId() {
		return pmenuId;
	}
	public void setPmenuId(String pmenuId) {
		this.pmenuId = pmenuId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public int getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}
	public int getMenuOrder() {
		return menuOrder;
	}
	public void setMenuOrder(int menuOrder) {
		this.menuOrder = menuOrder;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
