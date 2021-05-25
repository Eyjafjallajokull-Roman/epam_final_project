<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set value="${pageScope.get(\"currentPageAdmin\")}" var="currentPageAdmin" scope="page"/>
<c:set value="${pageScope.get(\"typeAdmin\")}" var="typeAdmin" scope="page"/>
<c:set value="${pageScope.get(\"typeActivityAdmin\")}" var="typeActivityAdmin" scope="page"/>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="/project/controller" method="get">
    <input type="hidden" name="command" value="pageNextAdmin"/>
    <select name="typeAdmin">
        <option value="activity.start_time">Start Time</option>
        <option value="activity.end_time">End Time</option>
        <option value="activity.name">Name</option>
    </select>
    <select name="typeActivityAdmin">
        <option value="all">All</option>
        <option value="EVENT">Event</option>
        <option value="TASK">Task</option>
        <option value="REMINDER">Reminder</option>
    </select>
    <button type="submit" class="giantbutton">My Activities</button>
</form>


<c:forEach items="${AllActivities}" var="activity">
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
        </tr>
    </div>
</c:forEach>


<div class="pagination">
    <c:forEach var="i" begin="1" end="${totalPagesAdmin}">
        <c:if test="${i==currentPageAdmin}">
            <a class="active"
               href="/project/controller?command=pageNextAdmin&currentPageAdmin=${i}&typeAdmin=${typeAdmin}&typeActivityAdmin=${typeActivityAdmin}">${i}</a>
        </c:if>
        <c:if test="${i!=currentPageAdmin}">
            <a href="/project/controller?command=pageNextAdmin&currentPageAdmin=${i}&typeAdmin=${typeAdmin}&typeActivityAdmin=${typeActivityAdmin}">${i}</a>
        </c:if>
    </c:forEach>
</div>
</body>
</html>
