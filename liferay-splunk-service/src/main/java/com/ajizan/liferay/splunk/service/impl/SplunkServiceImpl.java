/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.ajizan.liferay.splunk.service.impl;

import com.ajizan.liferay.splunk.exception.SplunkException;
import com.ajizan.liferay.splunk.service.SplunkService;
import com.ajizan.liferay.splunk.service.configuration.SplunkServiceConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

@Component(service = SplunkService.class, configurationPid = "com.ajizan.liferay.splunk.service.configuration.SplunkServiceConfiguration")
public class SplunkServiceImpl implements SplunkService {

	@Override
	public void process(AuditMessage auditMessage) throws PortalException {

		String url = _splunkServiceConfiguration.url();
		String token = _splunkServiceConfiguration.token();

		if (Validator.isNull(url) || Validator.isNull(token)) {
			_log.warn("url and/or token are not configured");
			return;
		}

		try {

			JSONObject event = this.getEventJson(auditMessage);

			URL urlToSplunk = new URL(url);
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlToSplunk.openConnection();
			httpURLConnection.setRequestProperty("Authorization", "Splunk " + token);
			httpURLConnection.setRequestProperty("Content-Type", "application/json; UTF-8");

			httpURLConnection.setDoOutput(true);
			DataOutputStream outputStream = new DataOutputStream(httpURLConnection.getOutputStream());
			outputStream.write(event.toString().getBytes("UTF8"));
			int responseCode = httpURLConnection.getResponseCode();
			String description = GetterUtil.getString(httpURLConnection.getContent());

			if (_log.isDebugEnabled()) {
				_log.debug(
						"event submitted to Splunk, response code = " + responseCode + ", description=" + description);
			}

			if (responseCode != 200) {
				throw new SplunkException(responseCode, description);
			}

		} catch (Exception e) {
			_log.error("failed to send message to " + url + " using token " + token, e);
			throw new PortalException(e);
		}

	}

	private JSONObject getEventJson(AuditMessage auditMessage) {
		JSONObject eventJson = JSONFactoryUtil.createJSONObject();
		eventJson.put("event", auditMessage.toJSONObject());
		eventJson.put("time", auditMessage.getTimestamp());
		return eventJson;

	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_splunkServiceConfiguration = ConfigurableUtil.createConfigurable(SplunkServiceConfiguration.class, properties);
	}

	private volatile SplunkServiceConfiguration _splunkServiceConfiguration;

	private static Log _log = LogFactoryUtil.getLog(SplunkServiceImpl.class);

}