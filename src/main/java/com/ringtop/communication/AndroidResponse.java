/**
 * bai
 */
package com.ringtop.communication;

import java.io.Serializable;
import java.util.List;

/**
 * 与前端通信的返回实体
 * @author bm.he
 * @date 2013-12-6 上午11:38:07
 */
public class AndroidResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private String status; //ok成功，fail失败
	
	private String msg; //消息
	
	private List rows;//数据信息

	
	public AndroidResponse() {
		
	}
	
	public AndroidResponse(String status, String msg, List rows) {
		
		this.status = status;
		this.msg = msg;
		this.rows = rows;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the rows
	 */
	public List getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List rows) {
		this.rows = rows;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
