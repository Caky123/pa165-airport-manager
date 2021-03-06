<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<table class="basic">
    <tr>
        <th><f:message key="id"/></th>
        <th><f:message key="destination.country"/></th>
        <th><f:message key="destination.city"/></th>
        <th><f:message key="destination.code"/></th>
        <th colspan="4"><f:message key="operations"/></th>
    </tr>
    <c:forEach items="${actionBean.destinations}" var="destination">
        <tr>
            <td>${destination.id}</td>
            <td><c:out value="${destination.country}"/></td>
            <td><c:out value="${destination.city}"/></td>
            <td><c:out value="${destination.code}"/></td>
<!--            <td>
                <%--<s:form beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationsActionBean">--%>
                    <%--<s:submit name="incoming">--%>
                        <%--<s:param name="destination.id" value="${destination.id}"/>--%>
                        <%--<s:param name="event" value="incoming"/>--%>
                        <%--<f:message key="destination.incoming"/>--%>
                    <%--</s:submit>--%>
                <%--</s:form>--%>
             </td>
             <td>
                <%--<s:form beanclass="cz.muni.fi.pa165.airportmanager.web.beans.DestinationsActionBean">--%>
                    <%--<s:submit name="outcoming">--%>
                        <%--<s:param name="destination.id" value="${destination.id}"/>--%>
                        <%--<s:param name="event" value="outcoming"/>--%>
                        <%--<f:message key="destination.outcoming"/>--%>
                    <%--</s:submit>--%>
                <%--</s:form>--%>
            </td>-->
                <td>
                <s:link beanclass="cz.muni.fi.pa165.airportmanager.rest.client.DestinationsClientActionBean" event="incoming">
                    <s:param name="destination.id" value="${destination.id}"/>
                    <f:message key="destination.incoming"/>
                </s:link>
            </td>
            <td>
                <s:link beanclass="cz.muni.fi.pa165.airportmanager.rest.client.DestinationsClientActionBean" event="outcoming">
                    <s:param name="destination.id" value="${destination.id}"/>
                    <f:message key="destination.outcoming"/>
                </s:link>
            </td>
            <td>
                <s:link beanclass="cz.muni.fi.pa165.airportmanager.rest.client.DestinationsClientActionBean" event="edit">
                    <s:param name="destination.id" value="${destination.id}"/>
                    <img class="icon" src="${pageContext.request.contextPath}/images/edit.png"/>
                </s:link>
            </td>
            <td>
                <s:link beanclass="cz.muni.fi.pa165.airportmanager.rest.client.DestinationsClientActionBean" event="delete">
                    <s:param name="destination.id" value="${destination.id}"/>
                    <img class="icon" src="${pageContext.request.contextPath}/images/delete.png"/>
                </s:link>
            </td>
        </tr>
    </c:forEach>
</table>