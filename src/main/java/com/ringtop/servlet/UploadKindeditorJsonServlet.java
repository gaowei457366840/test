package com.ringtop.servlet;

import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;

import com.ringtop.common.util.Assert;
import com.ringtop.common.util.DateUtil;
import com.ringtop.common.util.FileUtil;
import com.ringtop.common.util.Parameters;

/**
 * һ���ļ��ⲿ�ϴ�
 * kindeditor�༭���ļ��ϴ�
 * @author Administrator
 *
 */
public class UploadKindeditorJsonServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private int maxSize = 0;
	
	public UploadKindeditorJsonServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			PrintWriter out = response.getWriter();
			String path = "/" + DateUtil.getDate(DateUtil.FMT_YYMMDD) + "/topic/";
			//�ļ��������ʱĿ¼
			String savePathTemp = getServletConfig().getServletContext().getRealPath("temp");   

			//���������ϴ����ļ���չ��
			HashMap<String, String> extMap = new HashMap<String, String>();
			extMap.put("image", "gif,jpg,jpeg,png,bmp");
			extMap.put("flash", "swf,flv");
			extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
			extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

			response.setContentType("text/html; charset=UTF-8");

			if(!ServletFileUpload.isMultipartContent(request)){
				out.println(getError("��ѡ���ļ���"));
			}

			String dirName = request.getParameter("dir");
			if (dirName == null) {
				dirName = "kindeditorImages";
			}
			if(!extMap.containsKey(dirName)){
				out.println(getError("Ŀ¼����ȷ��"));
			}
			
			//�����ļ���
			File saveDirFile = new File(savePathTemp);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}

			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			List items = upload.parseRequest(request);
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				String fileName = item.getName();
				@SuppressWarnings("unused")
				long fileSize = item.getSize();
				if (!item.isFormField()) {
					//����ļ���С
					if(item.getSize() > maxSize){
						out.println(getError("�ϴ����ļ����ֻ��Ϊ2M"));
					}
					//�����չ��
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
						out.println(getError("�ϴ��ļ���չ���ǲ��������չ��\nֻ����" + extMap.get(dirName) + "��ʽ��"));
					}

					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
					
					File uploadedFile = new File(savePathTemp, newFileName);
					item.write(uploadedFile);
					
					String url = "";
					String httpImageSaveUrl = Parameters.map.get("httpImageSaveUrl");
					
					if(Assert.hasText(httpImageSaveUrl)) {
						String[] temps = httpImageSaveUrl.split("[/]");
						if(temps != null && temps.length > 2) {
							url = temps[0] + "//" + temps[2];
							url += "/DossierCamion/Junction?action=discoveryUploadFileHTTP&newFilePath=" + path + "&oldFielName=" + uploadedFile.getName();
						}
					}
					
					newFileName = FileUtil.http(uploadedFile, url, null);
					uploadedFile.delete();
					JSONObject obj = new JSONObject();
					obj.put("error", 0);
					obj.put("url", httpImageSaveUrl + newFileName);
					out.println(obj.toJSONString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}
		
	@SuppressWarnings("unchecked")
	public void init() throws ServletException {
	
		String maxSizeInit = getInitParameter("MAX_SIZE");
		
		if(Assert.hasText(maxSizeInit)) {
			try {
				this.maxSize = Integer.parseInt(maxSizeInit);
			} catch (Exception e) {
				this.maxSize = 2*1024*1024;
			}
		}
	}

}
