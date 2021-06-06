<%@ page language="java" contentType="text/html;UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set var="language" value="${not empty param.language ? param.language :
                                not empty language ? language :
                                pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="local" var="local"/>
<html>
<head>
    <title><fmt:message key="login.title" bundle="${local}"/></title>
    <style>
        <%@include file="style/style.css"%>
    </style>
</head>
<body>
<div class="authorizationPage">
    <div class="header">
        <div class="leftHeader">
        </div>
        <div class="rightHeader">
            <div class="checkLanguage">
                <form class="topcorner" method="post">
                    <select id="language" name="language" onchange="submit()">
                        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
                        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
                    </select>
                </form>
            </div>
        </div>
    </div>
    <div class="signForm">
        <div class="entering">
            <h1><fmt:message key="login.h1" bundle="${local}"/></h1>

            <form name="LoginForm" method="post" action="/project/controller">
                <div class="form-control">
                    <input type="email" name="email" placeholder="Email" required>
                </div>
                <div class="form-control">
                    <input type="password" name="password"
                           placeholder="<fmt:message key="login.password" bundle="${local}"/>" required>
                </div>
                <input type="hidden" name="command" value="login">
                <input id="login_btn" type="submit" value="<fmt:message key="login.send" bundle="${local}"/>">
            </form>
            <br/>
            <p id="questionAboutAcc"><fmt:message key="login.no_account" bundle="${local}"/></p>
        </div>
        <form name="goToRegister" action="/project/controller" method="post">
            <input type="hidden" name="command" value="goToRegister">
            <button id="register" type="submit"><fmt:message key="login.register_here" bundle="${local}"/></button>
        </form>
    </div>

    <f:colontitle/>
</div>
</body>
</html>