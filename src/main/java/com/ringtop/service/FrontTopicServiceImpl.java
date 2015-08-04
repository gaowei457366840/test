/**
 * bai
 */
package com.ringtop.service;


import java.util.List;

import com.ringtop.common.dao.inter.IBaseDao;
import com.ringtop.common.util.Assert;
import com.ringtop.common.util.ResultPage;
import com.ringtop.entity.Image;
import com.ringtop.entity.Topic;
import com.ringtop.entity.TopicComment;

/**
 * 话题service实现
 * @author bm.he
 * @date 2013-12-6 上午09:53:46
 */
public class FrontTopicServiceImpl implements FrontTopicService{
	
	private IBaseDao<Topic> topicBaseDao;
	
	private IBaseDao<TopicComment> topicCommentBaseDao;
	
	private IBaseDao<Image> imageBaseDao;
	



	/**
	 * 分页获取话题
	 */
	public ResultPage getTopic() {
		 StringBuffer hql = new StringBuffer(" from Topic t where t.status = ? order by t.auditorDate DESC");	//状态为1 已通过
		 
		return topicBaseDao.pagedQuery(hql.toString(), new Object[]{1});
	}



	/**
	 *  根据id获取话题
	 */
	public Topic getTopicById(String id) {
		if(Assert.hasText(id)){
			return this.topicBaseDao.getByPk(id);
		}
		return null;
	}
	
	/** 
	 *根据id获取图片 
	 * 
	 * */
	public List<Image> getImgsById(String id) {
		if(Assert.hasText(id)){
			String hql = "from Image i where i.topic.id = ?";
			return imageBaseDao.findByHql(hql, new Object[]{id});
		}
		return null;
	}




	/**
	 *  根据id获取评论
	 */
	public ResultPage getCommentsById(String id) {
		String hql = "from TopicComment tc where tc.topic.id = ? order by tc.createDate ";//根据评论时间查询评论
		if(Assert.hasText(id)){
			return topicCommentBaseDao.pagedQuery(hql, new Object[]{id});
		}
		return null;
		
	}
	
	
	public void setTopicBaseDao(IBaseDao<Topic> topicBaseDao) {
		this.topicBaseDao = topicBaseDao;
	}



	/**
	 * @param topicCommentBaseDao the topicCommentBaseDao to set
	 */
	public void setTopicCommentBaseDao(IBaseDao<TopicComment> topicCommentBaseDao) {
		this.topicCommentBaseDao = topicCommentBaseDao;
	}



	/**
	 * @param topicImgBaseDao the topicImgBaseDao to set
	 */
	public void setImageBaseDao(IBaseDao<Image> imageBaseDao) {
		this.imageBaseDao = imageBaseDao;
	}
}
