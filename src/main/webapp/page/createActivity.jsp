<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set value="${requestScope.get(\"type\")}" var="type" scope="request"/>
<html>
<head>
    <title>Create Activity</title>
</head>
<body>
<h1>Create Activity</h1>
<h1> ${type} </h1>

<c:if test="${type=='create'}">
    <form name="createProduct" method="post" action="/project/controller">
        <input type="hidden" name="command" value="createActivity">
        <input type="text" name="name" placeholder="name">
        <select id="type" name="typeOfActivity">
            <option value="EVENT">Event</option>
            <option value="TASK">Task</option>
            <option value="REMINDER">Reminder</option>
        </select>
        <input type="text" name="description_en" placeholder="description_en">
        <input type="text" name="description_ru" placeholder="description_ru">
        <input type="datetime-local" name="start_time" placeholder="start">
        <input type="datetime-local" name="end_time" placeholder="end">
        <input type="submit" value="AddNewActivity">
    </form>
</c:if>

<c:if test="${type=='update'}">
    <form name="updateActivity" method="post" action="/project/controller">
        <input type="hidden" name="command" value="updateActivity">
        <input type="text" name="name" placeholder="name">
        <select name="typeOfActivity">
            <option value="EVENT">Event</option>
            <option value="TASK">Task</option>
            <option value="REMINDER">Reminder</option>
        </select>
        <input type="text" name="description" placeholder="description">
        <input type="datetime-local" name="start_time" placeholder="start">
        <input type="datetime-local" name="end_time" placeholder="end">
        <input type="submit" value="updateActivity">
    </form>
</c:if>

<f:colontitle/>
</body>
</html>
