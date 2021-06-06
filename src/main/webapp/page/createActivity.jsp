<%@ taglib prefix="code" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set value="${requestScope.get(\"type\")}" var="type" scope="request"/>
<c:set var="language" value="${not empty param.language ? param.language :
                                not empty language ? language :
                                pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="local" var="local"/>
<html>
<head>
    <title><fmt:message key="cabinet.createActivity" bundle="${local}"/></title>
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
                <a href="/project/page/cabinet.jsp">
                    <%@include file="../icons/load.svg" %>
                </a>
            </div>
        </div>
        <div class="btnAct">
            <form id="flexForm" name="chooseType" action="/project/controller" method="post">
                <select name="typeOfActivity">
                    <option value="EVENT" ${typeActivityFUA == "EVENT" ? 'selected' : ''}>Event</option>
                    <option value="TASK" ${typeActivityFUA == "TASK" ? 'selected' : ''}>Task</option>
                    <option value="REMINDER" ${typeActivityFUA == "REMINDER" ? 'selected' : ''}>Reminder</option>
                    <option value="TIME_TRACKER" ${typeActivityFUA == "TIME_TRACKER" ? 'selected' : ''}>Time Tracker</option>
                </select>
                <input type="hidden" name="command" value="chooseTypeActivity">
                <input type="hidden" name="type" value="${typeP}">
                <button class="addActivity" type="submit">Confirm Type</button>
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
            <c:choose>
                <c:when test="${type=='create'}">
                    <h1>Create Activity</h1>
                </c:when>
                <c:when test="${type=='update'}">
                    <h1>Update Activity</h1>
                </c:when>
            </c:choose>

            <c:if test="${type=='create'}">
                <form name="createProduct" method="post" action="/project/controller">
                    <c:choose>
                        <c:when test="${typeOfActivity=='EVENT' || typeOfActivity == 'TASK' }">
                            <input type="hidden" name="command" value="createActivity">
                            <div class="form-control">
                                <input type="text" name="name"
                                       placeholder="<fmt:message key="tableA.name" bundle="${local}"/>">
                            </div>
                            <div class="form-control">
                                <input type="text" name="description_en"
                                       placeholder="<fmt:message key="tableA.DescriptionEn" bundle="${local}"/>">
                            </div>
                            <div class="form-control">
                                <input type="text" name="description_ru"
                                       placeholder="<fmt:message key="tableA.DescriptionRu" bundle="${local}"/>">
                            </div>
                            <input type="hidden" name="typeOfActivity" value="${typeOfActivity}">
                            <div class="form-control">
                                <input type="datetime-local" name="start_time"
                                       placeholder="<fmt:message key="tableA.StartTime" bundle="${local}"/>">
                            </div>
                            <div class="form-control">
                                <input type="datetime-local" name="end_time"
                                       placeholder="<fmt:message key="tableA.EndTime" bundle="${local}"/>">
                            </div>
                            <button id="login_btn" type="submit">Add New Activity</button>
                        </c:when>
                        <c:when test="${typeOfActivity=='REMINDER'}">
                            <input type="hidden" name="command" value="createActivity">
                            <div class="form-control">
                                <input type="text" name="name"
                                       placeholder="<fmt:message key="tableA.name" bundle="${local}"/>">
                            </div>
                            <div class="form-control">
                                <input type="text" name="description_en"
                                       placeholder="<fmt:message key="tableA.DescriptionEn" bundle="${local}"/>">
                            </div>
                            <div class="form-control">
                                <input type="text" name="description_ru"
                                       placeholder="<fmt:message key="tableA.DescriptionRu" bundle="${local}"/>">
                            </div>
                            <input type="hidden" name="typeOfActivity" value="${typeOfActivity}">
                            <div class="form-control">
                                <input type="datetime-local" name="end_time"
                                       placeholder="<fmt:message key="tableA.EndTime" bundle="${local}"/>">
                            </div>
                            <button id="login_btn" type="submit">Add New Activity</button>
                        </c:when>
                        <c:when test="${typeOfActivity == 'TIME_TRACKER'}">
                            <input type="hidden" name="command" value="createActivity">
                            <div class="form-control">
                                <input type="text" name="name"
                                       placeholder="<fmt:message key="tableA.name" bundle="${local}"/>">
                            </div>
                            <div class="form-control">
                                <input type="text" name="description_en"
                                       placeholder="<fmt:message key="tableA.DescriptionRu" bundle="${local}"/>">
                            </div>
                            <div class="form-control">
                                <input type="text" name="description_ru"
                                       placeholder="<fmt:message key="tableA.DescriptionEn" bundle="${local}"/>">
                            </div>
                            <input type="hidden" name="typeOfActivity" value="${typeOfActivity}">
                            <button id="login_btn" type="submit">Add New Activity</button>
                        </c:when>
                    </c:choose>
                </form>
            </c:if>
        </div>
    </div>

    <c:if test="${type=='update'}">
        <form name="updateActivity" method="post" action="/project/controller">
            <input type="hidden" name="command" value="updateActivity">
            <div class="form-control">
                <input type="text" name="name" value="${name}"
                       placeholder="<fmt:message key="tableA.name" bundle="${local}"/>">
            </div>
            <div class="form-control">
                <select name="typeOfActivity">
                    <option value="EVENT">Event</option>
                    <option value="TASK">Task</option>
                    <option value="REMINDER">Reminder</option>
                </select>
            </div>
            <div class="form-control">
                <input type="text" name="description_en" value="${description_en}"
                       placeholder="<fmt:message key="tableA.DescriptionEn" bundle="${local}"/>">
            </div>
            <div class="form-control">
                <input type="text" name="description_ru" value="${description_ru}"
                       placeholder="<fmt:message key="tableA.DescriptionRu" bundle="${local}"/>">
            </div>
            <div class="form-control">
                <input type="datetime-local" name="start_time" value="${start_time}"
                       placeholder="<fmt:message key="tableA.StartTime" bundle="${local}"/>">
            </div>
            <div class="form-control">
                <input type="datetime-local" name="end_time" value="${end_time}"
                       placeholder="<fmt:message key="tableA.EndTime" bundle="${local}"/>">
            </div>
            <input type="hidden" name="id" value="${id}">
            <button id="btnForSinUp" type="submit">Update Activity</button>
        </form>
    </c:if>
</div>

<f:colontitle/>
<script src="/project/js/language.js"></script>
</body>
</html>
