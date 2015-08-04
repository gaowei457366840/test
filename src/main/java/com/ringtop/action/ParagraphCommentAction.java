package com.ringtop.action;

import com.ringtop.common.action.BaseAction;
import com.ringtop.common.util.Assert;
import com.ringtop.common.util.ResultPage;
import com.ringtop.entity.Paragraph;
import com.ringtop.entity.ParagraphComment;
import com.ringtop.service.picture.ParagraphCommentService;
import com.ringtop.service.picture.ParagraphService;

/**
 * �������۹���action
 * @author Administrator
 *
 */
public class ParagraphCommentAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private ParagraphCommentService paragraphCommentService;
	
	private ParagraphService paragraphService;
	
	private ParagraphComment pc = new ParagraphComment();

	/*********************************************
	 * ����������۹�����ҳ��
	 * @return
	 */
	public String loadParagraphCommentList() {
		
		String command = this.getRequest().getParameter("command");
		if(!"query".equals(command)) {
			pc.setStatus(-1);
		}
		
		@SuppressWarnings("unused")
		ResultPage resultPage = this.paragraphCommentService.getResultPage(
						pc.getParagraph() != null ? pc.getParagraph().getContext() : "",
						pc.getUserCode(), pc.getCreateBeginDate(), pc.getCreateEndDate(),
						pc.getContext(),pc.getStatus());
		
		this.writeJqueryEasyuiResultPage(resultPage);
		
		return null;
	}
	
	
	/*********************************************
	 * ɾ�����������Ϣ
	 * @return
	 */
	public String delParagraphCommentById() {
		
		String msg = "1-ɾ�����������Ϣ�ɹ���";
		
		try {
			this.paragraphCommentService.delParagraphCommentById(pc.getId());
		} catch (Exception e) {
			e.printStackTrace();
			msg = "0-ɾ�����������Ϣʧ�ܣ�";
		}
		
		this.getJSONObjectByMessage("msg", msg);
		
		return null;
	}
	
	
	/*********************************************
	 * ��Ӻ�̨��������
	 * @return
	 */
	public String addPagePCInput() {
		
		String paragraphId = this.getRequest().getParameter("paragraphId");
		Paragraph paragraph = this.paragraphService.findParagraphById(paragraphId);
		
		this.setRequestAttribute("paragraph", paragraph);
		
		return "add_pc";
	}
	
	
	/**
	 * ��̨���������ύ
	 * @return
	 */
	public String addPagePCSubmit() {
		
		String msg = "1-��Ӷ������۳ɹ���";
		
		try {
			
			String paragraphId = this.getRequest().getParameter("paragraphId");
			Paragraph Paragraph = this.paragraphService.findParagraphById(paragraphId);
			if(Paragraph != null) {
				
				pc.setParagraph(Paragraph);
				this.paragraphCommentService.addParagraphComment(pc);
			} else {
				
				msg = "0-û���ҵ������۵Ķ�����Ϣ����ˢ��ҳ�����ԣ�";
			}
		} catch (Exception e) {
			msg = "0-��Ӷ�������ʧ�ܣ�";
		}
		
		this.writeWithJsonObject(this.getResponse(), msg);
		
		return null;
	}
	
	/*********************************************
	 * �����޸���Ϣҳ��
	 * @return
	 */
	public String updateParagraphCommentInput() {
		
		if(Assert.hasText(pc.getId())) {
			
			this.setRequestAttribute("pc", this.paragraphCommentService.findParagraphCommentById(pc.getId()));
		} else {
			this.setRequestAttribute("error", "û���ҵ�Ҫ���µĶ���");
		}
		
		return "update_input";
	}

	
	/*********************************************
	 * �ύ�޸ĵĶ���������Ϣ
	 * @return
	 */
	public String updateParagraphCommentSubmit() {
		
		String msg = "1-�޸Ķ���������Ϣ�ɹ���";
		
		try {
			ParagraphComment updatePComment = this.paragraphCommentService.findParagraphCommentById(pc.getId());
			
			if(updatePComment != null) {
				updatePComment.setAgree(pc.getAgree());
				updatePComment.setContext(pc.getContext());
				updatePComment.setOppose(pc.getOppose());
				updatePComment.setStatus(pc.getStatus());
				updatePComment.setUserCode(pc.getUserCode());
				updatePComment.setCreateDate(pc.getCreateDate());
				
				this.paragraphCommentService.updateParagraphComment(updatePComment);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			msg = "0-�޸Ķ���������Ϣʧ�ܣ�";
		}
		
		this.writeWithJsonObject(this.getResponse(), msg);
		
		return null;
	}

	
	
	
	
	
	
	
	
	

	public ParagraphCommentService getParagraphCommentService() {
		return paragraphCommentService;
	}

	public void setParagraphCommentService(
			ParagraphCommentService paragraphCommentService) {
		this.paragraphCommentService = paragraphCommentService;
	}

	public ParagraphService getParagraphService() {
		return paragraphService;
	}

	public void setParagraphService(ParagraphService paragraphService) {
		this.paragraphService = paragraphService;
	}

	public ParagraphComment getPc() {
		return pc;
	}

	public void setPc(ParagraphComment pc) {
		this.pc = pc;
	}
}
