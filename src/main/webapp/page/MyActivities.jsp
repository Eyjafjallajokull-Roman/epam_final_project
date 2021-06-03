<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set value="${pageScope.get(\"currentPage\")}" var="currentPage" scope="page"/>
<c:set value="${pageScope.get(\"type\")}" var="type" scope="page"/>
<c:set value="${pageScope.get(\"typeActivity\")}" var="typeActivity" scope="page"/>
<c:set value="${sessionScope.get(\"user\")}" var="user" scope="session"/>
<html>
<head>
    <title>MyActivities</title>
    <style>
        <%@include file="../style/style.css"%>
        <%@include file="../style/cabinetStyle.css"%>
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
            <form action="/project/controller" method="get">
                <input type="hidden" name="command" value="pageNext"/>
                <select id="type" name="type">
                    <option value="activity.start_time" ${type == "activity.start_time" ? 'selected' : ''} >Start Time
                    </option>
                    <option value="activity.end_time" ${type == "activity.end_time" ? 'selected' : ''}>End Time</option>
                    <option value="activity.name" ${type == "activity.name" ? 'selected' : ''}>Name</option>
                </select>
                <select id="typeOfActivity" name="typeActivity">
                    <option value="all"  ${typeActivity == "all" ? 'selected' : ''}>All</option>
                    <option value="EVENT"  ${typeActivity == "EVENT" ? 'selected' : ''}>Event</option>
                    <option value="TASK"  ${typeActivity == "TASK" ? 'selected' : ''}>Task</option>
                    <option value="REMINDER"  ${typeActivity == "REMINDER" ? 'selected' : ''}>Reminder</option>
                    <option value="TIME_TRACKER"  ${typeActivity == "TIME_TRACKER" ? 'selected' : ''}>Time Tracker
                    </option>
                </select>
                <button class="addActivity" type="submit" class="giantbutton">Sort</button>
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
    <%--        <form action="/project/controller" method="get">--%>
    <%--            <input type="hidden" name="command" value="pageNext"/>--%>
    <%--            <select id="type" name="type">--%>
    <%--                <option value="activity.start_time" ${type == "activity.start_time" ? 'selected' : ''} >Start Time--%>
    <%--                </option>--%>
    <%--                <option value="activity.end_time" ${type == "activity.end_time" ? 'selected' : ''}>End Time</option>--%>
    <%--                <option value="activity.name" ${type == "activity.name" ? 'selected' : ''}>Name</option>--%>
    <%--            </select>--%>
    <%--            <select id="typeOfActivity" name="typeActivity">--%>
    <%--                <option value="all"  ${typeActivity == "all" ? 'selected' : ''}>All</option>--%>
    <%--                <option value="EVENT"  ${typeActivity == "EVENT" ? 'selected' : ''}>Event</option>--%>
    <%--                <option value="TASK"  ${typeActivity == "TASK" ? 'selected' : ''}>Task</option>--%>
    <%--                <option value="REMINDER"  ${typeActivity == "REMINDER" ? 'selected' : ''}>Reminder</option>--%>
    <%--                <option value="TIME_TRACKER"  ${typeActivity == "TIME_TRACKER" ? 'selected' : ''}>Time Tracker</option>--%>
    <%--            </select>--%>
    <%--            <button type="submit" class="giantbutton">Sort</button>--%>
    <%--        </form>--%>

    <c:if test="${not empty activities}">
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
                                <c:if test="${activity.createdByUserID.equals(user.id)}">
                                <form class="menuitem" name="deleteActivityUser" method="post"
                                      action="/project/controller">
                                    <input type="hidden" name="command" value="deleteActivityUser"/>
                                    <input type="hidden" name="idDelete" value="${activity.id}">
                                    <button class="menubutton" type="submit">Delete Activity</button>
                                </form>
                                <form class="menuitem" name="updateActivityPage" method="post"
                                      action="/project/controller">
                                    <input type="hidden" name="command" value="updateActivityPage"/>
                                    <input type="hidden" name="idUpdate" value="${activity.id}">
                                    <button class="menubutton" type="submit">Update Activity</button>
                                </form>
                                <form class="menuitem" name="AddUser" method="post" action="/project/controller">
                                    <input type="hidden" name="command" value="addUserToActivity"/>
                                    <input type="hidden" name="activityToInsert" value="${activity.id}">
                                    <input type="text" name="userEmail" placeholder="User Email">
                                    <button class="menubutton" type="submit">Add User</button>
                                </form>
                            </td>
                        </tr>
                        </c:if>
                    </form>
                </c:forEach>
            </table>
        </div>
        <div class="pagination">
            <c:forEach var="i" begin="1" end="${totalPages}">
                <c:if test="${i==currentPage}">
                    <a class="active"
                       href="/project/controller?command=pageNext&currentPage=${i}&type=${type}&typeActivity=${typeActivity}">${i}</a>
                </c:if>
                <c:if test="${i!=currentPage}">
                    <a href="/project/controller?command=pageNext&currentPage=${i}&type=${type}&typeActivity=${typeActivity}">${i}</a>
                </c:if>
            </c:forEach>
        </div>
    </c:if>
</div>
<f:colontitle/>
<script src="../js/language.js"></script>
</body>
</html>
