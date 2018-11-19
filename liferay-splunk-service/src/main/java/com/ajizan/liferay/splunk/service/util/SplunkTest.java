package com.ajizan.liferay.splunk.service.util;


import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.splunk.*;
public class SplunkTest {
	 public static final Log _log = LogFactoryUtil.getLog(SplunkTest.class);
	 
     public static void test() {
    	 
    	 
    	
    	 ServiceArgs loginArgs = new ServiceArgs();
    	 loginArgs.setHost("labcorner.zango.ma");
    	 loginArgs.setScheme("http");
    	 loginArgs.setUsername("oharrari");
    	 loginArgs.setPassword("Zango123");
         loginArgs.setPort(8089);
         Service service = new Service(loginArgs);
    	 service.login();
      
         IndexCollection myIndexes = service.getIndexes();

         
     }
}
