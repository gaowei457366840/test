package com.ringtop.action;

import java.io.File;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.ringtop.common.action.BaseAction;
import com.ringtop.common.util.Assert;
import com.ringtop.common.util.DateUtil;
import com.ringtop.common.util.MD5Util;
import com.ringtop.common.util.Page;
import com.ringtop.common.util.PageUtil;
import com.ringtop.common.util.ResultPage;
import com.ringtop.entity.ScUser;
import com.ringtop.service.system.UserService;

/**
 * �û���Ϣ����action
 * @author Administrator
 *
 */
public class UserAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private ScUser scUser = new ScUser();
	
	private UserService userService;
	
	/*******************************************
	 * ��ȡ�û���Ϣ�б�
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String loadUserList() {
		
		String command = this.getRequest().getParameter("command");
		if(!"query".equals(command)) {
			scUser.setStatus(2);
		}
		ResultPage resultPage = this.userService.findResultPage(scUser.getUserName(), scUser.getUserCode(), scUser.getStatus());
		List<ScUser> userList = (List<ScUser>)resultPage.getResult();
		
		int start = ResultPage.getStartOfPage(PageUtil.getPageNo(), PageUtil.getPageSize());
		int total = (int) resultPage.getTotalCount();
		Page dataPage = new Page(userList, start, total, PageUtil.getPageSize());
		
		this.writeWithJsonObject(this.getResponse(), dataPage);
		
		return null;
	}
	
	
	/*******************************************
	 * ��Ӻ�̨�û���Ϣ
	 * @return
	 */
	public String addPageScUser() {
		
		String msg = "1-����û���Ϣ�ɹ���";
		
		try {
			
			scUser.setCreateDate(DateUtil.getDate(DateUtil.FMT_YY_MM_DD_HH_MM_SS));
			String random = RandomStringUtils.randomNumeric(4);
			scUser.setEncryptpara(random);
			scUser.setPassword(MD5Util.getEncryptedPwd(random + "666666"));
			scUser.setSource(2);
			
			String path = "/" + DateUtil.getDate() + "/portrait/";
			String fileName = "";
			
			if(scUser.getTempFile() == null) {
				File file = new File(this.getWebRootDir("images") + "/default_portrait.jpg");
				scUser.setTempFile(file);
				scUser.setTempFileFileName(file.getName());
			}
			
			fileName = this.uploadFileHTTP(scUser.getTempFile(), path, scUser.getTempFileFileName());
			
			if(Assert.hasText(fileName)) {
				scUser.setPortrait(fileName);
			}
			
			this.userService.addUser(scUser);
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			msg = "0-����û���Ϣʧ�ܣ�";
		}
		
		this.ajaxFormFileUploadWriteWithJsonObject(this.getResponse(), msg);
		
		return null;
	}
	
	
	/*******************************************
	 * ���ǰ���û���Ϣ
	 * @return
	 */
	public String addFrontScUser() {
		
		return null;
	}
	
	
	/*******************************************
	 * ���º�̨�û���Ϣ
	 * @return
	 */
	public String updatePageScUser() {
		
		String msg = "1-�����û���Ϣ�ɹ���";
		
		ScUser updateUser = this.userService.findScUserById(scUser.getId());
		
		if(updateUser == null) {
			
			msg = "0-û���ҵ����µ��û���Ϣ��";
			
		} else {
			
			try {
				updateUser.setPhoneCode(scUser.getPhoneCode());
				updateUser.setQqNum(scUser.getQqNum());
				updateUser.setSinaNum(scUser.getSinaNum());
				updateUser.setStatus(scUser.getStatus());
				updateUser.setUserName(scUser.getUserName());
				updateUser.setRemark(scUser.getRemark());
				
				if(scUser.getTempFile() != null) {
					String path = "/" + DateUtil.getDate(DateUtil.FMT_YYMMDD) + "/portrait/";
					String fileName = this.uploadFileHTTP(this.scUser.getTempFile(), path, this.scUser.getTempFileFileName());
					updateUser.setPortrait(fileName);
				}
				
				this.userService.updateUser(updateUser);
				
			} catch (RuntimeException e) {
				e.printStackTrace();
				msg = "0-�����û���Ϣʧ�ܣ�";
			}
		}
		
		this.ajaxFormFileUploadWriteWithJsonObject(this.getResponse(), msg);
		
		return null;
	}
	
	
	/*******************************************
	 * ɾ���û���Ϣ
	 * @return
	 */
	public String delUser() {
		
		String msg = "1-ɾ���û���Ϣ�ɹ���";
		try {
			this.userService.delUserById(scUser.getId());
		} catch (Exception e) {
			e.printStackTrace();
			msg = "0-ɾ���û���Ϣʧ��";
		}
		
		this.getJSONObjectByMessage("msg", msg);
		
		return null;
	}
	
	
	/*******************************************
	 * ��֤�û��ʺ�
	 * @return
	 */
	public String ajaxuserCode() {
		
		String msg = "1-�ñ�ſ�����ʹ��";
		//����û��˺�
		String userCode = this.getRequest().getParameter("userCode");
		//��ü�¼
		List<ScUser> scUser = userService.findScUserByCode(userCode);
		if( scUser .size()> 0){
			msg = "0-���û��˺���ʹ�ù�";
		}
		
		this.getJSONObjectByMessage("msg", msg);
		
		return null;
	}
	
	
	/*******************************************
	 * �û������ʼ��
	 * @return
	 */
	public String initUserPassword() {
		
		String msg = "1-�û������ʼ���ɹ���";
		
		scUser = this.userService.findScUserById(scUser.getId());
		if(scUser != null) {
			String random = RandomStringUtils.randomNumeric(4);
			scUser.setEncryptpara(random);
			scUser.setPassword(MD5Util.getEncryptedPwd(random + "666666"));
			this.userService.updateUser(scUser);
		} else {
			msg = "0-û���ҵ�Ҫ��ʼ�����û���Ϣ��ϵͳ�����쳣��";
		}
		
		this.getJSONObjectByMessage("msg", msg);
		
		return null;
	}
	
	
	/*******************************************
	 * ��֤�û�����
	 * @return
	 */
	public String validatePass() {
		
		String oldPass = this.getRequest().getParameter("oldPass");
		String msg = "1-ԭ������֤ͨ��";
		if(Assert.hasTextNull(oldPass)) {
			msg = "0-������ԭ����";
			this.getJSONObjectByMessage("msg", msg);
			return null;
		}
		
		scUser = (ScUser)this.getSession().getAttribute("disUser");
		if(scUser == null) {
			return "login";
		}
		
		if(!MD5Util.comparePassword(scUser.getEncryptpara(), oldPass, scUser.getPassword())) {
			msg = "0-�����ԭ���벻��ȷ������������";
		}
		
		this.getJSONObjectByMessage("msg", msg);
		
		return null;
	}
	
	
	/*******************************************
	 * �޸ĵ�ǰ��¼�û�����
	 * @return
	 */
	public String updateUserPass() {
		
		String newPass = this.getRequest().getParameter("newPass");
		scUser = (ScUser)this.getSession().getAttribute("disUser");
		String msg = "1-�û������޸ĳɹ���";
		
		try {
			if(scUser != null) {
				scUser = this.userService.findScUserByCode(scUser.getUserCode()).get(0);
				String random = RandomStringUtils.randomNumeric(4);
				scUser.setEncryptpara(random);
				scUser.setPassword(MD5Util.getEncryptedPwd(random + newPass));
				this.userService.updateUser(scUser);
				this.getSession().removeAttribute("disUser");
				this.getSession().setAttribute("disUser", scUser);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			msg = "0-�û������޸�ʧ�ܣ�";
		}
		
		this.getJSONObjectByMessage("msg", msg);
		
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
