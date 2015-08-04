package com.ringtop.service.picture;

import java.util.ArrayList;
import java.util.List;

import com.ringtop.common.dao.inter.IBaseDao;
import com.ringtop.common.util.Assert;
import com.ringtop.common.util.ResultPage;
import com.ringtop.common.util.SqlUtil;
import com.ringtop.entity.Paragraph;

/**
 * 段子信息管理
 * @author Administrator
 *
 */
public class ParagraphServiceImpl implements ParagraphService {
	
	private IBaseDao<Paragraph> paragraphBaseDao;
	
	public void addParagraph(Paragraph paragraph) {

		if(paragraph != null) {
			this.paragraphBaseDao.save(paragraph);
		}
	}

	public void updateParagraph(Paragraph paragraph) {
		
		if(paragraph != null) {
			this.paragraphBaseDao.update(paragraph);
		}
	}
	
	public void delParagraph(String id) {
		
		if(Assert.hasText(id)) {
			this.paragraphBaseDao.delete(this.paragraphBaseDao.getByPk(id));
		}
	}
	
	public Paragraph findParagraphById(String id) {
		
		return this.paragraphBaseDao.getByPk(id);
	}
	
	/******************************************************
	 * 分页查询段子信息
	 * @param auditorCode		审核用户
	 * @param initiateEndDate	发起用户
	 * @param initiateBeginDate	发起话题的起始时间
	 * @param initiateEndDate	发起话题的结束时间
	 * @param status			话题状态
	 * @return
	 */
	public ResultPage getResultPage(String auditorCode,String initiateCode,String initiateBeginDate,String initiateEndDate, int status, int topStatus) {
		
		StringBuffer hql= new StringBuffer(" select p from Paragraph p ");
		
		 List<Object> list = new ArrayList<Object>();
		 boolean hasWhere = false;
		
		if(Assert.hasText(auditorCode)) {
			
			hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			hql.append(" p.auditorCode like '%" + auditorCode + "%'");
		}
		
		if(Assert.hasText(initiateCode)) {
			
			hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			hql.append(" p.initiateCode like '%" + initiateCode + "%'");
		}
		
		if(Assert.hasText(initiateBeginDate)) {
			
			hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			hql.append(" p.initiateDate>=?");
			list.add(initiateBeginDate + " 00:00:00");
		}
		
		if(Assert.hasText(initiateEndDate)) {
			
			hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			hql.append(" p.initiateDate<=?");
			list.add(initiateEndDate + "23:59:59");
		}
		
		if(status != -1) {
			
			hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			hql.append(" p.status=?");
			list.add(status);
		}
		
		if(topStatus != -1) {
			
			hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			hql.append(" p.topStatus=?");
			list.add(topStatus);
		}
		
		hql.append(" order by p.initiateDate desc");
		return this.paragraphBaseDao.pagedQuery(hql.toString(), list.toArray());
	}

	
	
	public IBaseDao<Paragraph> getParagraphBaseDao() {
		return paragraphBaseDao;
	}

	public void setParagraphBaseDao(IBaseDao<Paragraph> paragraphBaseDao) {
		this.paragraphBaseDao = paragraphBaseDao;
	}
}
