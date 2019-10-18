package com.ping.wechat.util;

import org.springframework.context.ApplicationContext;

/**
 * 获取Bean工具类
 * @author ZhangJun
 * @createTime 2017-12-25 19:11
 * @description 获取Bean工具类
 *
 */
public class SpringBeanUtil {

	public static ApplicationContext applicationContext = null;

	public static Boolean isInit(){
		if(applicationContext==null){
			return false;
		}
		return true;
	}


	public static void setApplicationContext(ApplicationContext applicationContext){
		SpringBeanUtil.applicationContext = applicationContext;
	}

	public static Object getBean(String beanName){
		if(applicationContext==null){
			return null;
		}
		return applicationContext.getBean(beanName);
	}

}
