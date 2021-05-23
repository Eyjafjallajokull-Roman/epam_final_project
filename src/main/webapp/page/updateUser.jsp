
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set value="${sessionScope.get(\"user\")}" var="user" scope="session" />
<html>
<head>
    <title>Update</title>
</head>
<body>

<form name="updateUser" action="/project/controller" method="post">
    <input type="hidden" name="command" value="updateUser">
    <input name="name" type="text" placeholder="first name" value="${user.name}">
    <input name="surname" type="text" placeholder="lastname" value="${user.surname}">
    <%--
добавити кнопку яка буде відкривти додатково ще і зміну пароля
    --%>
    <input name="password" type="password" placeholder="oldPassword">
    <input name="newPassword" type="password" placeholder="New Password">
    <input name="confirmPassword" type="password" placeholder="confirmPassword">
    <input type="submit" value="UpdateUser">
</form>





<f:colontitle/>
</body>
</html>
