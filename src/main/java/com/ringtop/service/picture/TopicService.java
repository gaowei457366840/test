package com.ringtop.service.picture;

import com.ringtop.common.util.ResultPage;
import com.ringtop.entity.Topic;

/**
 * 话题信息管理service
 * @author Administrator
 *
 */
public interface TopicService {

	public void addTopic(Topic topic);
	
	public void updateTopic(Topic topic);
	
	public void delTopic(String id);
	
	public Topic findTopicById(String id);
	
	/******************************************************
	 * 分页查询话题信息
	 * @param title				标题
	 * @param auditorCode		审核用户
	 * @param initiateEndDate	发起用户
	 * @param initiateBeginDate	发起话题的起始时间
	 * @param initiateEndDate	发起话题的结束时间
	 * @param status			话题状态
	 * @return
	 */
	public ResultPage getResultPage(String title,String auditorCode,String initiateCode,String initiateBeginDate,String initiateEndDate, int status);
}
