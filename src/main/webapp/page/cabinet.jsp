<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set var="language" value="${not empty param.language ? param.language :
                                not empty language ? language :
                                pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="local" var="local"/>
<html>
<head>
    <title><fmt:message key="cabinet.title" bundle="${local}"/></title>
    <style>
        <%@include file="/style/style.css"%>
        <%@include file="/style/cabinetStyle.css"%>
    </style>
</head>

<body>
<div class="page">
    <div class="header">
        <div class="btnAct">
            <form class="menuitem" name="createActivityPage" method="post" action="/project/controller">
                <input type="hidden" name="command" value="createActivityPage"/>
                <button class="addActivity" type="submit">Create Activity</button>
            </form>
            <form class="menuitem" name="createActivityPage" method="post" action="/project/controller">
                <input type="hidden" name="command" value="goToMyActivities"/>
                <button class="addActivity" type="submit">My Activities</button>
            </form>
            <form name="all-activities" method="get" action="/project/controller">
                <input type="hidden" name="command" value="all-activities"/>
                <input type="hidden" name="showTable" value="${showTable}">
                <button class="addActivity" type="submit">Show Activities</button>
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


    <c:if test="${showTable == '1'}">
    <div class="tableBlock">
        <table>
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Start Time</th>
                <th>End Time</th>
                <th>Type of activity</th>
                <th></th>
            </tr>
            <c:forEach items="${activityList}" var="activity">
                <tr>
                    <td>${activity.name}</td>
                    <c:choose>
                        <c:when test="${language == 'ru_RU'}">
                    <td>${activity.descriptionRus}</td>
                        </c:when>
                        <c:otherwise>
                    <td>${activity.descriptionEng}</td>
                        </c:otherwise>
                    </c:choose>
                    <td>${activity.startTime}</td>
                    <td>${activity.endTime}</td>
                    <td>${activity.typeOfActivity}</td>

                    <td>
                        <c:if test="${activity.createdByUserID.equals(user.id)}">
                            <form class="menuitem" name="deleteActivityUser" method="post" action="/project/controller">
                                <input type="hidden" name="command" value="deleteActivityUser"/>
                                <input type="hidden" name="idDelete" value="${activity.id}">
                                <button class="btnDelete" type="submit"><img style="width: 30px" src="/project/icons/del.svg"
                                                                             alt=""/>
                                </button>
                            </form>
                            <form class="menuitem" name="updateActivityPage" method="post" action="/project/controller">
                                <input type="hidden" name="command" value="updateActivityPage"/>
                                <input type="hidden" name="idUpdate" value="${activity.id}">
                                <button class="btnEdit" type="submit"><img style="width: 30px" src="/project/icons/edit.svg"
                                                                           alt=""/></button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    </div>
    <f:colontitle/>
</div>


<%--            <form class="menuitem" name="createActivityPage" method="post" action="/project/controller">--%>
<%--                <input type="hidden" name="command" value="createActivityPage"/>--%>
<%--                <button class="menubutton" type="submit">Create Activity</button>--%>
<%--            </form>--%>

<%--            <form class="menuitem" name="updateActivityPage" method="post" action="/project/controller">--%>
<%--                <input type="hidden" name="command" value="updateActivityPage"/>--%>
<%--                <button class="menubutton" type="submit">Update Activity</button>--%>
<%--            </form>--%>


<%--            <form name="all-activities" method="get" action="/project/controller">--%>
<%--                <input type="hidden" name="command" value="all-activities"/>--%>
<%--                <button class="menubutton" type="submit">Show Activities</button>--%>
<%--            </form>--%>


<%--            <a href="/project/page/MyActivities.jsp">My Activities</a>--%>
<%--            <form class="menuitem" name="updateActivityPage" method="post" action="/project/controller">--%>
<%--                <input type="hidden" name="command" value="addUserToActivity"/>--%>
<%--                <input type="hidden" name="activityToInsert" value="${activity.id}">--%>
<%--                <input type="text" name="userEmail" placeholder="User Email">--%>
<%--                <button class="menubutton" type="submit">Add User</button>--%>
<%--            </form>--%>
<%--            --%>
<%--            <h3>All activities:</h3>--%>
<%--            <div id="all-activities">--%>
<%--                <c:forEach items="${activityList}" var="activity">--%>
<%--                    <tr>--%>
<%--                    <td class="tda">${activity.id}</td>--%>
<%--                    <td class="tda">${activity.name}</td>--%>
<%--                    <td class="tda">${activity.startTime}</td>--%>
<%--                    <td class="tda">${activity.endTime}</td>--%>
<%--                    <td class="tda">${activity.descriptionEng}</td>--%>
<%--                    <td class="tda">${activity.descriptionRus}</td>--%>
<%--                    <td class="tda">${activity.typeOfActivity}</td>--%>
<%--                    <c:if test="${activity.createdByUserID.equals(user.id)}">--%>
<%--                        --%>
<%--                        </tr>--%>
<%--                    </c:if>--%>

<%--                </c:forEach>--%>
<%--            </div>--%>

<script src="/project/js/main.js"></script>
</body>
</html>
