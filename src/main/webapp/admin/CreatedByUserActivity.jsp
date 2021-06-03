<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                <a href="adminCabinet.jsp"><%@include file="../icons/load.svg" %></a>
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
    <c:choose>
        <c:when test="${not empty activities}">
            <div>
                <table>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Start Time</th>
                        <th>End Time</th>
                        <th>Type of activity</th>
                        <th>UserEmail</th>
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
                                <td class="tda">${userService.findUserById(activity.getCreatedByUserID()).getEmail()}</td>
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
                           href="/project/controller?command=showActivitiesByCreatedId&currentPage=${i}&createdBy=${createdBy}">${i}</a>
                    </c:if>
                    <c:if test="${i!=currentPage}">
                        <a href="/project/controller?command=showActivitiesByCreatedId&currentPage=${i}&createdBy=${createdBy}">${i}</a>
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
