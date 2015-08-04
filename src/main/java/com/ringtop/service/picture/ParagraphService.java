package com.ringtop.service.picture;

import com.ringtop.common.util.ResultPage;
import com.ringtop.entity.Paragraph;

/**
 * ������Ϣ����
 * @author Administrator
 *
 */
public interface ParagraphService {

public void addParagraph(Paragraph paragraph);
	
	public void updateParagraph(Paragraph paragraph);
	
	public void delParagraph(String id);
	
	public Paragraph findParagraphById(String id);
	
	/******************************************************
	 * ��ҳ��ѯ������Ϣ
	 * @param auditorCode		����û�
	 * @param initiateEndDate	�����û�
	 * @param initiateBeginDate	���������ʼʱ��
	 * @param initiateEndDate	������Ľ���ʱ��
	 * @param status			����״̬
	 * @param topStatus			�ö�״̬
	 * @return
	 */
	public ResultPage getResultPage(String auditorCode,String initiateCode,String initiateBeginDate,String initiateEndDate, int status,int topStatus);
}
