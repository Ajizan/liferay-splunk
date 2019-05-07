package com.ajizan.liferay.splunk.audit.configuration;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import aQute.bnd.annotation.metatype.Meta;

@ExtendedObjectClassDefinition(category = "audit")
@Meta.OCD(
		id =  "com.ajizan.liferay.splunk.audit.configuration.SplunkAuditMessageProcessorConfiguration",
		localization = "content/Language",
		name = "splunk-audit-message-processor-configuration-name")
public interface SplunkAuditMessageProcessorConfiguration {

	@Meta.AD(deflt = "false", name = "enabled", required = false)
	public boolean enabled();

}
