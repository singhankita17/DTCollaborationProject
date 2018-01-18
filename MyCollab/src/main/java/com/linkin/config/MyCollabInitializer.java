package com.linkin.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyCollabInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
		  
	    @Override
	    protected Class<?>[] getRootConfigClasses() {
	        return new Class[] { AppContextDBConfig.class };
	    }
	   
	    @Override
	    protected Class<?>[] getServletConfigClasses() {
	    	 return new Class[] { WebConfig.class };
	    }
	   
	    @Override
	    protected String[] getServletMappings() {
	        return new String[] { "/" };
	    }

		@Override
		public void onStartup(ServletContext servletContext) throws ServletException {
			
			super.onStartup(servletContext);
			servletContext.addListener(new SessionListener());
		}
	  
	}

