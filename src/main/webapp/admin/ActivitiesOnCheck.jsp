<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set value="${pageScope.get(\"currentPage\")}" var="currentPage" scope="page"/>
<c:set value="${pageScope.get(\"type\")}" var="type" scope="page"/>
<html>
<head>
    <title>OnCheck</title>
</head>
<body>

<h1>${type}</h1>

<form name="activitiesOnCheck" action="/project/controller" method="get">
    <input type="hidden" name="command" value="activitiesOnCheck">
    <input type="hidden" name="type" value="ON_CHECK">
    <button type="submit">ShowActivitiesOnCheck</button>
</form>

<form name="activitiesOnDelete" action="/project/controller" method="get">
    <input type="hidden" name="command" value="activitiesOnCheck">
    <input type="hidden" name="type" value="ON_DELETE">
    <button type="submit">ShowActivitiesOnDelete</button>
</form>

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
                <%-- add user.Email --%>
        </tr>
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
    </div>
</c:forEach>
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
</body>
</html>

