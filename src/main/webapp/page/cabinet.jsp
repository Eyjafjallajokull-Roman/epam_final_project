<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language :
                                not empty language ? language :
                                pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="local" var="local"/>
<html>
<head>
    <title><fmt:message key="cabinet.title" bundle="${local}"/></title>
</head>
<body>
<h3>
    ${userLogin}
</h3>

<form class="menuitem" name="logout" method="post" action="/project/controller">
    <input type="hidden" name="command" value="logout"/>
    <button class="menubutton" type="submit">LOGOUT</button>
</form>

<a href="createActivity.jsp">Create Product</a>


<form name="all-activities" method="post" action="/project/controller">
    <input type="hidden" name="command" value="all-activities"/>
    <button class="menubutton" type="submit">Show Activities</button>
</form>

<div class="all-activities">
    <h5>${activities}</h5>
</div>

<form class="topcorner" method="post">
    <select id="language" name="language" onchange="submit()">
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
    </select>
</form>
</body>
</html>
