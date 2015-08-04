package com.ringtop.service.picture;

import java.util.ArrayList;
import java.util.List;

import com.ringtop.common.dao.inter.IBaseDao;
import com.ringtop.common.util.Assert;
import com.ringtop.common.util.ResultPage;
import com.ringtop.common.util.SqlUtil;
import com.ringtop.entity.Topic;

/**
 * 话题信息管理service
 * @author Administrator
 *
 */
public class TopicServiceImpl implements TopicService {

	private IBaseDao<Topic> topicBaseDao;
	
	public void addTopic(Topic topic) {

		if(topic != null) {
			topicBaseDao.save(topic);
		}
	}

	
	public void delTopic(String id) {

		if(Assert.hasText(id)) {
			this.topicBaseDao.delete(this.topicBaseDao.getByPk(id));
		}
	}

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
	public ResultPage getResultPage(String title,String auditorCode,String initiateCode,String initiateBeginDate,String initiateEndDate, int status) {
		
		StringBuffer hql = new StringBuffer(" select t from Topic t ");
		
		 List<Object> list = new ArrayList<Object>();
		 boolean hasWhere = false;
		
		 if(Assert.hasText(title)) {
			 hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			 hql.append(" t.title like '%" + title + "%'");
		 }
		 
		 if(Assert.hasText(auditorCode)) {
			 hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			 hql.append(" t.auditorCode like '%" + auditorCode + "%'");
		 }
		 
		 if(Assert.hasText(initiateCode)) {
			 hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			 hql.append(" t.initiateCode like '%" + initiateCode + "%'");
		 }
		 
		 if(Assert.hasText(initiateBeginDate)) {
			 hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			 hql.append(" t.initiateDate>=?");
			 list.add(initiateBeginDate + " 00:00:00");
		 }
		 
		 if(Assert.hasText(initiateEndDate)) {
			 hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			 hql.append(" t.initiateDate<=?");
			 list.add(initiateEndDate + " 23:59:59");
		 }
		 
		 if(status != -1) {
			 hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			 hql.append(" t.status=?");
			 list.add(status);
		 }
		 
		 hql.append(" order by t.initiateDate desc ");
		 
		return this.topicBaseDao.pagedQuery(hql.toString(), list.toArray());
	}


	public void updateTopic(Topic topic) {

		if(topic != null) {
			this.topicBaseDao.update(topic);
		}
	}

	
	public Topic findTopicById(String id) {
		
		if(Assert.hasText(id)) {
			return this.topicBaseDao.getByPk(id);
		} else {
			return null;
		}
	}

	
	
	
	
	
	
	
	
	
	
	public IBaseDao<Topic> getTopicBaseDao() {
		return topicBaseDao;
	}
	public void setTopicBaseDao(IBaseDao<Topic> topicBaseDao) {
		this.topicBaseDao = topicBaseDao;
	}

}
