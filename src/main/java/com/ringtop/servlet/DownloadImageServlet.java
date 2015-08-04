package com.ringtop.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ringtop.common.util.Assert;
import com.ringtop.common.util.DateUtil;
/**
 * 影像下载servlet
 * @author Administrator
 *
 */
public class DownloadImageServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private String contentType = "application/x-msdownload";
    @SuppressWarnings("unused")
	private String enc = "utf-8";

    /**
     * 初始化contentType，enc，fileRoot
     */
    public void init(ServletConfig config) throws ServletException {
        String tempStr = config.getInitParameter("contentType");
        if (tempStr != null && !tempStr.equals("")) {
            contentType = tempStr;
        }
        tempStr = config.getInitParameter("enc");
        if (tempStr != null && !tempStr.equals("")) {
            enc = tempStr;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	ServletOutputStream out = response.getOutputStream();
    	
    	String bizPath = request.getParameter("bizPath");
    	String fileHttpPath = request.getParameter("fileHttpPath");
    	
    	URL url = new URL(fileHttpPath + "&bizPath=" +bizPath);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		@SuppressWarnings("unused")
		long size = con.getContentLength();
		
		String string = "-";
		String fileName = "attachment;filename=" + DateUtil.shortFmt(new Date(), DateUtil.FMT_YYMMDD) + string +  DateUtil.shortFmt(new Date(), "HHmmss");
		if(Assert.hasText(bizPath)) {
			String[] temps = bizPath.split("[.]");
			if(temps.length == 2) {
				fileName += "." + temps[1];
			}
		} else {
			fileName += ".jpg";
		}
		response.setHeader("Content-Disposition", fileName);
		BufferedImage image = ImageIO.read(url);
		ImageIO.write(image, "jpg", out);
		
		out.flush();
		out.close();
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
		
	}
    
    
}
