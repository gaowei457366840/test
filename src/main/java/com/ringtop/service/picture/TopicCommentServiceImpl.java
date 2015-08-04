package com.ringtop.service.picture;

import java.util.ArrayList;
import java.util.List;
import com.ringtop.common.dao.inter.IBaseDao;
import com.ringtop.common.util.Assert;
import com.ringtop.common.util.ResultPage;
import com.ringtop.common.util.SqlUtil;
import com.ringtop.entity.TopicComment;

public class TopicCommentServiceImpl implements TopicCommentService {

	private IBaseDao<TopicComment> topicCommentBaseDao;
	
	/**
	 * 分页查询话题评论信息
	 * @param topicTitle		话题标题
	 * @param userCode			评论用户
	 * @param createBeginDate	创建时间
	 * @param createEndDate		创建时间
	 * @return
	 */
	public ResultPage getResultPage(String topicTitle, String userCode, String createBeginDate, String createEndDate,
			String context, int status) {
		
		StringBuffer hql = new StringBuffer(" select tc from TopicComment tc ");
		List<Object> values = new ArrayList<Object>();
		boolean hasWhere = false;
		
		if(Assert.hasText(topicTitle)) {
			hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			hql.append(" tc.topic.title like '%" + topicTitle + "%'");
		}
		
		if(Assert.hasText(userCode)) {
			hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			hql.append(" tc.userCode like '%" + userCode + "%'");
		}
		
		if(Assert.hasText(context)) {
			hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			hql.append(" tc.context like '%" + context + "%'");
		}
		
		if(Assert.hasText(createBeginDate)) {
			hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			hql.append(" tc.createDate>=?");
			values.add(createBeginDate + " 00:00:00");
		}
		
		if(Assert.hasText(createEndDate)) {
			hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			hql.append(" tc.createDate<=?");
			values.add(createEndDate + " 23:59:59");
		}
		
		if(status != -1) {
			hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			hql.append(" tc.status=?");
			values.add(status);
		}
		
		hql.append(" order by tc.createDate desc, tc.topic.initiateDate ");
		
		return this.topicCommentBaseDao.pagedQuery(hql.toString(), values.toArray());
	}
	
	public void delTopicCommentById(String id) {
		
		TopicComment comment = this.topicCommentBaseDao.getByPk(id);
		if(comment != null) {
			this.topicCommentBaseDao.delete(comment);
		}
	}
	
	public void updateTopicComment(TopicComment topicComment) {
		
		if(topicComment != null) {
			this.topicCommentBaseDao.update(topicComment);
		}
	}

	
	public TopicComment findTopicCommentById(String id) {
		
		return this.topicCommentBaseDao.getByPk(id);
	}
	
	
	public void addTopicComment(TopicComment tc) {
		
		if(tc != null) {
			this.topicCommentBaseDao.save(tc);
		}
	}
	
	
	
	
	
	
	public IBaseDao<TopicComment> getTopicCommentBaseDao() {
		return topicCommentBaseDao;
	}

	public void setTopicCommentBaseDao(IBaseDao<TopicComment> topicCommentBaseDao) {
		this.topicCommentBaseDao = topicCommentBaseDao;
	}
}
