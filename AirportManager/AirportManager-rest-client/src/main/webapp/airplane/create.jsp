<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout.jsp" titlekey="index.title">
    <s:layout-component name="body">
        
        <s:useActionBean beanclass="cz.muni.fi.pa165.airportmanager.rest.client.AirplaneClientActionBean" var="actionBean"/>
        
        <div class="text-content">
            <s:form beanclass="cz.muni.fi.pa165.airportmanager.rest.client.AirplaneClientActionBean">
                <s:hidden name="airplane.id"/>
                <fieldset><legend><f:message key="airplane.create"/></legend>
                    <%@include file="form.jsp"%>
                    <s:submit name="add">
                        <f:message key="airplane.create"/>
                    </s:submit>
                </fieldset>
            </s:form>
        </div>

    </s:layout-component>
</s:layout-render>