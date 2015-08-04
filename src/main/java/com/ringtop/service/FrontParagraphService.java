package com.ringtop.service;

import com.ringtop.common.util.ResultPage;
import com.ringtop.entity.Paragraph;

/**
 * 前端段子service
 * @author Administrator
 *
 */
public interface FrontParagraphService {


	/**
	 * 根据id获取段子
	 * @param id
	 * @return
	 * @author bm.he
	 * @date 2013-12-6 下午03:45:32
	 */
	public Paragraph getParagraphById(String id);
	
	/**
	 * 根据id获取段子评论
	 * @param id
	 * @return
	 * @author bm.he
	 * @date 2013-12-11 下午03:08:52
	 */
	public ResultPage getParagraphCommentsById(String id);
	
	/**
	 * 分页获取最新话题
	 */
	public ResultPage getParagraph();
}
