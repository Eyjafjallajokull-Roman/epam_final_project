<%@ taglib prefix="code" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set value="${requestScope.get(\"type\")}" var="type" scope="request"/>
<html>
<head>
    <title>Create Activity</title>
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
                <a href="cabinet.jsp"><%@include file="../icons/load.svg" %></a>
            </div>
        </div>
        <div class="btnAct">
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
                                <select id="language" name="language">
                                    <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
                                    <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
                                </select>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>


    <form name="chooseType" action="/project/controller" method="post">
        <select name="typeOfActivity">
            <option value="EVENT">Event</option>
            <option value="TASK">Task</option>
            <option value="REMINDER">Reminder</option>
            <option value="TIME_TRACKER">Time Tracker</option>
        </select>
        <input type="hidden" name="command" value="chooseTypeActivity">
        <input type="hidden" name="type" value="${typeP}">
        <button type="submit">Confirm Type</button>
    </form>

    <div class="signForm">
        <div class="entering">
            <h1>Create Activity</h1>
            <c:if test="${type=='create'}">
                <form name="createProduct" method="post" action="/project/controller">
                    <c:choose>
                        <c:when test="${typeOfActivity=='EVENT' || typeOfActivity == 'TASK' }">
                            <input type="hidden" name="command" value="createActivity">
                            <div class="form-control">
                                <input type="text" name="name" placeholder="name">
                            </div>
                            <div class="form-control">
                                <input type="text" name="description_en" placeholder="description_en">
                            </div>
                            <div class="form-control">
                                <input type="text" name="description_ru" placeholder="description_ru">
                            </div>
                            <input type="hidden" name="typeOfActivity" value="${typeOfActivity}">
                            <div class="form-control">
                                <input type="datetime-local" name="start_time" placeholder="start">
                            </div>
                            <div class="form-control">
                                <input type="datetime-local" name="end_time" placeholder="end">
                            </div>
                            <button id="login_btn" type="submit">Add new Activity</button>
                        </c:when>
                        <c:when test="${typeOfActivity=='REMINDER'}">
                            <input type="hidden" name="command" value="createActivity">
                            <div class="form-control">
                                <input type="text" name="name" placeholder="name">
                            </div>
                            <div class="form-control">
                                <input type="text" name="description_en" placeholder="description_ru">
                            </div>
                            <div class="form-control">
                                <input type="text" name="description_ru" placeholder="description_ru">
                            </div>
                            <input type="hidden" name="typeOfActivity" value="${typeOfActivity}">
                            <div class="form-control">
                                <input type="datetime-local" name="end_time" placeholder="end">
                            </div>
                            <button id="login_btn" type="submit">Add new Activity</button>
                        </c:when>
                        <c:when test="${typeOfActivity == 'TIME_TRACKER'}">
                            <input type="hidden" name="command" value="createActivity">
                            <div class="form-control">
                                <input type="text" name="name" placeholder="name">
                            </div>
                            <div class="form-control">
                                <input type="text" name="description_en" placeholder="description_ru">
                            </div>
                            <div class="form-control">
                                <input type="text" name="description_ru" placeholder="description_ru">
                            </div>

                            <input type="hidden" name="typeOfActivity" value="${typeOfActivity}">
                            <button id="login_btn" type="submit">Add new Activity</button>
                        </c:when>
                    </c:choose>
                </form>
            </c:if>
        </div>
    </div>

            <c:if test="${type=='update'}">
                <form name="updateActivity" method="post" action="/project/controller">
                    <input type="hidden" name="command" value="updateActivity">
                    <input type="text" name="name" value="${name}" placeholder="name">
                    <select name="typeOfActivity">
                        <option value="EVENT">Event</option>
                        <option value="TASK">Task</option>
                        <option value="REMINDER">Reminder</option>
                    </select>
                    <input type="text" name="description_en" value="${description_en}" placeholder="description_en">
                    <input type="text" name="description_ru" value="${description_ru}" placeholder="description_ru">
                    <input type="datetime-local" name="start_time" value="${start_time}" placeholder="start">
                    <input type="datetime-local" name="end_time" value="${end_time}" placeholder="end_Time">
                    <input type="hidden" name="id" value="${id}">
                    <input type="submit" value="updateActivity">
                </form>
            </c:if>
</div>

    <f:colontitle/>
<script src="../js/language.js"></script>
</body>
</html>
