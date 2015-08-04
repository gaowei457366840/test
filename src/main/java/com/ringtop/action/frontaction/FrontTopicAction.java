package com.ringtop.action.frontaction;

import java.util.ArrayList;
import java.util.List;

import com.ringtop.common.action.BaseAction;
import com.ringtop.common.util.DateUtil;
import com.ringtop.common.util.Parameters;
import com.ringtop.common.util.ResultPage;
import com.ringtop.communication.AndroidResponse;
import com.ringtop.entity.Image;
import com.ringtop.entity.Topic;
import com.ringtop.entity.TopicComment;
import com.ringtop.service.FrontTopicService;
import com.ringtop.service.picture.TopicCommentService;
import com.ringtop.service.picture.TopicService;

/**
 * 前端话题管理action
 * @author Administrator
 *
 */
public class FrontTopicAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private FrontTopicService frontTopicService;
	
	private TopicCommentService topicCommentService;
	
	private TopicService topicService;
	
	private String topicId;//话题id
	
	private String context;//评论内容
	

	
	/*******************************************
	 * 获取话题
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String loadTopic() {
		ResultPage resultPage = null;
		
		AndroidResponse res = new AndroidResponse();
		try {
			resultPage = frontTopicService.getTopic();
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus("fail");
		}
		res.setStatus("ok");
		List<Topic> list= (List<Topic>)resultPage.getResult();
		//为图片的url添加端口
		for(Topic t : list){
			t.setThumbnailFilePath(Parameters.map.get("httpImageSaveUrl") +  t.getThumbnailFilePath());
		}
		
		res.setRows(list);
	
		this.writeWithJsonObject(this.getResponse(), res);
		
		return null;
	}
	
	/*******************************************
	 * 根据id获取话题
	 * @return
	 */
	public String findTopicById(){
		AndroidResponse res = new AndroidResponse();
		
		Topic topic = null;
		try {
			topic = frontTopicService.getTopicById(topicId);
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus("fail");
		}
	    List<Object> result = new ArrayList<Object>();
	    result.add(topic);
		res.setRows(result);
		res.setStatus("ok");
		
		this.writeWithJsonObject(this.getResponse(), res);
		
		
		return null;
	}
	
	/**
	 * 根据id查询话题图片
	 * @return
	 * @author bm.he
	 * @date 2013-12-13 上午09:43:36
	 */
	public String getTopicImgById(){
		AndroidResponse res = new AndroidResponse();
		List<Image> imgs = null;
		try{
			imgs = frontTopicService.getImgsById(topicId);
		}catch (Exception e) {
			e.printStackTrace();
			res.setStatus("fail");
		}
		
		//去掉Topic对象，减少字符传输,添加url前缀
		for(Image i : imgs){
			i.setTopic(null);
			i.setFilePath(Parameters.map.get("httpImageSaveUrl") + i.getFilePath());
		}
		res.setStatus("ok");
		res.setRows(imgs);
		this.writeWithJsonObject(this.getResponse(), res);
		
		return null;
	}
	
	/**
	 * 根据话题Id分页查询评论
	 * @return
	 * @author bm.he
	 * @date 2013-12-10 上午11:03:05
	 */
	@SuppressWarnings("unchecked")
	public String getTopicCommentsById(){
		AndroidResponse res = new AndroidResponse();
		
		ResultPage resultPage = null;
		try {
			resultPage = frontTopicService.getCommentsById(topicId);
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus("fail");
			
		}
		res.setStatus("ok");
		List<TopicComment> list= (List<TopicComment>)resultPage.getResult();
		//删除所属的话题对象，减少传出的字符
		for(TopicComment t : list){
			t.setTopic(null);
		}
		res.setRows(list);
		
		this.writeWithJsonObject(this.getResponse(), res);
		return null;
	}
	
	/**
	 * 根据id评论话题
	 * @return
	 * @author bm.he
	 * @date 2013-12-16 下午02:40:48
	 */
	public String commentTopic(){
		AndroidResponse res = new AndroidResponse();
		Topic topic = null;
		TopicComment tc = new TopicComment();
		
		try {
			topic = topicService.findTopicById(topicId);
			if(topic != null){
				tc.setTopic(topic);
				tc.setContext(context);
				tc.setStatus(1);
				tc.setCreateDate(DateUtil.getDate(DateUtil.FMT_YY_MM_DD_HH_MM_SS));
				tc.setUserCode("test");
				topicCommentService.addTopicComment(tc);
				res.setStatus("ok");
				res.setMsg("评论成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus("fail");
			res.setMsg("出问题了");
		}
		
		
		
		
		this.writeWithJsonObject(this.getResponse(), res);
		
		
		return null;
	}


	
	
	
	
	/**
	 * @param topicId the topicId to set
	 */
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	/**
	 * @param frontTopicService the frontTopicService to set
	 */
	public void setFrontTopicService(FrontTopicService frontTopicService) {
		this.frontTopicService = frontTopicService;
	}

	/**
	 * @param topicCommentService the topicCommentService to set
	 */
	public void setTopicCommentService(TopicCommentService topicCommentService) {
		this.topicCommentService = topicCommentService;
	}

	/**
	 * @param topicService the topicService to set
	 */
	public void setTopicService(TopicService topicService) {
		this.topicService = topicService;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}
	
	
	
}
