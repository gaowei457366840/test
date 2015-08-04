package com.ringtop.action.frontaction;

import java.util.ArrayList;
import java.util.List;

import com.ringtop.common.action.BaseAction;
import com.ringtop.common.util.DateUtil;
import com.ringtop.common.util.ResultPage;
import com.ringtop.communication.AndroidResponse;
import com.ringtop.entity.Paragraph;
import com.ringtop.entity.ParagraphComment;
import com.ringtop.entity.TopicComment;
import com.ringtop.service.FrontParagraphService;
import com.ringtop.service.picture.ParagraphCommentService;
import com.ringtop.service.picture.ParagraphService;

/**
 * 前端段子管理action
 * @author Administrator
 *
 */
public class FrontParagraphAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private FrontParagraphService frontParagraphService;
	
	private ParagraphCommentService paragraphCommentService;
	
	private ParagraphService paragraphService;
	
	private String paragraphId;//段子id
	
	private String context;//评论内容
	
	/*******************************************
	 * 获取段子
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String loadParagraph() {
		ResultPage resultPage = null;
		
		AndroidResponse res = new AndroidResponse();
		try {
			resultPage = frontParagraphService.getParagraph();
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus("fail");
		}
		res.setStatus("ok");
		List<Paragraph> list= (List<Paragraph>)resultPage.getResult();
		
		res.setRows(list);
	
		this.writeWithJsonObject(this.getResponse(), res);
		
		return null;
	}
	
	/*******************************************
	 * 根据id获取段子
	 * @return
	 */
	public String findParagraphById(){
		AndroidResponse res = new AndroidResponse();
		
		Paragraph p = null;
		try {
			p = frontParagraphService.getParagraphById(paragraphId);
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus("fail");
		}
	    List<Object> result = new ArrayList<Object>();
	    result.add(p);
		res.setRows(result);
		res.setStatus("ok");
		
		this.writeWithJsonObject(this.getResponse(), res);
		
		
		return null;
	}
	
	/*******************************************
	 * 根据id获取段子评论
	 * @return
	 */
	public String getParagraphCommentsById(){
		AndroidResponse res = new AndroidResponse();
		
		ResultPage resultPage = null;
		try {
			resultPage = frontParagraphService.getParagraphCommentsById(paragraphId);
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus("fail");
		}
		List<ParagraphComment> list= (List<ParagraphComment>)resultPage.getResult();
		
		//删除所属的话题对象，减少传出的字符
		for(ParagraphComment p : list){
			p.setParagraph(null);
		}
		res.setRows(list);
		res.setStatus("ok");
		
		this.writeWithJsonObject(this.getResponse(), res);
		
		
		return null;
	}

	/**
	 * 根据id评论段子
	 * @return
	 * @author bm.he
	 * @date 2013-12-16 下午03:20:36
	 */
	public String commentParagraph(){
		AndroidResponse res = new AndroidResponse();
		Paragraph paragraph = null;
		ParagraphComment pc = new ParagraphComment();
		
		try {
			paragraph = paragraphService.findParagraphById(paragraphId);
			if(paragraph != null){
				pc.setParagraph(paragraph);
				pc.setContext(context);
				pc.setStatus(1);
				pc.setCreateDate(DateUtil.getDate(DateUtil.FMT_YY_MM_DD_HH_MM_SS));
				pc.setUserCode("test");
				
				paragraphCommentService.addParagraphComment(pc);
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
	public void setParagraphId(String paragraphId) {
		this.paragraphId = paragraphId;
	}

	/**
	 * @param fronParagraphService the fronParagraphService to set
	 */
	public void setFrontParagraphService(FrontParagraphService frontParagraphService) {
		this.frontParagraphService = frontParagraphService;
	}

	/**
	 * @param paragraphCommentService the paragraphCommentService to set
	 */
	public void setParagraphCommentService(
			ParagraphCommentService paragraphCommentService) {
		this.paragraphCommentService = paragraphCommentService;
	}

	/**
	 * @param paragraphService the paragraphService to set
	 */
	public void setParagraphService(ParagraphService paragraphService) {
		this.paragraphService = paragraphService;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}
	
}
