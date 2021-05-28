<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set value="${pageScope.get(\"currentPageU\")}" var="currentPageU" scope="page"/>
<c:set value="${pageScope.get(\"typeU\")}" var="typeU" scope="page"/>
<html>
<head>
    <title>AllUser</title>
</head>
<body>
<h1>All Users</h1>

<form action="/project/controller" method="get">
    <input type="hidden" name="command" value="pageNextUser"/>
    <select name="typeU">
        <option value="user.name">Name</option>
        <option value="user.surname">Surname</option>
    </select>
    <button type="submit" class="giantbutton">Users</button>
</form>

<c:forEach items="${users}" var="user">
    <form name="user">
        <tr>
            <td class="tda">${user.name}</td>
            <td class="tda">${user.surname}</td>
            <td class="tda">${user.email}</td>
        </tr>
    </form>

    <form class="menuitem" name="pageNextUser" method="get" action="/project/controller">
        <input type="hidden" name="command" value="pageNextFUActivity"/>
        <input type="hidden" name="email" value="${user.email}">
        <button class="menubutton" type="submit">Show Activities</button>
    </form>

</c:forEach>
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


</body>
</html>
