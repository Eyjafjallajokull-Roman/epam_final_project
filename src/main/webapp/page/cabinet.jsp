<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<c:set var="language" value="${not empty param.language ? param.language :
                                not empty language ? language :
                                pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="local" var="local"/>
<html>
<head>
    <title><fmt:message key="cabinet.title" bundle="${local}"/></title>
</head>
<body>
<h3>
    ${userLogin}
</h3>

<form class="menuitem" name="logout" method="post" action="/project/controller">
    <input type="hidden" name="command" value="logout"/>
    <button class="menubutton" type="submit">LOGOUT</button>
</form>



<form class="menuitem" name="createActivityPage" method="post" action="/project/controller">
    <input type="hidden" name="command" value="createActivityPage"/>
    <button class="menubutton" type="submit">Create Activity</button>
</form>

<form class="menuitem" name="updateActivityPage" method="post" action="/project/controller">
    <input type="hidden" name="command" value="updateActivityPage"/>
    <button class="menubutton" type="submit">Update Activity</button>
</form>


<form name="all-activities" method="post" action="/project/controller">
    <input type="hidden" name="command" value="all-activities"/>
    <button class="menubutton" type="submit">Show Activities</button>
</form>

<form name="updateUserPage" method="post" action="/project/controller">
    <input type="hidden" name="command" value="updateUserPage"/>
    <button class="menubutton" type="submit">Update</button>
</form>


<h3>All products:</h3>
<div id="all-activities"></div>

${activities}

<form class="topcorner" method="post">
    <select id="language" name="language" onchange="submit()">
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
    </select>
</form>
<f:colontitle/>
</body>
</html>
