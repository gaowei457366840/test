package com.ringtop.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FileUtil {
	
	
	/***************************************************
	 * �÷����ǰ� K ת��Ϊ K,M,G
	 * ת���ļ���С[byteת����K,M,G]
	 * @param fileS
	 * @return
	 */
	public static String FormetFileSize(double fileS) {
	    DecimalFormat df = new DecimalFormat("#.00");
	    String fileSizeString = "";
	    if (fileS < 1024) {
	        fileSizeString = df.format(fileS) + "KB";
	    } else if (fileS < 1048576) {
	        fileSizeString = df.format(fileS / 1024) + "M";
	    } else {
	        fileSizeString = df.format(fileS / 1048576) + "G";
	    } 
	    return fileSizeString;
	}
	
	/*****************************************************************
	 * ��ʽ���ļ���С
	 * Ϊ����ϵͳ�з��㣬�ڴ˰��ļ��Ĵ�С��ʽ��Ϊdouble���͵Ĵ�С����λΪKB
	 * @param fileLength	�ļ���С
	 * @return
	 */
	public static double FormetFileSizeDoubleK(long fileLength) {
		
		DecimalFormat format = new DecimalFormat("#.00");
		return Double.parseDouble(format.format((double)fileLength/1024));
	}
	
	/***************************************************
	 * byteת����ָ�������ʹ�С
	 * @param fileS
	 * @param sizeType
	 * @return
	 */
	public static String FormetFileSizeChoice(long fileS, String sizeType) {
		DecimalFormat df = new DecimalFormat("0.00");
		
		String fileSizeString = "";
		if(sizeType == null || "".equals(sizeType)){
			return FormetFileSize(fileS);
			
		}else if(sizeType.equalsIgnoreCase("B")){
			 fileSizeString = df.format((double) fileS) + "B";
			 
		}else if(sizeType.equalsIgnoreCase("K")){
			 fileSizeString = df.format((double) fileS / 1024) + "K";
			 
		}else if(sizeType.equalsIgnoreCase("M")){
			 fileSizeString = df.format((double) fileS / 1048576) + "M";
			 
		}else if(sizeType.equalsIgnoreCase("G")){
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		
		 return fileSizeString;
	}
	
	/***************************************************
	 * ȡ���ļ���С
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static String getFileSize(File f) throws Exception {
        long s = 0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s = fis.available();
        } else {
           // f.createNewFile();
            System.out.println("�ļ�������");
        }
        
        return FormetFileSizeChoice(s, "K");
    }
	
	
	/***************************************************
	 * ȡ���ļ��д�С
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static Long getFoderSize(File f) throws Exception {
        
		long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFoderSize(flist[i]);
            }else {
                size = size + flist[i].length();
            }
        }
        
        return size;
    }
	
	/***************************************************
	 * ȡĿ¼�ļ�����
	 * @param f
	 * @return
	 */
	public static Long getFolorNum(File f){
		
        long size = 0;
        File flist[] = f.listFiles();
        size = flist.length;
        
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size =  + getFileNum(flist[i]);
                size--;
            }
        }
        return size;

    }
	
	/***************************************************
	 * �����������ļ���·��
	 * @param f
	 * @return
	 * 		
	 */
	public static List<String> checkChildFolder(File f){
		List<String> childPath = new ArrayList<String>();
		File flist[] = f.listFiles();
		
		for (int i = 0; i < flist.length; i++) {
			if(flist[i].isDirectory()){
				childPath.add(flist[i].getAbsolutePath());
			}
		}
		
		return childPath;
	}
	
	/***************************************************
	 * ��ȡ�ļ���Ŀ
	 * @param file
	 * @return
	 */
	public static int getFileNum(File file) {
		
		int fileNum = 0;
		File[] files = file.listFiles();
		for(int i=0; i<files.length; i++) {
			if(files[i].isDirectory()) {
				fileNum += getFileNum(files[i]);
			} else {
				fileNum += 1;
			}
		}
	
		return fileNum;
	}
	
	
	
	/****************************************
	 * http ͼƬ�ϴ�
	 * ����http����Ȼ����շ�����Ϣ
	 * @param url
	 * @param params
	 * @return
	 */
	public static String http(File file, String url, Map<String, String> params) {

		URL u = null;
		HttpURLConnection con = null;
		// �����������
		StringBuffer sb = new StringBuffer();
		if (params != null) {
			for(Entry<String, String> e : params.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			sb.substring(0, sb.length() - 1);
		}

		System.out.println("send_url:" + url);
		System.out.println("send_data:" + sb.toString());
		
		// ���Է�������
		try {

			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestProperty(  
                    "Accept",  
                    "image/gif,image/x-xbitmap,image/jpeg,image/pjpeg,application/vnd.ms-excel,application/vnd.ms-powerpoint,application/msword,application/x-shockwave-flash,application/x-quickviewplus,*/*"
                );  
			con.setRequestProperty("Accept-Language", "zh-cn");  
			con.setRequestProperty("Content-type", "multipart/form-data;boundary=---------------------------7d318fd100112");  
			con.setRequestProperty("Accept-Encoding","gzip,deflate");  
			con.setRequestProperty("User-Agent","Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1)");  
			con.setRequestProperty("Connection", "Keep-Alive");  
			con.setDoOutput(true);  
			con.setUseCaches(true);  
			
			//��ȡ�ļ���  
            int size = (int) file.length();  
            byte[] data = new byte[size];  
            FileInputStream fis = new FileInputStream(file);  
            OutputStream out = con.getOutputStream();  
            fis.read(data, 0, size);  
            //д���ļ���  
            out.write(file.getName().trim().getBytes());  
            //д��ָ���  
            out.write('|');  
            //д��ͼƬ��  
            out.write(data);  
            out.flush();  
            out.close();  
            fis.close();  
            
		    //��ȡ��Ӧ���  
            int code = con.getResponseCode();  
            String sCurrentLine = "";  
            //�����Ӧ���  
            String sTotalString = "";  
            if (code == 200) {
            	
                InputStream is = con.getInputStream();  
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));  
                
                while((sCurrentLine = reader.readLine()) != null) {
                    if(sCurrentLine.length() > 0) { 
                        sTotalString = sCurrentLine.trim();
                    }
                }
                
            } else {  
                sTotalString = "Զ�̷���������ʧ��,�������:" + code;  
            }  
            
            return sTotalString;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}

		return null;
	}


	/****************************************
	 * http ��Ӧ
	 * ����http����Ȼ����շ�����Ϣ
	 * @param url
	 * @param params
	 * @return
	 */
	public static String http(String url, Map<String, String> params) {

		URL u = null;
		HttpURLConnection con = null;
		// �����������
		StringBuffer sb = new StringBuffer();
		if (params != null) {
			for(Entry<String, String> e : params.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			sb.substring(0, sb.length() - 1);
		}

		System.out.println("send_url:" + url);
		System.out.println("send_data:" + sb.toString());
		
		// ���Է�������
		try {

			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			osw.write(sb.toString());
			osw.flush();
			osw.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}

		// ��ȡ��������
		StringBuffer buffer = new StringBuffer();

		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buffer.toString();
	}
}