/**
 * bai
 */
package com.ringtop.service;

import com.ringtop.common.dao.inter.IBaseDao;
import com.ringtop.common.util.Assert;
import com.ringtop.common.util.ResultPage;
import com.ringtop.entity.Paragraph;
import com.ringtop.entity.ParagraphComment;

/**
 * 段子service实现
 * 
 * @author bm.he
 * @date 2013-12-6 上午09:53:46
 */
public class FrontParagraphServiceImpl implements FrontParagraphService{
	
	private IBaseDao<Paragraph> paragraphBaseDao;

	private IBaseDao<ParagraphComment> paragraphCommentBaseDao;
	

	/**
	 * 分页获取话题
	 */
	public ResultPage getParagraph() {
		 StringBuffer hql = new StringBuffer(" from Paragraph p where p.status = ? order by p.auditorDate DESC");	//状态为1 已通过
		 
		return paragraphBaseDao.pagedQuery(hql.toString(), new Object[]{1});
	}

	

	/**
	 *  根据id获取段子
	 */
	public Paragraph getParagraphById(String id) {
		return this.paragraphBaseDao.getByPk(id);
	}


	/** 
	 * 根据id获取段子的评论
	 */
	public ResultPage getParagraphCommentsById(String id) {
		if(Assert.hasText(id)){
			String hql = "from ParagraphComment pc where pc.paragraph.id = ? order by pc.createDate";// DESC";
			return paragraphCommentBaseDao.pagedQuery(hql, new Object[]{id});
		}
		return null;
	}
	
	public void setParagraphBaseDao(IBaseDao<Paragraph> paragraphBaseDao) {
		this.paragraphBaseDao = paragraphBaseDao;
	}



	/**
	 * @param paragraphCommentBaseDao the paragraphCommentBaseDao to set
	 */
	public void setParagraphCommentBaseDao(IBaseDao<ParagraphComment> paragraphCommentBaseDao) {
		this.paragraphCommentBaseDao = paragraphCommentBaseDao;
	}
	
	
	
}
