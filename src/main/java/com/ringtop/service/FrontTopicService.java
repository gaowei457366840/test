package com.ringtop.service;

import java.util.List;

import com.ringtop.common.util.ResultPage;
import com.ringtop.entity.Image;
import com.ringtop.entity.Topic;

/**
 * 话题service
 * @author Administrator
 *
 */
public interface FrontTopicService {


	/**
	 * 根据id获取话题
	 * @param id
	 * @return
	 * @author bm.he
	 * @date 2013-12-6 下午03:45:32
	 */
	public Topic getTopicById(String id);
	
	/**
	 * 分页获取最新话题
	 */
	public ResultPage getTopic();
	
	/**
	 * 根据id获取评论
	 */
	public ResultPage getCommentsById(String id);
	
	/**
	 * 根据id获取图片
	 * @param id
	 * @return
	 * @author bm.he
	 * @date 2013-12-13 上午09:48:34
	 */
	public List<Image> getImgsById(String id);
	
	
}
