<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set value="${pageScope.get(\"currentPageFUA\")}" var="currentPageFUA" scope="page"/>
<c:set value="${pageScope.get(\"typeFUA\")}" var="typeFUA" scope="page"/>

<html>
<head>
    <title>Activity List</title>
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
                <a href="adminCabinet.jsp"><%@include file="../icons/load.svg" %></a>
            </div>
        </div>
        <div class="btnAct">
            <c:if test="${not empty email}">
                <form action="/project/controller" method="get">
                    <input type="hidden" name="command" value="pageNextFUActivity"/>
                    <input type="hidden" name="email" value="${email}">
                    <select name="typeFUA">
                        <option value="activity.start_time" ${typeFUA== "activity.start_time" ? 'selected' : ''}>Start Time
                        </option>
                        <option value="activity.end_time" ${typeFUA == "activity.end_time" ? 'selected' : ''}>End Time</option>
                        <option value="activity.name"  ${typeFUA == "activity.name" ? 'selected' : ''}>Name</option>
                    </select>
                    <select name="typeActivityFUA">
                        <option value="all" ${typeActivityFUA == "all" ? 'selected' : ''}>All</option>
                        <option value="EVENT" ${typeActivityFUA == "EVENT" ? 'selected' : ''}>Event</option>
                        <option value="TASK" ${typeActivityFUA == "TASK" ? 'selected' : ''}>Task</option>
                        <option value="REMINDER" ${typeActivityFUA == "REMINDER" ? 'selected' : ''}>Reminder</option>
                        <option value="TIME_TRACKER" ${typeActivityFUA == "TIME_TRACKER" ? 'selected' : ''}>Time Tracker
                        </option>
                    </select>
                    <button type="addActivity" class="giantbutton">My Activities</button>
                </form>
            </c:if>
            <form action="/project/controller" method="get">
                <input type="hidden" name="command" value="pageNextFUActivity">
                <input type="text" name="email" value="${email}">
                <button class="addActivity" type="submit" class="giantbutton">Users</button>
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


<%--    <c:if test="${not empty email}">--%>
<%--        <form action="/project/controller" method="get">--%>
<%--            <input type="hidden" name="command" value="pageNextFUActivity"/>--%>
<%--            <input type="hidden" name="email" value="${email}">--%>
<%--            <select name="typeFUA">--%>
<%--                <option value="activity.start_time" ${typeFUA== "activity.start_time" ? 'selected' : ''}>Start Time--%>
<%--                </option>--%>
<%--                <option value="activity.end_time" ${typeFUA == "activity.end_time" ? 'selected' : ''}>End Time</option>--%>
<%--                <option value="activity.name"  ${typeFUA == "activity.name" ? 'selected' : ''}>Name</option>--%>
<%--            </select>--%>
<%--            <select name="typeActivityFUA">--%>
<%--                <option value="all" ${typeActivityFUA == "all" ? 'selected' : ''}>All</option>--%>
<%--                <option value="EVENT" ${typeActivityFUA == "EVENT" ? 'selected' : ''}>Event</option>--%>
<%--                <option value="TASK" ${typeActivityFUA == "TASK" ? 'selected' : ''}>Task</option>--%>
<%--                <option value="REMINDER" ${typeActivityFUA == "REMINDER" ? 'selected' : ''}>Reminder</option>--%>
<%--                <option value="TIME_TRACKER" ${typeActivityFUA == "TIME_TRACKER" ? 'selected' : ''}>Time Tracker--%>
<%--                </option>--%>
<%--            </select>--%>
<%--            <button type="submit" class="giantbutton">My Activities</button>--%>
<%--        </form>--%>
<%--    </c:if>--%>
    <c:choose>
        <c:when test="${not empty activities}">
            <div class="tableBlock">
                <table>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Start Time</th>
                        <th>End Time</th>
                        <th>Type of activity</th>
                        <th>Commands</th>
                    </tr>
                    <c:forEach items="${activities}" var="activity">

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
                                <td>
                                    <form>
                                        <input type="hidden" name="command" value="showCreatedUser"/>
                                        <input type="hidden" name="createdId"
                                               value="${userService.findUserById(activity.getCreatedByUserID()).getEmail()}">
                                        <button type="submit">ShowInfoWhoCreated</button>
                                    </form>
                                    <c:if test="${not empty email}">
                                        <form>
                                            <input type="hidden" name="command" value="deleteUserFromActivity"/>
                                            <input type="hidden" name="activityIdFUA" value="${activity.id}">
                                            <input type="hidden" name="email" value="${email}">
                                            <button type="submit">DeleteUserFromActivity</button>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                </table>
            </div>


            <div class="pagination">
                <c:forEach var="i" begin="1" end="${totalPagesFUA}">
                    <c:if test="${i==currentPageFUA}">
                        <a class="active"
                           href="/project/controller?command=pageNextFUActivity&email${email}&currentPageFUA=${i}&typeFUA=${typeFUA}&typeActivityFUA=${typeActivityFUA}">${i}</a>
                    </c:if>
                    <c:if test="${i!=currentPageFUA}">
                        <a href="/project/controller?command=pageNextFUActivity&currentPageFUA=${i}&email=${email}&typeFUA=${typeFUA}&typeActivityFUA=${typeActivityFUA}">${i}</a>
                    </c:if>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>

        </c:otherwise>
    </c:choose>
</div>
<f:colontitle/>
<script src="../js/language.js"></script>
</body>
</html>
