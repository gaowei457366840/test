package com.ringtop.action;

import java.util.ArrayList;


import java.util.List;

import com.google.gson.Gson;
import com.ringtop.common.action.BaseAction;
import com.ringtop.entity.Image;
import com.ringtop.service.picture.ImageService;

/**
 * ͼƬ��Ϣ����
 * @author Administrator
 *
 */
public class ImageAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private Image image = new Image();
	
	private ImageService imageService;

	/***********************************************
	 * ��װ��ͼ��Ϣ��������ϸ�鿴ҳ��
	 * @return
	 */
	public String getCommonImage() {
		
		String imgFilepath 	= this.getRequest().getParameter("imgFilepath");
		String imageId		= this.getRequest().getParameter("imageId");
		
		//��������Ӱ��
		List<Image> list = new ArrayList<Image>();
		Image image = new Image();
		image.setFilePath(imgFilepath);
		list.add(image);
		
		Gson gson = new Gson();
		String imageListJson = gson.toJson(list);
		
		this.getRequest().setAttribute("imageListJson", 	imageListJson);
		this.getRequest().setAttribute("imageId", 			imageId);
		
		return "iframe_image";
	}
	
	
	/***********************************************
	 * ɾ��ͼƬ��Ϣ
	 * @return
	 */
	public String delImageByImageId() {
		
		String msg = "1-ɾ��ͼƬ��Ϣ�ɹ�";
		try {
			
			image = this.imageService.findImageById(image.getId());
			if(image != null) {
				this.httpDelFile(image.getFilePath());
				this.imageService.delImageByImageId(image.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.getJSONObjectByMessage("msg", msg);
		
		return null;
	}

	
	
	
	
	

	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public ImageService getImageService() {
		return imageService;
	}
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
}
