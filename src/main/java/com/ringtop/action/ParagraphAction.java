package com.ringtop.action;

import com.ringtop.common.action.BaseAction;
import com.ringtop.common.util.Assert;
import com.ringtop.common.util.DateUtil;
import com.ringtop.common.util.ResultPage;
import com.ringtop.entity.Paragraph;
import com.ringtop.service.picture.ParagraphService;

/**
 * ������Ϣ����action
 * @author Administrator
 *
 */
public class ParagraphAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private Paragraph p = new Paragraph();
	
	private ParagraphService paragraphService;
	
	/*******************************************
	 * ��ȡ������Ϣ�б�
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String loadParagraphList() {
		
		String command = this.getRequest().getParameter("command");
		if(!"query".equals(command)) {
			p.setStatus(-1);
			p.setTopStatus(-1);
		}
		
		ResultPage resultPage = this.paragraphService.getResultPage(p.getAuditorCode(),p.getInitiateCode(),
						p.getInitiateBeginDate(),p.getInitiateEndDate(),p.getStatus(),p.getTopStatus());
		
		this.writeJqueryEasyuiResultPage(resultPage);
		
		return null;
	}
	
	
	/*******************************************
	 * ��Ӻ�̨������Ϣ
	 * @return
	 */
	public String addPageParagraph() {
		
		String msg = "1-��Ӷ�����Ϣ�ɹ���";
		
		try {
			
			p.setInitiateDate(DateUtil.getDate(DateUtil.FMT_YY_MM_DD_HH_MM_SS));
			this.paragraphService.addParagraph(p);
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			msg = "0-��Ӷ�����Ϣʧ�ܣ�";
		}
		
		this.ajaxFormFileUploadWriteWithJsonObject(this.getResponse(), msg);
		
		return null;
	}
	
	
	/*******************************************
	 * ���������Ϣ�޸�ҳ��
	 * @return
	 */
	public String updatePageParagraphInput() {
		
		if(Assert.hasText(p.getId())) {
			p = this.paragraphService.findParagraphById(p.getId());
			this.setRequestAttribute("p", p);
		} else {
			this.setRequestAttribute("error", "û���ҵ����µĶ��������������쳣������ϵ����Ա���߹ر���������ԣ�");
		}
		return "update_input";
	}
	
	
	/*******************************************
	 * ���º�̨������Ϣ
	 * @return
	 */
	public String updatePageScParagraph() {
		
		String msg = "1-���¶�����Ϣ�ɹ���";
		
		Paragraph updateParagraph = this.paragraphService.findParagraphById(p.getId());
		
		if(updateParagraph == null) {
			
			msg = "0-û���ҵ����µĶ�����Ϣ��";
			
		} else {
			
			try {
				
				this.paragraphService.updateParagraph(p);
				
			} catch (RuntimeException e) {
				e.printStackTrace();
				msg = "0-���¶�����Ϣʧ�ܣ�";
			}
		}
		
		this.ajaxFormFileUploadWriteWithJsonObject(this.getResponse(), msg);
		
		return null;
	}
	
	
	/*******************************************
	 * ɾ�������Ϣ
	 * @return
	 */
	public String delParagraph() {
		
		String msg = "1-ɾ�������Ϣ�ɹ���";
		try {
			this.paragraphService.delParagraph(p.getId());
		} catch (Exception e) {
			e.printStackTrace();
			msg = "0-ɾ�������Ϣʧ��";
		}
		
		this.getJSONObjectByMessage("msg", msg);
		
		return null;
	}


	
	
	
	
	public Paragraph getP() {
		return p;
	}
	public void setP(Paragraph p) {
		this.p = p;
	}
	public ParagraphService getParagraphService() {
		return paragraphService;
	}
	public void setParagraphService(ParagraphService paragraphService) {
		this.paragraphService = paragraphService;
	}
}
