package com.ringtop.entity;

import com.ringtop.common.entity.POJO;

/**
 * ͼƬ��Ϣ
 * @author Administrator
 *
 */
public class Image implements POJO {

	private static final long serialVersionUID = 1L;

	private String id;
	
	//����
	private String title;
	//���ݽ���
	private String context;
	//ͼƬ��ַ
	private String filePath;
	//����ͼ��ַ
	private String thumbnailFilePath;
	//���ʶ��1��2��3....
	private int group;
	//��ͬ��-��
	private int agree;
	//������-��
	private int oppose;
	//����ʱ��
	private String crateDate;
	//�������⣨���⣩
	private Topic topic;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getThumbnailFilePath() {
		return thumbnailFilePath;
	}
	public void setThumbnailFilePath(String thumbnailFilePath) {
		this.thumbnailFilePath = thumbnailFilePath;
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	public int getAgree() {
		return agree;
	}
	public void setAgree(int agree) {
		this.agree = agree;
	}
	public int getOppose() {
		return oppose;
	}
	public void setOppose(int oppose) {
		this.oppose = oppose;
	}
	public String getCrateDate() {
		return crateDate;
	}
	public void setCrateDate(String crateDate) {
		this.crateDate = crateDate;
	}
}
