package com.ajizan.liferay.splunk.web.portlet;

import com.ajizan.liferay.splunk.web.constants.LiferaySplunkWebPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author oharrari
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + LiferaySplunkWebPortletKeys.LiferaySplunkWeb,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class LiferaySplunkWebPortlet extends MVCPortlet {
	
	public static final Log _log = LogFactoryUtil.getLog(LiferaySplunkWebPortlet.class);
	
	
	private void  sentEvent(String event) {
		 Message message = new Message();
		 message.setDestinationName(LiferaySplunkWebPortletKeys.DESTINATION_NAME);
		 message.setResponseDestinationName(LiferaySplunkWebPortletKeys.BUS_RESPONSE);
		 message.put("event", event);
		 _messageBus.sendMessage(message.getDestinationName(), message);
	}
	
   public void submitEvent(ActionRequest request, ActionResponse actionResponse) {
	   
	  String event =  ParamUtil.getString(request, "event");
	  
	  this.sentEvent(event);
   }
	
	@Reference
	private MessageBus _messageBus;
	
}