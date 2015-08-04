package com.ringtop.action.frontaction;

import java.io.File;

import com.ringtop.common.action.BaseAction;
import com.ringtop.common.util.Assert;
import com.ringtop.common.util.DateUtil;
import com.ringtop.communication.AndroidResponse;
import com.ringtop.entity.ScUser;
import com.ringtop.service.system.UserService;

/**
 * ǰ̨�û���Ϣ����
 * @author Administrator
 *
 */
public class FrontUserAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private ScUser scUser = new ScUser();
	
	private UserService userService;

	/*******************************************
	 * ����û���Ϣ
	 * @return
	 */
	public String addUser() {
		
		AndroidResponse res = new AndroidResponse("ok","�û��Ѿ�ע��", null);
		
		try {
			
			String imei = this.getRequest().getParameter("imei");
			
			if(Assert.hasText(imei)) {
				
				scUser = this.userService.findScUserByImei(imei);
				
				if(scUser == null) {
					
					scUser = new ScUser();
					
					scUser.setImei(imei);
					scUser.setUserCode(imei);
					scUser.setCreateDate(DateUtil.getDate(DateUtil.FMT_YY_MM_DD_HH_MM_SS));
					scUser.setStatus(1);
					scUser.setSource(1);
					scUser.setLastLoginDate(DateUtil.getDate(DateUtil.FMT_YY_MM_DD_HH_MM_SS));
					
					String path = "/" + DateUtil.getDate(DateUtil.FMT_YYMMDD) + "/portrait/";
					File file = new File(this.getWebRootDir("images") + "/default_portrait.jpg");
					String fileName = this.uploadFileHTTP(file, path, file.getName());
					
					scUser.setPortrait(fileName);
					
					this.userService.updateUser(scUser);
					
					res.setStatus("ok");
					res.setMsg("�û�ע��ɹ�");
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			res.setStatus("fail");
			res.setMsg("��������쳣��");
		}
		
		
		this.writeWithJsonObject(this.getResponse(), res);
		
		return null;
	}
	
	
	/*******************************************
	 * �޸��û�ͷ��
	 * @return
	 */
	public String updateUserPortrait() {
		
		return null;
	}
	
	
	/*******************************************
	 * �޸��û�������Ϣ
	 * @return
	 */
	public String updateUserOther() {
		
		return null;
	}
	
	
	
	
	
	
	
	public ScUser getScUser() {
		return scUser;
	}
	public void setScUser(ScUser scUser) {
		this.scUser = scUser;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
