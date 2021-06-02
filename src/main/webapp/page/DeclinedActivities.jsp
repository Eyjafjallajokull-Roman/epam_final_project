<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<html>
<head>
    <title>Declined Activities</title>
</head>
<body>

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
<c:forEach items="${activities}" var="activity">

        <form name="activity">
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
                <td>${activity.startTime}</td>
                <td>${activity.endTime}</td>
                <td class="tda">${activity.typeOfActivity}</td>
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

</body>
</html>
