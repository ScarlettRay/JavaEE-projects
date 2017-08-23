package com.inxedu.os.common.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author www.inxedu.com
 *
 */
public class MySessionListener implements HttpSessionListener {
	//全站在线人数
	public static int num = 0;
	//创建session的时候+1
	public void sessionCreated(HttpSessionEvent event) {
		num++;
		System.out.println("全站在线人数:"+num);
	}
	//销毁session的时候-1
	public void sessionDestroyed(HttpSessionEvent event) {
		if(num>0){
			num--;
		}
		System.out.println("全站在线人数:"+num);
	}
}
