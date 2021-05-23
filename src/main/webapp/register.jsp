<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>

<html>
<head>
    <title>Registration</title>
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
                <h1>Please sign up</h1>
                <form class="register-form" action="/project/controller" method="post">
                    <div class="form-control">
                        <input name="name" type="text" placeholder="first name"/>
                    </div>
                    <div class="form-control">
                        <input name="lastName" type="text" placeholder="last name"/>
                    </div>
                    <div class="form-control">
                        <input name="email" type="email" placeholder="email address"/>
                    </div>
                    <div class="form-control">
                        <input name="password" type="password" placeholder="password"/>
                    </div>
                    <div class="form-control">
                        <input name="confirmPassword" type="password" placeholder="confirm password"/>
                    </div>
                    <input type="hidden" name="command" value="register">
                    <div class="buttonsBlock">
                        <button id="btnForSinUp" type="submit">Sign Up</button>
                        <br/>
                    </div>
                </form>
                <p id="questionAboutAcc">Already have an account?</p>
                <form name="goToLogin" action="/project/controller" method="post">
                    <input type="hidden" name="command" value="goToLogin">
                    <button id="registered" type="submit">Login here</button>
                </form>
            </div>
        </div>
    </div>
    <f:colontitle/>
</div>
</body>
</html>
