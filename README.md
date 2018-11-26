#  Liferay 7.1 Splunk Audit Plugin 
## abstract 
>Splunk Audit Plugin is a liferay 7.1  plugin provide an easy way  to link your **Liferay portal** to your **Splunk Server** in order to send audit messages for a future deep processing .


## Getting Started
* before we get into the installation guide , it will be wise to check out the compatiblities :

### compatibilities
  > this plugins is devopped and tested for both (Community Edition & DXP ) of Liferay  7.1 , 
        except for Liferay CE because it does not support (Portal Security audit ) so u have to 
        invoke Splunk Audit Plugin programmatically  as explained in next lignes .
            
## Installation 

> *  download the plugin from Marcketplace  and intall it , [guide to install app from marketplace](https://dev.liferay.com/discover/portal/-/knowledge_base/7-1/using-the-liferay-marketplace )

> * or clone this repository and place it to your liferay workspace/modules 
> import it with with your IDE then build & deploy to your liferay instance 

## Configuration 

> **step 1 : Create Splunk Token**
>  go to your splunk server => settings  => data input stats => http Event Collector  and add new Token 

> **step2 : Configure Splunk in your liferay instance**
> go to your liferay Portal Control Panel => Configuration => System Settings => Platform => third Party 
add le Splunk URL with the the following format protocole://splunkServer:port/services/Collector ,
add the token that you already created ,  if your on a Liferay DXP instance check (Enable Splunk Audit) to make your portal forword Audit Message to your Splunk Server automaticly ,  otherwise (CE cas )  you have to do it programmatically which will be covered in the end of this article.



![image](https://image.ibb.co/nzG64q/splunk-Config.png) 

>for more information about HEC (HTTP Event Collector)  check the documentation below 
 [go splunk documentation](https://docs.splunk.com/Documentation/Splunk/7.2.1/Data/HECExamples )

> ***how to use Splunk Audit Plugin on Liferay 7.1 coumunity edition***
all you have to do is to send a com.liferay.portal.kernel.messaging.Message instance with the properties "event" and "time" to the Splunk Audit Plugin Message Bus with destination name "splunkDestination"

> exemple : 
```
      	public void  sentEvent(String event) {
		 Message message = new Message();
		 message.setDestinationName(LiferaySplunkWebPortletKeys.DESTINATION_NAME);
		 message.put("event", event);
		 long  ts= new Date().getTime()/1000;  
		 message.put("time", ts);
		 _messageBus.sendMessage(message.getDestinationName(), message);
	}
```
