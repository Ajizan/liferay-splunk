package com.ajizan.liferay.splunk.service.configuration;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import aQute.bnd.annotation.metatype.Meta;

@ExtendedObjectClassDefinition(category = "splunk")
@Meta.OCD(id = "com.ajizan.liferay.splunk.service.configuration.SplunkServiceConfiguration", localization = "content/Language", name = "splunk-service-configuration-name")
public interface SplunkServiceConfiguration {

	@Meta.AD(deflt = "http://localhost:8088/services/collector/raw", description = "splunk-url-desc", name = "splunk-url", required = false)
	public String url();

	@Meta.AD(deflt = "xxxx", description = "splunk-token", name = "splunk-token", required = false)
	public String token();

}
