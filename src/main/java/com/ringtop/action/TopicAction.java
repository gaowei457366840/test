package com.ringtop.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ringtop.common.action.BaseAction;
import com.ringtop.common.util.Assert;
import com.ringtop.common.util.DateUtil;
import com.ringtop.common.util.Parameters;
import com.ringtop.common.util.ResultPage;
import com.ringtop.entity.Image;
import com.ringtop.entity.ScUser;
import com.ringtop.entity.Topic;
import com.ringtop.service.picture.ImageService;
import com.ringtop.service.picture.TopicService;

/**
 * ������Ϣ����action
 * @author Administrator
 *
 */
public class TopicAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private Topic topic = new Topic();
	
	private TopicService topicService;
	
	private ImageService imageService;
	
	/*******************************************
	 * ��ȡ������Ϣ�б�
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String loadTopicList() {
		
		String command = this.getRequest().getParameter("command");
		if(!"query".equals(command)) {
			topic.setStatus(-1);
		}
		ResultPage resultPage = this.topicService.getResultPage(topic.getTitle(), topic.getAuditorCode(),topic.getInitiateCode(),
						topic.getInitiateBeginDate(),topic.getInitiateEndDate(),topic.getStatus());
		
		this.writeJqueryEasyuiResultPage(resultPage);
		
		return null;
	}
	
	
	/*******************************************
	 * ��Ӻ�̨������Ϣ
	 * @return
	 */
	public String addPageScTopic() {
		
		String msg = "1-��ӻ�����Ϣ�ɹ���";
		
		try {
			String path = "/" + DateUtil.getDate(DateUtil.FMT_YYMMDD) + "/thumbnail/";
			@SuppressWarnings("unused")
			String fileName = this.uploadFileHTTP(topic.getTempFile(), path, topic.getTempFileFileName());
			topic.setThumbnailFilePath(fileName);
			topic.setInitiateDate(DateUtil.getDate(DateUtil.FMT_YY_MM_DD_HH_MM_SS));
			if(Assert.hasTextNull(topic.getInitiateCode())) {
				ScUser scUser = (ScUser)this.getSession().getAttribute("disUser");
				if(scUser != null) {
					topic.setInitiateCode(scUser.getUserCode());
				} else {
					return "login";
				}
			}
			
			this.topicService.addTopic(topic);
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			msg = "0-��ӻ�����Ϣʧ�ܣ�";
		}
		
		this.ajaxFormFileUploadWriteWithJsonObject(this.getResponse(), msg);
		
		return null;
	}
	
	
	/*******************************************
	 * ���뻰����Ϣ�޸�ҳ��
	 * @return
	 */
	public String updatePageScTopicInput() {
		
		if(Assert.hasText(topic.getId())) {
			topic = this.topicService.findTopicById(topic.getId());
			this.setRequestAttribute("topic", topic);
		} else {
			this.setRequestAttribute("error", "û���ҵ����µĶ��������������쳣������ϵ����Ա���߹ر���������ԣ�");
		}
		return "update_input";
	}
	
	
	/*******************************************
	 * ���º�̨������Ϣ
	 * @return
	 */
	public String updatePageScTopic() {
		
		String msg = "1-���»�����Ϣ�ɹ���";
		//���·���ͼƬ��ʱ���ȼ���ԭ���ķ���ͼƬ��·�����·����ϴ��ɹ��Ժ��ٰ�ԭ���ľɷ���ɾ��
		String delFilePath = "";
		
		Topic updateTopic = this.topicService.findTopicById(topic.getId());
		
		if(updateTopic == null) {
			
			msg = "0-û���ҵ����µĻ�����Ϣ��";
			
		} else {
			
			try {
				if(topic.getTempFile() != null) {
					
					delFilePath = updateTopic.getThumbnailFilePath();
					
					String path = "/" + DateUtil.getDate(DateUtil.FMT_YYMMDD) + "/thumbnail/";
					String fileName = this.uploadFileHTTP(topic.getTempFile(), path, topic.getTempFileFileName());
					topic.setThumbnailFilePath(fileName);
				} else {
					
					topic.setThumbnailFilePath(updateTopic.getThumbnailFilePath());
				}
				
				this.topicService.updateTopic(topic);
				
				if(Assert.hasText(delFilePath)) {
					this.httpDelFile(delFilePath);
				}
				
			} catch (RuntimeException e) {
				e.printStackTrace();
				msg = "0-���»�����Ϣʧ�ܣ�";
			}
		}
		
		this.ajaxFormFileUploadWriteWithJsonObject(this.getResponse(), msg);
		
		return null;
	}
	
	
	/*******************************************
	 * ɾ������Ϣ
	 * @return
	 */
	public String delTopic() {
		
		String msg = "1-ɾ������Ϣ�ɹ���";
		try {
			this.topicService.delTopic(topic.getId());
		} catch (Exception e) {
			e.printStackTrace();
			msg = "0-ɾ������Ϣʧ��";
		}
		
		this.getJSONObjectByMessage("msg", msg);
		
		return null;
	}

	
	/*******************************************
	 * ���뻰����ͼ�ϴ�ҳ��
	 * @return
	 */
	public String uploadImageInput() {
		
		String httpImageSaveUrl = Parameters.map.get("httpImageSaveUrl");
		
		if(Assert.hasText(httpImageSaveUrl)) {
			String[] temps = httpImageSaveUrl.split("[/]");
			this.setRequestAttribute("imgHttpServerIP", temps[2].split("[:]")[0]);
			this.setRequestAttribute("imgHttpServerPort", temps[2].split("[:]")[1]);
		}
		
		this.setRequestAttribute("topic", this.topicService.findTopicById(topic.getId()));
		
		return "upload_input";
	}
	
	
	/*******************************************
	 * �����ύ������ͼ��Ϣ
	 * @return
	 */
	public String uploadImageSubmit() {
		
		String msg = "0-������ͼ��Ϣ�ϴ�ʧ�ܣ�ˢ��ҳ�����ԣ�";
		if(Assert.hasText(this.topic.getId())) {
			
			topic = this.topicService.findTopicById(topic.getId());
			if(topic != null) {
				
				String fileNames = this.getRequest().getParameter("fileNames");
				
				if(Assert.hasText(fileNames)) {
					
					String[] temps = fileNames.split("[,]");
					List<Image> list = new ArrayList<Image>();
					for(int i=0; i<temps.length; i++) {
						Image image = new Image();
						image.setFilePath(temps[i]);
						image.setTopic(topic);
						image.setAgree(new Random().nextInt(20));
						image.setOppose(new Random().nextInt(4));
						image.setCrateDate(DateUtil.getDate());
						list.add(image);
					}
					
					this.imageService.addImageList(list);
					msg = "1-������ͼ��Ϣ�ϴ��ɹ�";
				}
				
			}
		}
		
		this.getJSONObjectByMessage("msg", msg);
		
		return null;
	}
	
	
	/*******************************************
	 * ��ݻ���Id��ȡ��Ӧ����ͼ��Ϣ�б�
	 * @return
	 */
	public String getImageList() {
		
		if(Assert.hasText(topic.getId())) {
			this.setRequestAttribute("imageList", this.imageService.findImageListByTopicId(this.topic.getId()));
			this.setRequestAttribute("topic", this.topicService.findTopicById(topic.getId()));
		}
		
		return "image_detail";
	}
	
	
	
	

	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public TopicService getTopicService() {
		return topicService;
	}
	public void setTopicService(TopicService topicService) {
		this.topicService = topicService;
	}
	public ImageService getImageService() {
		return imageService;
	}

	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
}
