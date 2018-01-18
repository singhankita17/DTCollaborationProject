package com.linkin.config;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent event) {
		
			System.out.println("==== Session is created ====");
			event.getSession().setMaxInactiveInterval(15*60);
		
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		
		HttpSession session = event.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		System.out.println(" user Id = "+userId);
		session.removeAttribute("userId");
	    session.invalidate();
			System.out.println("==== Session is destroyed ====");
		
	}
	

}