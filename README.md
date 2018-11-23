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
> got to your liferay Portal Control Panel => Configuration => System Settings => Platform => third Party 
add le Splunk URL with the the following format protocole://splunkServer:port/services/Collector ,
add the token that you already created and if your on a Liferay DXP instance check the check Box (Enable Splunk Audit) to make your portal forword Audit Message to your Splunk Server automaticly 

![image](https://image.ibb.co/nzG64q/splunk-Config.png)

for more information about HEC (HTTP Event Collector)  check the documentation below 
 [go splunk documentation](https://docs.splunk.com/Documentation/Splunk/7.2.1/Data/HECExamples )
 