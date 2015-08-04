package com.ringtop.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring 工具类
 * @author Administrator
 *
 */
public class SpringBeanUtil {

	private static ApplicationContext context;
	
	public static Object getBeanByName(String beanName) {
		
		if(context == null) {
			context = new ClassPathXmlApplicationContext("classpath:com/ringtop/config/spring/*.xml");
		}
		
		return context.getBean(beanName);
	}
}
