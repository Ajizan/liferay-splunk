package com.ajizan.liferay.splunk.audit;

import com.ajizan.liferay.splunk.audit.configuration.SplunkAuditMessageProcessorConfiguration;
import com.ajizan.liferay.splunk.service.SplunkService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.audit.AuditException;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.audit.AuditMessageProcessor;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author oharrari
 */
@Component(immediate = true, property = "eventTypes=*", configurationPid = "com.ajizan.liferay.splunk.audit.configuration.SplunkAuditMessageProcessorConfiguration", service = AuditMessageProcessor.class)
public class SplunkAuditMessageProcessor implements AuditMessageProcessor {

	private static final Log _log = LogFactoryUtil.getLog(SplunkAuditMessageProcessor.class);

	@Override
	public void process(AuditMessage auditMessage) throws AuditException {

		if (_splunkAuditMessageProcessorConfiguration.enabled()) {

			if (_log.isDebugEnabled()) {
				_log.debug("processing audit message with Splunk:" + auditMessage);
			}

			try {
				_splunkService.process(auditMessage);
			} catch (Exception e) {
				_log.error("an error occurred when processing audit message with Splunk", e);
				throw new AuditException(e);
			}
		} else {
			if (_log.isDebugEnabled()) {
				_log.debug("ignoring audit message as Splunk Audit is disabled:" + auditMessage);
			}
		}

	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_splunkAuditMessageProcessorConfiguration = ConfigurableUtil
				.createConfigurable(SplunkAuditMessageProcessorConfiguration.class, properties);
	}

	private volatile SplunkAuditMessageProcessorConfiguration _splunkAuditMessageProcessorConfiguration;

	@Reference
	private SplunkService _splunkService;

}