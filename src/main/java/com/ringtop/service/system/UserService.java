package com.ringtop.service.system;

import java.util.List;

import com.ringtop.common.util.ResultPage;
import com.ringtop.entity.ScUser;

/**
 * �û���Ϣ����service
 * @author Administrator
 *
 */
public interface UserService {

	/*****************************************
	 * ��ҳ��ѯ�û���Ϣ�б�
	 * @param userName
	 * @param userCode
	 * @param status
	 * @return
	 */
	public ResultPage findResultPage(String userName, String userCode, int status);
	
	
	/*****************************************
	 * ����û���Ϣ
	 * @param scUser
	 */
	public void addUser(ScUser scUser);
	
	
	/*****************************************
	 * �޸��û���Ϣ
	 * @param scUser
	 */
	public void updateUser(ScUser scUser);
	
	
	/*****************************************
	 * ����û������ʶɾ���û���Ϣ
	 * @param userId
	 */
	public void delUserById(String userId);
	
	
	/*****************************************
	 * ��������ʶ�����û���Ϣ
	 * @param userId
	 * @return
	 */
	public ScUser findScUserById(String userId);
	
	
	/*****************************************
	 * ����û��ʺŲ����û���Ϣ
	 * @param userCode
	 * @return
	 */
	public List<ScUser> findScUserByCode(String userCode);
	
	
	/*****************************************
	 * �÷���û��ʹ��ע�����ԣ����Ե��ø÷�����ʱ�����ʹ��ע��service�ķ�������Ҳ����ֱ��new
	 * ����û��ʺŲ����û���Ϣ
	 * @param userCode
	 * @return
	 */
	public ScUser findScUserLoginByCode(String userCode);
	
	
	/*****************************************
	 * ִ��sql���
	 * @param sql
	 * @param values
	 */
	public void excSql(String sql, Object[] values);
	
	
	/*****************************************
	 * ����ֻ�Ų�ѯ�û���Ϣ
	 * @param imei
	 * @return
	 */
	public ScUser findScUserByImei(String imei);
}
