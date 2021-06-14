<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set value="${pageScope.get(\"currentPageU\")}" var="currentPageU" scope="page"/>
<c:set value="${pageScope.get(\"typeU\")}" var="typeU" scope="page"/>
<c:set var="language" value="${not empty param.language ? param.language :
                                not empty language ? language :
                                pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="local" var="local"/>
<html>
<head>
    <title>All User</title>
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
                <input type="hidden" name="command" value="pageNextUser"/>
                <select name="typeU">
                    <option value="user.name" ${typeU == "user.name" ? 'selected' : ''}>Name</option>
                    <option value="user.surname"${typeU == "user.surname" ? 'selected' : ''}>Surname</option>
                </select>
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
    <%--    <form action="/project/controller" method="get">--%>
    <%--        <input type="hidden" name="command" value="pageNextUser"/>--%>
    <%--        <select name="typeU">--%>
    <%--            <option value="user.name" ${typeU == "user.name" ? 'selected' : ''}>Name</option>--%>
    <%--            <option value="user.surname"${typeU == "user.surname" ? 'selected' : ''}>Surname</option>--%>
    <%--        </select>--%>
    <%--        <button type="submit" class="giantbutton">Users</button>--%>
    <%--    </form>--%>
    <c:choose>
        <c:when test="${not empty users}">
            <div>
                <table class="tableBlock">
                    <tr>
                        <th><fmt:message key="tableA.name" bundle="${local}"/></th>
                        <th><fmt:message key="register.surname" bundle="${local}"/></th>
                        <th>Email</th>
                        <th><fmt:message key="tableA.command" bundle="${local}"/></th>
                    </tr>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td class="tda">${user.name}</td>
                            <td class="tda">${user.surname}</td>
                            <td class="tda">${user.email}</td>
                            <td>
                                <form class="menuitem" name="pageNextUser" method="get" action="/project/controller">
                                    <input type="hidden" name="command" value="pageNextFUActivity"/>
                                    <input type="hidden" name="email" value="${user.email}">
                                    <button class="menubutton" type="submit">Show Activities</button>
                                </form>

                                <form class="menuitem" name="showActivitiesByCreatedId" method="get"
                                      action="/project/controller">
                                    <input type="hidden" name="command" value="showActivitiesByCreatedId"/>
                                    <input type="hidden" name="createdBy" value="${user.email}">
                                    <button class="menubutton" type="submit">Show Created Activities</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>


            <div class="pagination">
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <c:if test="${i==currentPageU}">
                        <a class="active"
                           href="/project/controller?command=pageNextUser&currentPageU=${i}&type=${typeU}">${i}</a>
                    </c:if>
                    <c:if test="${i!=currentPageU}">
                        <a href="/project/controller?command=pageNextUser&currentPageU=${i}&typeU=${typeU}">${i}</a>
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
