<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set var="language" value="${not empty param.language ? param.language :
                                not empty language ? language :
                                pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="local" var="local"/>
<html>
<head>
    <title>Title</title>
    <style>
        <%@include file="../style/style.css"%>
        <%@include file="/style/cabinetStyle.css"%>
    </style>
</head>
<body>
<div class="page">
    <div class="header">
        <div class="leftHeader">
            <div class="logo">
                <a href="/project/admin/adminCabinet.jsp">
                    <%@include file="../icons/load.svg" %>
                </a>
            </div>
        </div>
        <div class="btnAct">
            <form action="ActivitiesOnCheck.jsp">
                <button class="addActivity" type="submit">ActivitiesOnCheck</button>
            </form>
            <form action="AllActivities.jsp">
                <button class="addActivity" type="submit">All Activities</button>
            </form>
            <form action="AllUsers.jsp">
                <button class="addActivity" type="submit">All Users</button>
            </form>
            <form action="FindUserByActivity.jsp">
                <button class="addActivity" type="submit">FindUsersActivity</button>
            </form>
            <form action="FindActivityByUser.jsp">
                <button class="addActivity" type="submit">Find Activities By User</button>
            </form>
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
                        <div class="checkLanguage">
                            <button onclick="myFunction()" class="langBtn">Language</button>
                            <div id="ChangeLanguage" class="languages-list">
                                <form class="topcorner" method="post">
                                <select id="language" name="language">
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
</div>

<h2> Hello Admin: ${userLogin} </h2>


<f:colontitle/>
<script src="/project/js/language.js"></script>
</body>
</html>
