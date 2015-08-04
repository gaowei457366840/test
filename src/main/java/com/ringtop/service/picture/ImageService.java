package com.ringtop.service.picture;

import java.util.List;

import com.ringtop.entity.Image;

public interface ImageService {

	public void addImageList(List<Image> list);

	public List<Image> findImageListByTopicId(String id);

	public void delImageByImageId(String id);
	
	public Image findImageById(String id);
}
