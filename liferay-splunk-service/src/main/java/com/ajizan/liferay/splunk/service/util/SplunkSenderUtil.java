package com.ajizan.liferay.splunk.service.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;


public class SplunkSenderUtil {
private static final Log _log = LogFactoryUtil.getLog(SplunkSenderUtil.class);
    
 	   
	
	private static HttpURLConnection getConnection(String uri , String token ) throws IOException  {

		URL url = new URL(uri);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setRequestProperty("Authorization", "Splunk "+token);
		httpURLConnection.setRequestProperty("Content-Type", "application/json; UTF-8");
		return httpURLConnection;

	}
	
	private static JSONObject getEventJson(String event) {
		JSONObject eventJson = JSONFactoryUtil.createJSONObject();
		eventJson.put("event", event);
		return eventJson ;
        
	}
	
	public static void logEvent(String  event , String uri , String token ) throws IOException {
	
		HttpURLConnection connection = getConnection(uri , token );
		
		connection.setDoOutput(true);
		DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
		outputStream.write(getEventJson(event).toString().getBytes("UTF8"));
		outputStream.flush();
		outputStream.close();
		

		if (_log.isDebugEnabled()) {
			int responseCode = connection.getResponseCode();
			if (responseCode == 200) {
				
				_log.info("event logged to Splunk , status = " + responseCode);
			} else {
				
				_log.info("Unable to log event  to Splunk , status = " + responseCode );
			}
		}
		
		
		
		
	}

}
