package com.ringtop.service.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ringtop.common.dao.impl.AbstractDAO;
import com.ringtop.common.dao.inter.IBaseDao;
import com.ringtop.common.util.Assert;
import com.ringtop.common.util.ResultPage;
import com.ringtop.common.util.SqlUtil;
import com.ringtop.entity.ScUser;

/**
 * �û���Ϣ����service
 * @author Administrator
 *
 */
public class UserServiceImpl implements UserService {
	
	private IBaseDao<ScUser> userBaseDao;
	
	private AbstractDAO dao = new AbstractDAO();

	/*****************************************
	 * ��ҳ��ѯ�û���Ϣ�б�
	 * @param userName
	 * @param userCode
	 * @param status
	 * @return
	 */
	 public ResultPage findResultPage(String userName, String userCode, int status) {
		
		 StringBuffer hql = new StringBuffer(" select u from ScUser u ");
		 List<Object> list = new ArrayList<Object>();
		 boolean hasWhere = false;
		
		 if(Assert.hasText(userName)) {
			 hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			 hql.append(" u.userName like '%" + userName + "%'");
		 }
		 
		 if(Assert.hasText(userCode)) {
			 hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			 hql.append(" u.userCode=?");
			 list.add(userCode);
		 }
		 
		 if(status == 0 || status == 1) {
			 hasWhere = SqlUtil.appendWhereIfNeed(hql, hasWhere);
			 hql.append(" u.status=?");
			 list.add(status);
		 }
		 
		return this.userBaseDao.pagedQuery(hql.toString(), list.toArray());
	}
	 
	 
	 /*****************************************
	 * ����û���Ϣ
	 * @param scUser
	 */
	public void addUser(ScUser scUser) {
		
		if(scUser != null) {
			this.userBaseDao.save(scUser);
		}
	}
	
	
	/*****************************************
	 * �޸��û���Ϣ
	 * @param scUser
	 */
	public void updateUser(ScUser scUser) {
		
		if(scUser != null) {
			this.userBaseDao.update(scUser);
		}
	}
	
	
	/*****************************************
	 * ����û������ʶɾ���û���Ϣ
	 * @param userId
	 */
	public void delUserById(String userId) {
		
		if(Assert.hasText(userId)) {
			this.userBaseDao.delete(this.userBaseDao.getByPk(userId));
		}
	}
	
	
	/*****************************************
	 * ��������ʶ�����û���Ϣ
	 * @param userId
	 * @return
	 */
	public ScUser findScUserById(String userId) {
		
		return this.userBaseDao.getByPk(userId);
	}
	
	
	/*****************************************
	 * ����û��ʺŲ����û���Ϣ
	 * @param userCode
	 * @return
	 */
	public List<ScUser> findScUserByCode(String userCode) {
		
		String hql = " select u from ScUser u where u.userCode=?";
		
		return this.userBaseDao.findByHql(hql, new Object[] {userCode});
	}
	
	
	@SuppressWarnings("unchecked")
	public ScUser findScUserLoginByCode(String userCode) {
		
		String sql = " select u.userCode,u.password,u.encryptpara from sc_user u where u.userCode=? and u.status=?";
		ScUser user = null;
		@SuppressWarnings("unused")
		List<Map<String, String>> list = dao.execQuery(sql, new Object[] {userCode,1});
		if(Assert.notEmpty(list)) {
			
			user = new ScUser();
			
			Map<String, String> map = list.get(0);
			user.setUserCode(map.get("userCode"));
			user.setPassword(map.get("password"));
			user.setEncryptpara(map.get("encryptpara"));
			
		}
		return user;
	}

	
	/*****************************************
	 * ִ��sql���
	 * @param sql
	 * @param values
	 */
	public void excSql(String sql, Object[] values) {
		
		dao.execSql(sql, values);
	}
	
	
	/*****************************************
	 * ����ֻ�Ų�ѯ�û���Ϣ
	 * @param imei
	 * @return
	 */
	public ScUser findScUserByImei(String imei) {
		
		String hql = " select u from ScUser u where u.imei=?";
		List<ScUser> list = this.userBaseDao.findByHql(hql, new Object[] {imei});
		
		if(Assert.notEmpty(list)) {
			return list.get(0);
		} else {
			return null;
		}
	}
	 
	 
	 
	 
	public IBaseDao<ScUser> getUserBaseDao() {
		return userBaseDao;
	}
	public void setUserBaseDao(IBaseDao<ScUser> userBaseDao) {
		this.userBaseDao = userBaseDao;
	}
}
