package com.gaowei.power.account_email;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class AccountEmailServiceImp implements AccountEmailService {
	
	private JavaMailSender javaMailSender;
	private String systemEmail;

	public void sendMail(String to, String subject, String htmlText) {
		// TODO Auto-generated method stub
         try {
        	 MimeMessage msg =javaMailSender.createMimeMessage();
        	 MimeMessageHelper msgHelper =new MimeMessageHelper(msg);
        	 
        	 msgHelper.setFrom(systemEmail);
        	 msgHelper.setTo(to);
        	 msgHelper.setSubject(subject);
        	 msgHelper.setText(htmlText,true);
        	 
        	 javaMailSender.send(msg);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public String getSystemEmail() {
		return systemEmail;
	}

	public void setSystemEmail(String systemEmail) {
		this.systemEmail = systemEmail;
	}

}
