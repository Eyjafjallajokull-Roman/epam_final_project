<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set value="${pageScope.get(\"currentPageFUA\")}" var="currentPageFUA" scope="page"/>
<c:set value="${pageScope.get(\"typeFUA\")}" var="typeFUA" scope="page"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/project/controller" method="get">
    <input type="hidden" name="command" value="pageNextFUA">
    <input type="text" name="activityIdFUA" value="activityIdFUA">
    <button type="submit" class="giantbutton">Users</button>
</form>

<c:if test="${not empty activityIdFUA}">
    <form action="/project/controller" method="get">
        <input type="hidden" name="command" value="pageNextFUA">
        <input type="hidden" name="activityIdFUA" value="${activityIdFUA}">
        <select name="typeFUA">
            <option value="user.name">Name</option>
            <option value="user.surname">Surname</option>
        </select>
        <button type="submit" class="giantbutton">Users</button>
    </form>
</c:if>


<c:forEach items="${users}" var="user">
    <form name="user">
        <tr>
            <td class="tda">${user.name}</td>
            <td class="tda">${user.surname}</td>
            <td class="tda">${user.email}</td>
        </tr>
    </form>
    <c:if test="${not empty activityIdFUA}">
        <form>
            <input type="hidden" name="command" value="deleteUserFromActivity"/>
            <input type="hidden" name="activityIdFUA" value="${activityIdFUA}">
            <input type="hidden" name="email" value="${user.email}">
            <button type="submit">DeleteUserFromActivity</button>
        </form>
    </c:if>
</c:forEach>
<div class="pagination">
    <c:forEach var="i" begin="1" end="${totalPagesFUA}">
        <c:if test="${i==currentPageFUA}">
            <a class="active"
               href="/project/controller?command=pageNextFUA&activityIdFUA=${activityIdFUA}&currentPageFUA=${i}&typeFUA=${typeFUA}">${i}</a>
        </c:if>
        <c:if test="${i!=currentPageFUA}">
            <a href="/project/controller?command=pageNextFUA&currentPageFUA=${i}&activityIdFUA=${activityIdFUA}&typeFUA=${typeFUA}">${i}</a>
        </c:if>
    </c:forEach>
</div>
</body>
</html>
