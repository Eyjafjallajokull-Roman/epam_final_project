<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Create Activity</title>
</head>
<body>
<h1>Create Activity</h1>


<form name="createProduct" method="post" action="/project/controller">
    <input type="hidden" name="command" value="createActivity">
    <input type="text" name="name" placeholder="name">
    <select id="type" name="typeOfActivity">
        <option value="EVENT">Event</option>
        <option value="TASK">Task</option>
        <option value="REMINDER">Reminder</option>
    </select>
    <input type="text" name="description" placeholder="description">
    <input type="datetime-local" name="start_time" placeholder="start">
    <input type="datetime-local" name="end_time" placeholder="end">
    <input type="submit" value="AddNewActivity">
</form>


<%--<form name="updateActivity" method="post" action="/project/controller">--%>
<%--    <input type="hidden" name="command" value="updateActivity">--%>
<%--    <input type="text" name="name" placeholder="name">--%>
<%--    <select name="typeOfActivity">--%>
<%--        <option value="EVENT">Event</option>--%>
<%--        <option value="TASK">Task</option>--%>
<%--        <option value="REMINDER">Reminder</option>--%>
<%--    </select>--%>
<%--    <input type="text" name="description" placeholder="description">--%>
<%--    <input type="datetime-local" name="start_time" placeholder="start">--%>
<%--    <input type="datetime-local" name="end_time" placeholder="end">--%>
<%--    <input type="submit" value="updateActivity">--%>
<%--</form>--%>


</body>
</html>
