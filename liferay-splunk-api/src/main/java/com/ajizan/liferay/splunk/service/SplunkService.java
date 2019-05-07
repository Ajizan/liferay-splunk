package com.ajizan.liferay.splunk.service;

import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.exception.PortalException;

public interface SplunkService {

	public void process(AuditMessage auditMessage) throws PortalException;

}
