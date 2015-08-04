package com.ringtop.action;

import com.ringtop.common.action.BaseAction;
import com.ringtop.common.util.Assert;
import com.ringtop.common.util.ResultPage;
import com.ringtop.entity.Topic;
import com.ringtop.entity.TopicComment;
import com.ringtop.service.picture.TopicCommentService;
import com.ringtop.service.picture.TopicService;

/**
 * �������۹���action
 * @author Administrator
 *
 */
public class TopicCommentAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private TopicCommentService topicCommentService;
	
	private TopicService topicService;
	
	private TopicComment tc = new TopicComment();

	/*********************************************
	 * ���뻰�����۹�����ҳ��
	 * @return
	 */
	public String loadTopicCommentList() {
		
		String command = this.getRequest().getParameter("command");
		if(!"query".equals(command)) {
			tc.setStatus(-1);
		}
		
		@SuppressWarnings("unused")
		ResultPage resultPage = this.topicCommentService.getResultPage(
						tc.getTopic() != null ? tc.getTopic().getTitle() : "",
						tc.getUserCode(), tc.getCreateBeginDate(), tc.getCreateEndDate(),
						tc.getContext(),tc.getStatus());
		
		this.writeJqueryEasyuiResultPage(resultPage);
		
		return null;
	}
	
	
	/*********************************************
	 * ɾ����������Ϣ
	 * @return
	 */
	public String delTopicCommentById() {
		
		String msg = "1-ɾ����������Ϣ�ɹ���";
		
		try {
			this.topicCommentService.delTopicCommentById(tc.getId());
		} catch (Exception e) {
			e.printStackTrace();
			msg = "0-ɾ����������Ϣʧ�ܣ�";
		}
		
		this.getJSONObjectByMessage("msg", msg);
		
		return null;
	}
	
	
	/*********************************************
	 * ��Ӻ�̨��������
	 * @return
	 */
	public String addPageTCInput() {
		
		String topicId = this.getRequest().getParameter("topicId");
		Topic topic = this.topicService.findTopicById(topicId);
		
		this.setRequestAttribute("topic", topic);
		
		return "add_tc";
	}
	
	
	/**
	 * ��̨���������ύ
	 * @return
	 */
	public String addPageTCSubmit() {
		
		String msg = "1-��ӻ������۳ɹ���";
		
		try {
			
			String topicId = this.getRequest().getParameter("topicId");
			Topic topic = this.topicService.findTopicById(topicId);
			if(topic != null) {
				
				tc.setTopic(topic);
				this.topicCommentService.addTopicComment(tc);
			} else {
				
				msg = "0-û���ҵ������۵Ļ�����Ϣ����ˢ��ҳ�����ԣ�";
			}
		} catch (Exception e) {
			msg = "0-��ӻ�������ʧ�ܣ�";
		}
		
		this.writeWithJsonObject(this.getResponse(), msg);
		
		return null;
	}
	
	/*********************************************
	 * �����޸���Ϣҳ��
	 * @return
	 */
	public String updateTopicCommentInput() {
		
		if(Assert.hasText(tc.getId())) {
			
			this.setRequestAttribute("tc", this.topicCommentService.findTopicCommentById(tc.getId()));
		} else {
			this.setRequestAttribute("error", "û���ҵ�Ҫ���µĶ���");
		}
		
		return "update_input";
	}

	
	/*********************************************
	 * �ύ�޸ĵĻ���������Ϣ
	 * @return
	 */
	public String updateTopicCommentSubmit() {
		
		String msg = "1-�޸Ļ���������Ϣ�ɹ���";
		
		try {
			TopicComment updateTComment = this.topicCommentService.findTopicCommentById(tc.getId());
			
			if(updateTComment != null) {
				updateTComment.setAgree(tc.getAgree());
				updateTComment.setContext(tc.getContext());
				updateTComment.setOppose(tc.getOppose());
				updateTComment.setStatus(tc.getStatus());
				updateTComment.setUserCode(tc.getUserCode());
				updateTComment.setCreateDate(tc.getCreateDate());
				
				this.topicCommentService.updateTopicComment(updateTComment);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			msg = "0-�޸Ļ���������Ϣʧ�ܣ�";
		}
		
		this.writeWithJsonObject(this.getResponse(), msg);
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	public TopicService getTopicService() {
		return topicService;
	}
	public void setTopicService(TopicService topicService) {
		this.topicService = topicService;
	}
	public TopicCommentService getTopicCommentService() {
		return topicCommentService;
	}
	public void setTopicCommentService(TopicCommentService topicCommentService) {
		this.topicCommentService = topicCommentService;
	}
	public TopicComment getTc() {
		return tc;
	}
	public void setTc(TopicComment tc) {
		this.tc = tc;
	}
}
