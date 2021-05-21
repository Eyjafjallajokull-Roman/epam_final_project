<%--
  Created by IntelliJ IDEA.
  User: Roman
  Date: 21.05.2021
  Time: 20:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Activity</title>
</head>
<body>
<h1>Create Activity</h1>

<form name="createProduct" method="post" action="/project/controller">
    <input type="hidden" name="command" value="createActivity">
    <input type="text" name="name" placeholder="name">
    <select id="language" name="typeOfActivity">
        <option value="EVENT">Event</option>
        <option value="TASK">Task</option>
        <option value="REMINDER">Reminder</option>
    </select>
    <input type="text" name="description" placeholder="description">
    <input type="datetime-local" name="start_time" placeholder="start">
    <input type="datetime-local" name="end_time" placeholder="end">
</form>

</body>
</html>
