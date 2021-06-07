<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set var="language" value="${not empty param.language ? param.language :
                                not empty language ? language :
                                pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="local" var="local"/>
<html>
<head>
    <title><fmt:message key="decAct.title" bundle="${local}"/></title>
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
    <c:if test="${not empty activities}">
        <div class="tableBlock">
            <table>
                <tr>
                    <th><fmt:message key="tableA.name" bundle="${local}"/></th>
                    <th><fmt:message key="tableA.Description" bundle="${local}"/></th>
                    <th><fmt:message key="tableA.StartTime" bundle="${local}"/></th>
                    <th><fmt:message key="tableA.EndTime" bundle="${local}"/></th>
                    <th><fmt:message key="tableA.TypeOfActivity" bundle="${local}"/></th>
                    <th><fmt:message key="tableA.command" bundle="${local}"/></th>
                </tr>
                <c:forEach items="${activities}" var="activity">

                    <form name="activity">
                        <tr>
                            <td class="tda">${activity.name}</td>
                            <c:choose>
                                <c:when test="${language == 'ru_RU' || language== 'ru'}">
                                    <td>${activity.descriptionRus}</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${activity.descriptionEng}</td>
                                </c:otherwise>
                            </c:choose>
                            <td>${activity.startTime}</td>
                            <td>${activity.endTime}</td>
                            <td class="tda">${activity.typeOfActivity}</td>
                            <td>
                                <form class="menuitem" name="updateActivityPage" method="post"
                                      action="/project/controller">
                                    <input type="hidden" name="command" value="updateActivityPage"/>
                                    <input type="hidden" name="idUpdate" value="${activity.id}">
                                    <button class="btnEdit" type="submit"><img style="width: 30px"
                                                                               src="/project/icons/edit.svg"
                                                                               alt=""/></button>
                                </form>
                                <form class="menuitem" name="deleteActivity" method="post"
                                      action="/project/controller">
                                    <input type="hidden" name="command" value="deleteActivityAdmin"/>
                                    <input type="hidden" name="idDelete" value="${activity.id}">
                                    <button class="btnEdit" type="submit"><img style="width: 30px"
                                                                               src="/project/icons/del.svg"
                                                                               alt=""/></button>
                                </form>
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </table>
        </div>


        <div class="pagination">
            <c:forEach var="i" begin="1" end="${totalPages}">
                <c:if test="${i==currentPage}">
                    <a class="active"
                       href="/project/controller?command=showDeclinedActivities&currentPage=${i}">${i}</a>
                </c:if>
                <c:if test="${i!=currentPage}">
                    <a href="/project/controller?command=showDeclinedActivities&currentPage=${i}">${i}</a>
                </c:if>
            </c:forEach>
        </div>
    </c:if>
</div>
<f:colontitle/>
<script src="/project/js/language.js"></script>
</body>
</html>
