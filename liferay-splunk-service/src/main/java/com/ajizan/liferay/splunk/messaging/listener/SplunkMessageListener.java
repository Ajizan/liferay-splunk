package com.ajizan.liferay.splunk.messaging.listener;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.Validator;
import com.ajizan.liferay.splunk.config.SplunkFormWebConfiguration;
import com.ajizan.liferay.splunk.messaging.config.MessagingConfigConstants;
import com.ajizan.liferay.splunk.service.util.SplunkSenderUtil;


@Component(
		immediate = true,
		configurationPid = "com.ajizan.liferay.splunk.config.SplunkFormWebConfiguration",
		property = {
		"destination.name=" + MessagingConfigConstants.DESTINATION_NAME }, service = MessageListener.class)


public class SplunkMessageListener  extends BaseMessageListener {

	
	public static  final Log _log = LogFactoryUtil.getLog(SplunkMessageListener.class);
	@Override
	protected void doReceive(Message message) throws Exception {
		
		if(_log.isDebugEnabled()){
			_log.info("message receved " + message );
			String payload = (String)message.getPayload();
		 _log.info("Message payload: " + payload);
		}
		
		String event = message.getString(SplunkConstants.EVENT);
		if(Validator.isNotNull(event)) {
			 SplunkSenderUtil.logEvent(event , _splunkFormWebConfiguration.uri() ,  _splunkFormWebConfiguration.token() );
			
		}
	}
	
	
	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_splunkFormWebConfiguration = ConfigurableUtil.createConfigurable(
	    		SplunkFormWebConfiguration.class, properties);
	}

	private volatile SplunkFormWebConfiguration _splunkFormWebConfiguration;
	
	
	@Reference
    private volatile MessageBus _messageBus;

}
