package com.ringtop.service.picture;

import com.ringtop.common.util.ResultPage;
import com.ringtop.entity.ParagraphComment;

public interface ParagraphCommentService {

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
	public ResultPage getResultPage(String paragraphTitle, String userCode, String createBeginDate, String createEndDate,String context, int status);
	
	public void delParagraphCommentById(String id);
	
	public void updateParagraphComment(ParagraphComment pc);

	public ParagraphComment findParagraphCommentById(String id);

	public void addParagraphComment(ParagraphComment pc);
}
