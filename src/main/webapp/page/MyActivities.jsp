<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set value="${pageScope.get(\"currentPage\")}" var="currentPage" scope="page"/>
<c:set value="${pageScope.get(\"type\")}" var="type" scope="page"/>
<c:set value="${pageScope.get(\"typeActivity\")}" var="typeActivity" scope="page"/>
<c:set value="${sessionScope.get(\"user\")}" var="user" scope="session"/>
<html>
<head>
    <title>MyActivities</title>
</head>
<body>
<h1>My Activities</h1>

<form action="/project/controller" method="get">
    <input type="hidden" name="command" value="pageNext"/>
    <select name="type">
        <option value="activity.start_time">Start Time</option>
        <option value="activity.end_time">End Time</option>
    </select>
    <select name="typeActivity">
        <option value="all">All</option>
        <option value="EVENT">Event</option>
        <option value="TASK">Task</option>
        <option value="REMINDER">Reminder</option>
    </select>
    <button type="submit" class="giantbutton">My Activities</button>
</form>

<c:forEach items="${activities}" var="activity">
    <form name="activity">
    <td class="tda">${activity.id}</td>
    <td class="tda">${activity.name}</td>
    <td class="tda">${activity.startTime}</td>
    <td class="tda">${activity.endTime}</td>
    <td class="tda">${activity.descriptionEng}</td>
    <td class="tda">${activity.descriptionRus}</td>
    <td class="tda">${activity.typeOfActivity}</td>

    <c:if test="${activity.createdByUserID.equals(user.id)}">
        <form class="menuitem" name="deleteActivityUser" method="post" action="/project/controller">
            <input type="hidden" name="command" value="deleteActivityUser"/>
            <input type="hidden" name="idDelete"  value="${activity.id}">
            <button class="menubutton" type="submit">Delete Activity</button>
        </form>
        <form class="menuitem" name="updateActivityPage" method="post" action="/project/controller">
            <input type="hidden" name="command" value="updateActivityPage"/>
            <input type="hidden" name="idUpdate"  value="${activity.id}">
            <button class="menubutton" type="submit">Update Activity</button>
        </form>
        </tr>
    </c:if>
    </form>

</c:forEach>
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
</body>
</html>
