package com.ringtop.service.picture;

import java.util.List;

import com.ringtop.common.dao.inter.IBaseDao;
import com.ringtop.common.util.Assert;
import com.ringtop.entity.Image;

public class ImageServiceImpl implements ImageService {

	private IBaseDao<Image> imageBaseDao;
	
	public void addImageList(List<Image> list) {

		if(Assert.notEmpty(list)) {
			imageBaseDao.saveOrUpdateALL(list);
		}
	}
	
	
	public Image findImageById(String id) {
		
		return this.imageBaseDao.getByPk(id);
	}
	
	
	public void delImageByImageId(String id) {
		
		Image image = this.imageBaseDao.getByPk(id);
		if(image != null) {
			this.imageBaseDao.delete(image);
		}
	}
	
	public List<Image> findImageListByTopicId(String id) {
		
		String hql = " select im from Image im where im.topic.id=?";
		
		return this.imageBaseDao.findByHql(hql, new Object[] {id});
	}

	
	public IBaseDao<Image> getImageBaseDao() {
		return imageBaseDao;
	}

	public void setImageBaseDao(IBaseDao<Image> imageBaseDao) {
		this.imageBaseDao = imageBaseDao;
	}

}
