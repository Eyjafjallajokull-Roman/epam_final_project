<%@ page language="java" contentType="text/html;UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty param.language ? param.language :
                                not empty language ? language :
                                pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="local" var="local"/>
<html>
<head>
    <title><fmt:message key="login.title" bundle="${local}"/></title>
</head>
<body>
<h1><fmt:message key="login.h1" bundle="${local}"/></h1>
<form name="LoginForm" method="post" action="/project/controller">
    <input type="email" name="email" placeholder="Email" required>
    <input type="password" name="password" placeholder="<fmt:message key="login.password" bundle="${local}"/>" required>
    <input type="hidden" name="command" value="login">
    <input type="submit" value="<fmt:message key="login.send" bundle="${local}"/>">
</form>
<br>
<form class="topcorner" method="post">
    <select id="language" name="language" onchange="submit()">
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
    </select>
</form>
</body>
</html>