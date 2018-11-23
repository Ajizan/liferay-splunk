package com.ajizan.liferay.splunk.audit.portlet;

import com.ajizan.liferay.splunk.audit.constants.LiferaySplunkAuditPortletKeys;
import com.liferay.portal.kernel.audit.AuditException;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.security.audit.AuditMessageProcessor;




import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author oharrari
 */
@Component( immediate = true, property = "eventTypes=*", service = AuditMessageProcessor.class)

public class LiferaySplunkAuditPortlet implements AuditMessageProcessor  {
	

	private static final Log _log = LogFactoryUtil.getLog(LiferaySplunkAuditPortlet.class);

	@Override
	public void process(AuditMessage auditMessage) throws AuditException {
		try {
			doProcess(auditMessage);
		} catch (Exception e) {
			_log.debug("unable to do Process " + e);
		}

	}

	private void doProcess(AuditMessage auditMessage) {
		
			if (_log.isDebugEnabled()) {
				_log.debug(auditMessage.toJSONObject());
				this.sendEvent(auditMessage);
			}
		
			
	}

	private void sendEvent(AuditMessage auditMessage) {
		long  ts= auditMessage.getTimestamp().getTime()/1000;  
		_log.info("time"+ts);
		Message message = new Message();
		message.setDestinationName(LiferaySplunkAuditPortletKeys.DESTINATION_NAME );
		message.setResponseDestinationName(LiferaySplunkAuditPortletKeys.BUS_RESPONSE );
		message.put("event", auditMessage.toJSONObject());
		message.put("time", ts);
		message.put("messageAudit", true);
		_messageBus.sendMessage(message.getDestinationName(), message);
	}

	@Reference
	private MessageBus _messageBus;

	
	
}