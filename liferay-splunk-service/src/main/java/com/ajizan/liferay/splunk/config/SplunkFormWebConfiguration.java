package com.ajizan.liferay.splunk.config;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import aQute.bnd.annotation.metatype.Meta;

@ExtendedObjectClassDefinition(
		category = "platform",
        scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(id = "com.ajizan.liferay.splunk.config.SplunkFormWebConfiguration", localization = "content/Language", name = "splunk-form-web-configuration-name")

public interface SplunkFormWebConfiguration {

	@Meta.AD(deflt = " ", description = "splunk host 'protocole://host:port/endpoint'", name = "splunkUrl", required = true)
	public String uri();

	@Meta.AD(deflt = " ", description = "splunk token", name = "splunToken", required = true)
	public String token();

}
