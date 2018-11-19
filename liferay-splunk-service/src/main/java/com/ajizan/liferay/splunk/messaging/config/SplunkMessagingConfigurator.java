package com.ajizan.liferay.splunk.messaging.config;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;



@Component(immediate = true, service = SplunkMessagingConfigurator.class)

public class SplunkMessagingConfigurator {
	private volatile BundleContext _bundleContext;

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		DestinationConfiguration destinationConfiguration = new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_SERIAL, MessagingConfigConstants.DESTINATION_NAME);
		
	

		destinationConfiguration.setMaximumQueueSize(MessagingConfigConstants.MAX_QUERYIES);
        
		
		Destination destination = _destinationFactory.createDestination(destinationConfiguration);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("destination.name", destination.getName());
		ServiceRegistration<Destination> serviceRegistration = _bundleContext.registerService(Destination.class,
				destination, properties);

		_serviceRegistrations.put(destination.getName(), serviceRegistration);

	}

	@Deactivate
	protected void deactivate() {

		for (ServiceRegistration<Destination> serviceRegistration : _serviceRegistrations.values()) {

			Destination destination = _bundleContext.getService(serviceRegistration.getReference());

			serviceRegistration.unregister();

			destination.destroy();

		}
		_serviceRegistrations.clear();

	}

	@Reference
	private DestinationFactory _destinationFactory;

	private final Map<String, ServiceRegistration<Destination>> _serviceRegistrations = new HashMap<>();

}