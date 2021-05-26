<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set var="language" value="${not empty param.language ? param.language :
                                not empty language ? language :
                                pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="local" var="local"/>

<html>
<head>
    <title><fmt:message key="register.title" bundle="${local}"/></title>
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
    <div id="mainBlock">
        <div class="registrationBlock">
            <div id="mainForm">
                <h1><fmt:message key="register.sign_up" bundle="${local}"/></h1>
                <form class="register-form" action="/project/controller" method="post">
                    <div class="form-control">
                        <input name="name" type="text" id="userName" placeholder="<fmt:message key="register.name" bundle="${local}"/>"/>
                        <i class="gg-check-o"></i>
                        <i class="gg-close"></i>
                        <p>Not a valid name</p>
                    </div>
                    <div class="form-control">
                        <input name="lastName" type="text"  id="last" placeholder="<fmt:message key="register.surname" bundle="${local}"/>"/>
                        <i class="gg-check-o"></i>
                        <i class="gg-close"></i>
                        <p>Not a valid surname</p>
                    </div>
                    <div class="form-control">
                        <input name="email" type="email" id="email" placeholder="Email"/>
                        <i class="gg-check-o"></i>
                        <i class="gg-close"></i>
                        <p>Not a valid email</p>
                    </div>
                    <div class="form-control">
                        <input name="password" type="password" id="pass" placeholder="<fmt:message key="register.password" bundle="${local}"/>"/>
                        <i class="gg-check-o"></i>
                        <i class="gg-close"></i>
                        <p>Not a valid password</p>
                    </div>
                    <div class="form-control">
                        <input name="confirmPassword" type="password" id="pass2" placeholder="<fmt:message key="register.confirm_password" bundle="${local}"/>"/>
                        <i class="gg-check-o"></i>
                        <i class="gg-close"></i>
                        <p>Not a valid password</p>
                    </div>
                    <input type="hidden" name="command" value="register">
                    <div class="buttonsBlock">
                        <button onclick="checkInputs()" id="btnForSinUp" type="submit"><fmt:message key="register.sign" bundle="${local}"/></button>
                        <br/>
                    </div>
                </form>
                <p id="questionAboutAcc"><fmt:message key="register.have_account" bundle="${local}"/></p>
                <form name="goToLogin" action="/project/controller" method="post">
                    <input type="hidden" name="command" value="goToLogin">
                    <button id="registered" type="submit"><fmt:message key="register.login_here" bundle="${local}"/></button>
                </form>
            </div>
        </div>
    </div>
    <f:colontitle/>
</div>
<script src="js/main.js"></script>
</body>
</html>
