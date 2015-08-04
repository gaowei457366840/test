package com.ringtop.common.action;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.interceptor.RequestAware;
import org.json.simple.JSONObject;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ognl.OgnlValueStack;
import com.ringtop.common.util.Assert;
import com.ringtop.common.util.FileUtil;
import com.ringtop.common.util.Page;
import com.ringtop.common.util.PageUtil;
import com.ringtop.common.util.Parameters;
import com.ringtop.common.util.ResultPage;
import com.ringtop.common.util.UnicodeUtil;

/**
 * ����action��Ϣ��
 * @author Administrator
 *
 */
public class BaseAction extends ActionSupport implements RequestAware {
	
	@SuppressWarnings("unchecked")
	private Map request;
	
	@SuppressWarnings("unused")
	private HttpServletRequest httpRequest;
	
	private static final long serialVersionUID = 1L;
	
	protected ActionContext actionContext = null;
	
	private Logger logger = Logger.getLogger(BaseAction.class);

	@SuppressWarnings("unchecked")
	public void setRequest(Map request) {
		this.request = request;
		setHttpRequest();
	}

	public void setHttpRequest() {
		
		OgnlValueStack stack = (OgnlValueStack) request.get("struts.valueStack");
		// ���HttpServletResponse����
		httpRequest = (HttpServletRequest) stack.getContext().get(StrutsStatics.HTTP_REQUEST);

	}

	
	protected ActionContext getActionContext() {
		
		return (actionContext == null) ? actionContext = ActionContext.getContext() : actionContext;
	}

	protected HttpServletRequest getRequest() {

		return ServletActionContext.getRequest();
	}

	protected HttpServletRequest getHTTPRequest() {
		
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
		return request;
	}

	protected HttpServletResponse getResponse() {
		
		return ServletActionContext.getResponse();
	}

	protected void setRequestAttribute(String key, Object value) {
		
		getRequest().setAttribute(key, value);
	}

	protected ServletContext getServletContext() {
		
		return ServletActionContext.getServletContext();
	}

	protected HttpSession getSession() {
		
		HttpServletRequest request = getRequest();
		HttpSession session = null;
		if (request != null) {
			session = request.getSession(true);
		}
		return session;
	}

	
	/************************************
	 * ��õ�ǰStrutsSession
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public Map getStrutsSession() {
		
		return getActionContext().getContext().getSession();
	}

	/************************************
	 * �õ�վ���Ŀ¼
	 * 
	 * @return
	 */
	protected String getWebRootDir() {
		
		return getServletContext().getRealPath("/");
	}

	/************************************
	 * �õ�վ���Ŀ¼
	 * @return
	 */
	protected String getWebRootDir(String name) {
		
		return getServletContext().getRealPath(name);
	}
	
	
	/************************************
	 * ���� jquery easyui ��ҳ��Ϣ
	 * @param resultPage
	 */
	@SuppressWarnings("unchecked")
	public void writeJqueryEasyuiResultPage(ResultPage resultPage) {
		
		int start = ResultPage.getStartOfPage(PageUtil.getPageNo(), PageUtil.getPageSize());
		int total = (int) resultPage.getTotalCount();
		Page dataPage = new Page((List<Object>)resultPage.getResult(), start, total, PageUtil.getPageSize());
		
		this.writeWithJsonObject(this.getResponse(), dataPage);
	}
	
