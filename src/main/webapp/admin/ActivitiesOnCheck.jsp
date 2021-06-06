<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set value="${pageScope.get(\"currentPage\")}" var="currentPage" scope="page"/>
<c:set value="${pageScope.get(\"type\")}" var="type" scope="page"/>
<c:set var="language" value="${not empty param.language ? param.language :
                                not empty language ? language :
                                pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="local" var="local"/>
<html>
<head>
    <title>OnCheck</title>
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
                <a href="adminCabinet.jsp">
                    <%@include file="../icons/load.svg" %>
                </a>
            </div>
        </div>
        <div class="btnAct">
            <form class="menuitem" name="activitiesOnCheck" action="/project/controller" method="get">
                <input type="hidden" name="command" value="activitiesOnCheck">
                <input type="hidden" name="type" value="ON_CHECK">
                <input type="hidden" name="typeParam" value="activity_status.name">
                <button class="addActivity" type="submit">ShowActivitiesOnCheck</button>
            </form>
            <form class="menuitem" name="activitiesOnDelete" action="/project/controller" method="get">
                <input type="hidden" name="command" value="activitiesOnCheck">
                <input type="hidden" name="type" value="ON_DELETE">
                <input type="hidden" name="typeParam" value="activity_status.name">
                <button class="addActivity" type="submit">ShowActivitiesOnDelete</button>
            </form>
            <form class="menuitem" name="activitiesOnDelete" action="/project/controller" method="get">
                <input type="hidden" name="command" value="activitiesOnCheck">
                <input type="hidden" name="type" value="ON_UPDATE">
                <input type="hidden" name="typeParam" value="activity_status.name">
                <button class="addActivity" type="submit">ShowActivitiesOnUpdate</button>
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
        <c:when test="${not empty activities}">
            <div class="tableBlock">
                <table>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Start Time</th>
                        <th>End Time</th>
                        <th>Type of activity</th>
                        <th>Email</th>
                        <th></th>
                    </tr>
                    <c:forEach items="${activities}" var="activity">
                        <input type="hidden" name="activity.id" value="${activity.id}">
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
                            <td class="tda">${userService.findUserById(activity.getCreatedByUserID()).getEmail()}</td>
                            <td>
                                <form action="/project/controller" name="accept" , method="get">
                                    <input type="hidden" name="command" value="AcDecActivity">
                                    <input type="hidden" name="option" value="acceptActivity">
                                    <input type="hidden" name="typeOf" value="${type}">
                                    <input type="hidden" name="id" value="${activity.id}">
                                    <button type="submit">Accept</button>
                                </form>
                                <form action="/project/controller" name="accept" method="get">
                                    <input type="hidden" name="command" value="AcDecActivity">
                                    <input type="hidden" name="option" value="declineActivity">
                                    <input type="hidden" name="typeOf" value="${type}">
                                    <input type="hidden" name="id" value="${activity.id}">
                                    <button type="submit">Decline</button>
                                </form>
                            </td>
                            <c:if test="${not empty activity.oldActivityId && activity.oldActivityId != 0}">
                                <br>
                                <td class="tda"${activityService.findActivityById(activity.oldActivityId).getId()}></td>
                                <td class="tda">${activityService.findActivityById(activity.oldActivityId).getName()}</td>
                                <td class="tda">${activityService.findActivityById(activity.oldActivityId).getStartTime()}</td>
                                <td class="tda">${activityService.findActivityById(activity.oldActivityId).getEndTime()}</td>
                                <td class="tda">${activityService.findActivityById(activity.oldActivityId).getDescriptionEng()}</td>
                                <td class="tda">${activityService.findActivityById(activity.oldActivityId).getTypeOfActivity()}</td>
                            </c:if>
                        </tr>

                    </c:forEach>

                </table>
            </div>


            <div class="pagination">
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <c:if test="${i==currentPage}">
                        <a class="active"
                           href="/project/controller?command=activitiesOnCheck&currentPage=${i}&type=${type}">${i}</a>
                    </c:if>
                    <c:if test="${i!=currentPage}">
                        <a href="/project/controller?command=activitiesOnCheck&currentPage=${i}&type=${type}">${i}</a>
                    </c:if>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
        </c:otherwise>
    </c:choose>
</div>

<f:colontitle/>
<script src="/project/js/language.js"></script>
</body>
</html>

