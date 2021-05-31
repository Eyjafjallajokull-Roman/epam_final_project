<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:forEach items="${activities}" var="activity">
    <div>
        <tr>
            <td class="tda"${activity.id}></td>
            <td class="tda">${activity.name}</td>
            <td class="tda">${activity.startTime}</td>
            <td class="tda">${activity.endTime}</td>
            <td class="tda">${activity.descriptionEng}</td>
            <td class="tda">${activity.descriptionRus}</td>
            <td class="tda">${activity.typeOfActivity}</td>
            <td class="tda">${userService.findUserById(activity.getCreatedByUserID()).getEmail()}</td>

            <form class="menuitem" name="updateActivityPage" method="post" action="/project/controller">
                <input type="hidden" name="command" value="addUserToActivity"/>
                <input type="hidden" name="activityToInsert" value="${activity.id}">
                <input type="text" name="userEmail" placeholder="User Email">
                <button class="menubutton" type="submit">Add User</button>
            </form>

            <form class="menuitem" name="pageNextUser" method="get" action="/project/controller">
                <input type="hidden" name="command" value="pageNextFUA"/>
                <input type="hidden" name="activityIdFUA" value="${activity.id}">
                <button class="menubutton" type="submit">Show Users</button>
            </form>
        </tr>
    </div>
</c:forEach>
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
</body>
</html>
