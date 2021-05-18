<%@ page language="java" contentType="text/html;UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<form name="LoginForm" method="post" action="controller">
    <input type="text" name="login">
    <input type="password" name="password">
    <input type="hidden" name="command" value="login">
    <input type="submit" value="Send">
</form>
</body>
</html>