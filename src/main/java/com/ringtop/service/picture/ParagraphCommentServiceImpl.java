package com.ringtop.service.picture;

import java.util.ArrayList;
import java.util.List;

import com.ringtop.common.dao.inter.IBaseDao;
import com.ringtop.common.util.Assert;
import com.ringtop.common.util.ResultPage;
import com.ringtop.common.util.SqlUtil;
import com.ringtop.entity.ParagraphComment;

/**
 * 段子评论管理
 * @author Administrator
 *
 */
public class ParagraphCommentServiceImpl implements ParagraphCommentService {

	private IBaseDao<ParagraphComment> paragraphCommentBaseDao;
	
	public void addParagraphComment(ParagraphComment pc) {
		
		if(pc != null) {
			paragraphCommentBaseDao.save(pc);
		}
	}

	public void delParagraphCommentById(String id) {
		
		ParagraphComment comment = this.paragraphCommentBaseDao.getByPk(id);
		if(comment != null) {
			this.paragraphCommentBaseDao.delete(comment);
		}
	}

	public ParagraphComment findParagraphCommentById(String id) {
		
		return paragraphCommentBaseDao.getByPk(id);
	}

	public ResultPage getResultPage(String paragraphContext,String userCode,String createBeginDate, String createEndDate, String context,int status) {
		
		
		StringBuffer hql = new StringBuffer(" select pc from ParagraphComment pc ");
		List<Object> values = new ArrayList<Object>();
		boolean hasWhere = false;
		
		if(Assert.hasText(paragraphContext)) {
			hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			hql.append(" pc.paragraph.context like '%" + paragraphContext + "%'");
		}
		
		if(Assert.hasText(userCode)) {
			hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			hql.append(" pc.userCode like '%" + userCode + "%'");
		}
		
		if(Assert.hasText(context)) {
			hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			hql.append(" pc.context like '%" + context + "%'");
		}
		
		if(Assert.hasText(createBeginDate)) {
			hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			hql.append(" pc.createDate>=?");
			values.add(createBeginDate + " 00:00:00");
		}
		
		if(Assert.hasText(createEndDate)) {
			hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			hql.append(" pc.createDate<=?");
			values.add(createEndDate + " 23:59:59");
		}
		
		if(status != -1) {
			hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			hql.append(" pc.status=?");
			values.add(status);
		}
		
		hql.append(" order by pc.createDate desc, pc.paragraph.initiateDate ");
		
		return this.paragraphCommentBaseDao.pagedQuery(hql.toString(), values.toArray());
	}

	public void updateParagraphComment(ParagraphComment pc) {
		
		if(pc != null) {
			this.paragraphCommentBaseDao.update(pc);
		}
	}

	
	
	
	
	
	
	
	
	
	public IBaseDao<ParagraphComment> getParagraphCommentBaseDao() {
		return paragraphCommentBaseDao;
	}

	public void setParagraphCommentBaseDao(
			IBaseDao<ParagraphComment> paragraphCommentBaseDao) {
		this.paragraphCommentBaseDao = paragraphCommentBaseDao;
	}
}
