package com.ajizan.liferay.splunk.audit;


import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import com.ajizan.liferay.splunk.config.SplunkFormWebConfiguration;
import com.ajizan.liferay.splunk.messaging.config.MessagingConfigConstants;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.audit.AuditException;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.security.audit.AuditMessageProcessor;

@Component(configurationPid = "com.ajizan.liferay.splunk.config.SplunkFormWebConfiguration", immediate = true, property = "eventTypes=*", service = AuditMessageProcessor.class)

public class SplunkAuditMessageProcessor implements AuditMessageProcessor {

	private static final Log _log = LogFactoryUtil.getLog(SplunkAuditMessageProcessor.class);

	@Override
	public void process(AuditMessage auditMessage) throws AuditException {
		try {
			doProcess(auditMessage);
		} catch (Exception e) {
			_log.debug("unable to do Process " + e);
		}

	}

	private void doProcess(AuditMessage auditMessage) {
		if (_splunkFormWebConfiguration.enabled())
		{
			
			
			if (_log.isDebugEnabled()) {
				_log.debug(auditMessage.toJSONObject());
				this.sendEvent(auditMessage);
			}
		}
			
	}

	private void sendEvent(AuditMessage auditMessage) {
		long  ts= auditMessage.getTimestamp().getTime()/1000;  
		_log.info("time"+ts);
		Message message = new Message();
		message.setDestinationName(MessagingConfigConstants.DESTINATION_NAME);
		message.setResponseDestinationName(MessagingConfigConstants.BUS_RESPONSE);
		message.put("event", auditMessage.toJSONObject());
		message.put("time", ts);
		_messageBus.sendMessage(message.getDestinationName(), message);
	}

	@Reference
	private MessageBus _messageBus;

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_splunkFormWebConfiguration = ConfigurableUtil.createConfigurable(SplunkFormWebConfiguration.class, properties);
	}

	private volatile SplunkFormWebConfiguration _splunkFormWebConfiguration;

}
