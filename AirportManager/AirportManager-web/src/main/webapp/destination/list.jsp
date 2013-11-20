<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="index.title">
    <s:layout-component name="body">
        
        <s:useActionBean beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationsActionBean" var="actionBean"/>

        <div class="text-content">
            <h1><f:message key="destination.list.alldestinations"/></h1>
            <table class="basic">
                <tr>
                    <th><f:message key="id"/></th>
                    <th><f:message key="destination.country"/></th>
                    <th><f:message key="destination.city"/></th>
                    <th><f:message key="destination.code"/></th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach items="${actionBean.destinations}" var="destination">
                    <tr>
                        <td>${destination.id}</td>
                        <td><c:out value="${destination.country}"/></td>
                        <td><c:out value="${destination.city}"/></td>
                        <td><c:out value="${destination.code}"/></td>
                        <td>
                            <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationsActionBean" event="edit">
                                <s:param name="destination.id" value="${destination.id}"/>
                                <f:message key="edit"/>
                            </s:link>
                        </td>
                        <td>
                            <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationsActionBean" event="delete">
                                <s:param name="destination.id" value="${destination.id}"/>
                                <f:message key="delete"/>
                            </s:link>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationsActionBean" event="create">
                <f:message key="create"/>
            </s:link>
                
            <s:link beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationsActionBean" event="createTest">
                <f:message key="createTest"/>
            </s:link>
        </div>
        
    </s:layout-component>
</s:layout-render>