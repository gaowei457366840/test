package com.gaowei.power.account_email;

import javax.mail.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

public class AccountEmailServiceTest {

	private GreenMail greenMail;
	
	@ Before 
	public void startMailServer() throws Exception{
		greenMail = new GreenMail(ServerSetup.SMTP);
		greenMail.setUser("457366840@qq.com", "1234asdfghjkl");
		greenMail.start();
	}
	
	@ Test
	public void testSendMail() throws Exception{
		ApplicationContext ctx =new ClassPathXmlApplicationContext("application-context.xml");
		AccountEmailService accountEmailService = (AccountEmailService)ctx.getBean("accountEmailService");
		
		String subject ="test subject";
		
		String  htmlText="<h3>test</h3>";
		
		accountEmailService.sendMail("457366840@qq.com",subject, htmlText);
		greenMail.waitForIncomingEmail(2000,1);
		Message [] msgs = greenMail.getReceivedMessages(); 
		
	}
	
	@ After
	public void stopMailServer(){
		greenMail.stop();
		
	}
}
