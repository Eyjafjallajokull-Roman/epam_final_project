<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<form class="register-form" action="/project/controller" method="post">
    <input class="firstName" name="name" type="text" placeholder="first name"/>
    <input class="lastName" name="lastName" type="text" placeholder="last name"/>
    <input class="email" name="email" type="email" placeholder="email address"/>
    <input class="password" name="password" type="password" placeholder="password"/>
    <input class="confirmPassword" name="confirmPassword" type="password" placeholder="confirm password"/>
    <input type="hidden" name="command" value="register">
    <input type="submit" value="Register"/>
</form>
</body>
</html>
