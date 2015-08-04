package com.ringtop.service.picture;

import com.ringtop.common.util.ResultPage;
import com.ringtop.entity.TopicComment;

/**
 * 话题评论管理service
 * @author Administrator
 *
 */
public interface TopicCommentService {

	/**
	 * 分页查询话题评论信息
	 * @param topicTitle		话题标题
	 * @param userCode			评论用户
	 * @param createBeginDate	创建时间
	 * @param createEndDate		创建时间
	 * @param context			内容
	 * @param status			状态
	 * @return
	 */
	public ResultPage getResultPage(String topicTitle, String userCode, String createBeginDate, String createEndDate,String context, int status);
	
	public void delTopicCommentById(String id);
	
	public void updateTopicComment(TopicComment topicComment);

	public TopicComment findTopicCommentById(String id);

	public void addTopicComment(TopicComment tc);
}
