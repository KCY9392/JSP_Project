package com.kh.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    public ContextListener() {

    
        
    }

    public void contextDestroyed(ServletContextEvent sce)  { 

    
    }

    public void contextInitialized(ServletContextEvent sce)  { 

        new AESCryptor();
        
    }
	
}