	/************************************
	 * ����Ajax��Ϣ
	 * @param response
	 * @param msg
	 * @return
	 */
	public String returnAjaxMsg(String msg) {
		
		getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = getResponse().getWriter();
			out.write(msg);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/************************************
	 * ��������
	 * ���ַ���json����ʽд��
	 * @throws IOException 
	 */
	public void writeWithJsonMsg(HttpServletResponse response, String message) {
		
		try {
			response.setContentType("text/json charset=utf-8;");
			//���ÿͻ��˵�������Ӧ��ѭ�������   no-cache����ʾ���滺��
			response.setHeader("Cache-Control", "no-cache"); 
			PrintWriter writer = response.getWriter();
			writer.write(message);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/************************************
	 * json.simple.JSONObject
	 * ��ȡʹ��json����ʽ����ҳ������
	 * @param msgFlag	ҳ���ȡ��json��ʶ
	 * @param value		json��valueֵ
	 */
	@SuppressWarnings("unchecked")
	public void getJSONObjectByMessage(String msgFlag, String value) {
		
		logger.info(msgFlag + " : " + value);
		
		JSONObject object = new JSONObject();
		object.put(msgFlag, value);
		returnAjaxMsg(object.toString());
	}
	
	
	/************************************
	 * ����������
	 * �Ѷ�����json����ʽд��
	 * @param response
	 * @param outObject
	 * @throws IOException
	 */
	public void writeWithJsonObject(HttpServletResponse response, Object outObject) {
		
		try {
			response.setContentType("text/json; charset=utf-8");
			response.setHeader("Cache-Control", "no-cache"); 
			//Gson gson = new Gson();
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			String result = gson.toJson(outObject);
			PrintWriter writer = response.getWriter();
			writer.write(result);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	/************************************
	 * ����������
	 * �Ѷ�����json����ʽд��
	 * @param response
	 * @param outObject
	 * @throws IOException
	 */
	public void ajaxFormFileUploadWriteWithJsonObject(HttpServletResponse response, Object outObject) {
		
		try {
			response.setContentType("text/html; charset=utf-8");
			response.setHeader("Cache-Control", "no-cache"); 
			Gson gson = new Gson();
			String result = gson.toJson(outObject);
			PrintWriter writer = response.getWriter();
			writer.write(result);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	
	/************************************
	 * URL ת������
	 */
	public String getChinese(String url) {
		
		try {
			url = UnicodeUtil.decodeUnicode(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}

	
	/************************************
	 * �ϴ��ļ�
	 * @param file
	 * @param path
	 * @throws IOException
	 * @return �²��Ե��ļ����
	 */
	public String uploadFile(File file, String path, String oldFielName) {

		InputStream is = null;
		OutputStream os = null;
		try {
			File f = new File(path);
			if (!f.exists()) {
				System.out.println("�ļ��в�����");
				if (f.mkdirs()) {
					System.out.println("�����ļ��гɹ�");
				} else {
					System.out.println("�����ļ���ʧ��");
				}
			}

			if (file != null && file.length() > 0) {

				byte b[] = new byte[(int) file.length()];
				is = new FileInputStream(file);
				String fileName = "";
				if(Assert.hasText(oldFielName)) {
					String[] values = oldFielName.split("[.]");
					fileName = UUID.randomUUID().toString() + "." + values[1];
				}
				
				os = new FileOutputStream(path + "\\" + fileName);

				int count = 0;
				while ((count = is.read(b)) > 0) {
					os.write(b, 0, count);
				}

				os.close();
				is.close();

				return fileName;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	/************************************
	 * 
	 * httpЭ���ϴ��ļ����ļ������������Ϣ�����ȥ
	 * @param file
	 * @param path
	 * @param oldFielName
	 * @return
	 */
	public String uploadFileHTTP(File file, String path, String oldFielName) {
		
		String fileName = "";
		try {

			if (file != null && file.length() > 0) {
				
				String httpImageSaveUrl = Parameters.map.get("httpImageSaveUrl");
				if(Assert.hasText(httpImageSaveUrl)) {
					String[] temps = httpImageSaveUrl.split("[/]");
					if(temps != null && temps.length > 2) {
						String url = temps[0] + "//" + temps[2];
						url += "/DossierCamion/Junction?action=discoveryUploadFileHTTP&newFilePath=" + path + "&oldFielName=" + oldFielName;
						fileName = FileUtil.http(file ,url, null);
					}
				}
			} 

		} catch (Exception e) {
			e.printStackTrace();
		}

		return fileName;
	}
	
	
	/************************************
	 * 
	 * ���ͼƬ·��ɾ��ͼƬ
	 * httpЭ�飬��ϴ������ʹ��
	 * @return
	 */
	public String httpDelFile(String filePath) {
		
		String responeStr = "";
		String httpImageSaveUrl = Parameters.map.get("httpImageSaveUrl");
		if(Assert.hasText(httpImageSaveUrl)) {
			String[] temps = httpImageSaveUrl.split("[/]");
			if(temps != null && temps.length > 2) {
				String url = temps[0] + "//" + temps[2];
				url += "/DossierCamion/Junction?action=delFile&delFilePath=" + filePath;
				responeStr = FileUtil.http(url, null);
			}
		}
		return responeStr;
	}
}
