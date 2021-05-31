<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<table>
    <tr>
        <th>Name</th>
        <th>SurName</th>
        <th>Email</th>
        <%--for future commands--%>
        <th></th>
    <tr>
        <td>${name}</td>
        <td>${surname}</td>
        <td>${email}</td>
        <td>
            <form class="menuitem" name="pageNextUser" method="get" action="/project/controller">
                <input type="hidden" name="command" value="pageNextFUActivity"/>
                <input type="hidden" name="email" value="${user.email}">
                <button class="menubutton" type="submit">Show Activities</button>
            </form>
        </td>
    </tr>
</table>

</body>
</html>
