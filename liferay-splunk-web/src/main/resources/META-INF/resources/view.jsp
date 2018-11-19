<%@ include file="./init.jsp" %>



<portlet:actionURL name="submitEvent" var="submitEventURL">
    <portlet:param name="redirect" value="${currentUrl}"/>
</portlet:actionURL>

<aui:form action="<%=submitEventURL%>" method="POST" name="eventForm">

	

   <aui:input name="event" type="text"  placeholder="event"  label="event" required="true"/>
  
   <aui:button-row> 
     <aui:button type="submit" />
   </aui:button-row>
</aui:form>
