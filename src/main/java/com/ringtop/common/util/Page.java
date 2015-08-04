package com.ringtop.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @作者 Uncle_Dallas
 * @功能描述 分页对象 必须得添加序列化，方便日志存储
 * @时间 2012-2-22
 * @版本 V1.0
 */
public class Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5013884730964660496L;
	/** 返回数据，在EasyUI中是约定的名字的必须是rows */
	private List<?> rows;
	/** 每页大小 */
	private int pageSize;

	/** 返回总记录数，在EasyUI中是约定的名字 */
	private int total;
	/** 临时设置起始位置 */
	private int startIndex;
	/** 临时设置起始位置 */
	private int totalPage;
	/** the page number **/
	

	public Page() {
		super();
	}
	public Page(List<?> rows) {
		this.rows = rows;
	}

	public Page(List<?> rows, int total) {
		super();
		this.rows = rows;
		this.total = total;
	}
	
	@SuppressWarnings("unchecked")
	public Page(List<?> rows, int start, int total, int pageSize) {
		this.pageSize = 15;

		if (rows == null) {
			this.rows = new ArrayList();
		} else if (rows.size() <= pageSize) {
			this.rows = rows;
		} else {
			this.rows = rows;
		}
		this.startIndex = start;
		this.pageSize = ((pageSize > 0) ? pageSize : 15);
		this.total = total;
		
		//总页数
		if(this.total==0){
			this.totalPage =0;
		}else{
			this.totalPage = this.total/this.pageSize+1;
		}
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}