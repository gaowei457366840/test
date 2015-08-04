package com.ringtop.action.frontaction;

import java.util.ArrayList;
import java.util.List;

import com.ringtop.common.action.BaseAction;
import com.ringtop.communication.AndroidResponse;
import com.ringtop.service.FrontCommonService;

/**
 * ǰ���޻��߲ȹ���action
 * @author Administrator
 *
 */
public class FrontAgreeAndOpposeAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private FrontCommonService frontCommonService;
	
	//����Id
	private String id;
	//��Ӧ�����ͣ�0-���⣻1-�������ۣ�2-���ӣ�3-�������ۣ�
	private String type;
	//ȡ���ޣ��ȣ���������ޣ��ȣ��ı�ʶ��0-���ӣ�1 ���٣�
	private String operate;
	
	/***********************************************
	 * ��
	 * @return
	 */
	public String agree() {
		
		AndroidResponse res = new AndroidResponse();
		
		try {
			
			String agreeStr = this.frontCommonService.updateAgree(id, type,operate);
			
			res.setStatus("ok");
			res.setMsg("����Ϣ���³ɹ���");
			
			List<String> rows = new ArrayList<String>();
			rows.add(agreeStr);
			res.setRows(rows);
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			res.setStatus("fail");
			res.setMsg("����Ϣ���³��쳣��");
		}
		
		
		this.writeWithJsonObject(this.getResponse(), res);
		
		return null;
	}
	
	
	/***********************************************
	 * ��
	 * @return
	 */
	public String oppose() {
		
		AndroidResponse res = new AndroidResponse();
		
		try {
			
			String agreeStr = this.frontCommonService.updateOppose(id, type, operate);
			
			res.setStatus("ok");
			res.setMsg("����Ϣ���³ɹ���");
			
			List<String> rows = new ArrayList<String>();
			rows.add(agreeStr);
			res.setRows(rows);
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			res.setStatus("fail");
			res.setMsg("����Ϣ���³��쳣��");
		}
		
		
		this.writeWithJsonObject(this.getResponse(), res);
		
		return null;
	}

	
	
	
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public FrontCommonService getFrontCommonService() {
		return frontCommonService;
	}
	public void setFrontCommonService(FrontCommonService frontCommonService) {
		this.frontCommonService = frontCommonService;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
