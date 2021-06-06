<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set value="${sessionScope.get(\"user\")}" var="user" scope="session"/>
<c:set var="language" value="${not empty param.language ? param.language :
                                not empty language ? language :
                                pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="local" var="local"/>
<html>
<head>
    <style>
        <%@include file="../style/style.css"%>
        <%@include file="/style/cabinetStyle.css"%>
    </style>
    <title>Update</title>

</head>
<body>
<div class="page">
    <div class="header">
        <div class="btnAct">
            <div class="leftHeader">
                <div class="logo">
                    <a href="/project/page/cabinet.jsp">
                        <%@include file="../icons/load.svg" %>
                    </a>
                </div>
            </div>
        </div>
        <div class="rightHeader">
            <div class="hamburger-menu">
                <input id="menu__toggle" type="checkbox"/>
                <label class="menu__btn" for="menu__toggle">
                    <span></span>
                </label>
                <ul class="menu__box">
                    <li>
                        <form class="menu__item" name="logout" method="post" action="/project/controller">
                            <input type="hidden" name="command" value="logout"/>
                            <button class="langBtn" type="submit">LOGOUT</button>
                        </form>
                    </li>
                    <li>
                        <form class="menu__item" name="updateUserPage" method="post" action="/project/controller">
                            <input type="hidden" name="command" value="updateUserPage"/>
                            <button class="langBtn" type="submit">Update</button>
                        </form>
                    </li>
                    <li>
                        <div class="checkLanguage">
                            <button onclick="myFunction()" class="langBtn">Language</button>
                            <div id="ChangeLanguage" class="languages-list">
                                <form class="topcorner" method="post">
                                    <select id="language" name="language" onchange="submit()">
                                        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
                                        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
                                    </select>
                                </form>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>

    <div class="signForm">
        <div class="entering">
            <h1>Update</h1>
            <form name="updateUser" action="/project/controller" method="post">
                <input type="hidden" name="command" value="updateUser">
                <div class="form-control">
                    <input name="name" type="text" placeholder="first name" value="${user.name}">
                </div>
                <div class="form-control">
                    <input name="surname" type="text" placeholder="lastname" value="${user.surname}">
                </div>

                <%--
            добавити кнопку яка буде відкривти додатково ще і зміну пароля
                --%>
                <div class="form-control">
                    <input name="password" type="password" placeholder="oldPassword">
                </div>
                <div class="form-control">
                    <input name="newPassword" type="password" placeholder="New Password">
                </div>
                <div class="form-control">
                    <input name="confirmPassword" type="password" placeholder="confirmPassword">
                </div>
                <button id="login_btn" type="submit">Update User</button>
            </form>
        </div>
    </div>
</div>

<f:colontitle/>
<script src="/project/js/language.js"></script>
</body>
</html>
