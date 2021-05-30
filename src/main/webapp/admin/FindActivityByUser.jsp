<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set value="${pageScope.get(\"currentPageFUA\")}" var="currentPageFUA" scope="page"/>
<c:set value="${pageScope.get(\"typeFUA\")}" var="typeFUA" scope="page"/>
<c:set value="${pageScope.get(userService.findUserById(activity.getCreatedByUserID()).getEmail())}" var="createdId"
       scope="page"/>
<html>
<head>
    <title>Activity List</title>
</head>
<body>
<form action="/project/controller" method="get">
    <input type="hidden" name="command" value="pageNextFUActivity">
    <input type="text" name="email" value="${email}">
    <button type="submit" class="giantbutton">Users</button>
</form>

<c:if test="${not empty email}">
    <form action="/project/controller" method="get">
        <input type="hidden" name="command" value="pageNextFUActivity"/>
        <input type="hidden" name="email" value="${email}">
        <select name="typeFUA">
            <option value="activity.start_time">Start Time</option>
            <option value="activity.end_time">End Time</option>
            <option value="activity.name">Name</option>
        </select>
        <select name="typeActivityFUA">
            <option value="all">All</option>
            <option value="EVENT">Event</option>
            <option value="TASK">Task</option>
            <option value="REMINDER">Reminder</option>
        </select>
        <button type="submit" class="giantbutton">My Activities</button>
    </form>
</c:if>

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
            <td class="tda">${createdId}</td>
            <form>
                <input type="hidden" name="command" value="showCreatedUser"/>
                <input type="hidden" name="email" value="${createdId}">
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
        </tr>
    </div>
</c:forEach>

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

</body>
</html>
