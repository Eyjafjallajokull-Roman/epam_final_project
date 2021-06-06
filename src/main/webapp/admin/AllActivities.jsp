<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set value="${pageScope.get(\"currentPageAdmin\")}" var="currentPageAdmin" scope="page"/>
<c:set value="${pageScope.get(\"typeAdmin\")}" var="typeAdmin" scope="page"/>
<c:set value="${pageScope.get(\"typeActivityAdmin\")}" var="typeActivityAdmin" scope="page"/>
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
            <form id="flexForm" action="/project/controller" method="get">
                <input type="hidden" name="command" value="pageNextAdmin"/>
                <select name="typeAdmin">
                    <option value="activity.start_time" ${typeAdmin == "activity.start_time" ? 'selected' : ''}>Start
                        Time
                    </option>
                    <option value="activity.end_time" ${typeAdmin == "activity.end_time" ? 'selected' : ''}>End Time
                    </option>
                    <option value="activity.name" ${typeAdmin == "activity.name" ? 'selected' : ''}>Name</option>
                    <option value="activity.users" ${typeAdmin == "activity.users" ? 'selected' : ''}>By Users</option>
                </select>
                <select name="typeActivityAdmin">
                    <option value="all" ${typeActivityAdmin == "all" ? 'selected' : ''}>All</option>
                    <option value="EVENT" ${typeActivityAdmin == "EVENT" ? 'selected' : ''}>Event</option>
                    <option value="TASK" ${typeActivityAdmin == "TASK" ? 'selected' : ''}>Task</option>
                    <option value="REMINDER" ${typeActivityAdmin == "REMINDER" ? 'selected' : ''}>Reminder</option>
                    <option value="TIME_TRACKER" ${typeActivityAdmin == "TIME_TRACKER" ? 'selected' : ''}>Time Tracker
                    </option>
                </select>
                <button class="addActivity" type="submit" class="giantbutton">My Activities</button>
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

    <c:choose>
        <c:when test="${not empty AllActivities}">
            <div >
                <table class="tableBlock">
                    <tr>
                        <th><fmt:message key="tableA.name" bundle="${local}"/></th>
                        <th><fmt:message key="tableA.Description" bundle="${local}"/></th>
                        <th><fmt:message key="tableA.StartTime" bundle="${local}"/></th>
                        <th><fmt:message key="tableA.EndTime" bundle="${local}"/></th>
                        <th><fmt:message key="tableA.TypeOfActivity" bundle="${local}"/></th>
                        <th><fmt:message key="tableA.command" bundle="${local}"/></th>
                    </tr>
                    <c:forEach items="${AllActivities}" var="activity">

                        <form name="activity">
                            <input type="hidden" name="activityId" value="${activity.id}">
                            <tr>
                                <td class="tda">${activity.name}</td>
                                <c:choose>
                                    <c:when test="${language == 'ru_RU'}">
                                        <td>${activity.descriptionRus}</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>${activity.descriptionEng}</td>
                                    </c:otherwise>
                                </c:choose>
                                <td class="tda">${activity.startTime}</td>
                                <td class="tda">${activity.endTime}</td>
                                <td class="tda">${activity.typeOfActivity}</td>
                                <input type="hidden" name="email"
                                       value=" ${userService.findUserById(activity.getCreatedByUserID()).getEmail()}"/>
                                <td>
                                    <form class="menuitem" name="updateActivityPage" method="post"
                                          action="/project/controller">
                                        <input type="hidden" name="command" value="addUserToActivity"/>
                                        <input type="hidden" name="activityToInsert" value="${activity.id}">
                                        <input type="text" name="userEmail" placeholder="User Email">
                                        <button class="menubutton" type="submit">Add User</button>
                                    </form>

                                    <form class="menuitem" name="pageNextUser" method="get"
                                          action="/project/controller">
                                        <input type="hidden" name="command" value="pageNextFUA"/>
                                        <input type="hidden" name="activityIdFUA" value="${activity.id}">
                                        <button class="menubutton" type="submit">Show Users</button>
                                    </form>
                                    <form>
                                        <input type="hidden" name="command" value="showCreatedUser"/>
                                        <input type="hidden" name="createdId"
                                               value="${userService.findUserById(activity.getCreatedByUserID()).getEmail()}">
                                        <button type="submit">ShowInfoWhoCreated</button>
                                    </form>
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                </table>
            </div>
            <div>
                <form action="/project/controller" method="post" name="saveExcel">
                    <input type="hidden" name="command" value="saveToExcel">
                    <button type="submit">Save Excel</button>
                </form>
            </div>


            <div class="pagination">
                <c:forEach var="i" begin="1" end="${totalPagesAdmin}">
                    <c:if test="${i==currentPageAdmin}">
                        <a class="active"
                           href="/project/controller?command=pageNextAdmin&currentPageAdmin=${i}&typeAdmin=${typeAdmin}&typeActivityAdmin=${typeActivityAdmin}">${i}</a>
                    </c:if>
                    <c:if test="${i!=currentPageAdmin}">
                        <a href="/project/controller?command=pageNextAdmin&currentPageAdmin=${i}&typeAdmin=${typeAdmin}&typeActivityAdmin=${typeActivityAdmin}">${i}</a>
                    </c:if>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <h1>No Activities found by this criteria </h1>
        </c:otherwise>
    </c:choose>
</div>
<f:colontitle/>
<script src="/project/js/language.js"></script>
</body>
</html>
