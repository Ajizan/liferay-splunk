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

package com.ajizan.liferay.splunk.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for Splunk. This utility wraps
 * {@link com.ajizan.liferay.splunk.service.impl.SplunkLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SplunkLocalService
 * @see com.ajizan.liferay.splunk.service.base.SplunkLocalServiceBaseImpl
 * @see com.ajizan.liferay.splunk.service.impl.SplunkLocalServiceImpl
 * @generated
 */
@ProviderType
public class SplunkLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.ajizan.liferay.splunk.service.impl.SplunkLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static SplunkLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SplunkLocalService, SplunkLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SplunkLocalService.class);

		ServiceTracker<SplunkLocalService, SplunkLocalService> serviceTracker = new ServiceTracker<SplunkLocalService, SplunkLocalService>(bundle.getBundleContext(),
				SplunkLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}