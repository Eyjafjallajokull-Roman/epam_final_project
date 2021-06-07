<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="local" var="local"/>
<html>
<head>
    <title>Security Error</title>
    <style>
        <%@include file="style/style.css"%>
    </style>
</head>
<body>
<div class=index_div>
    <h1><fmt:message key="securityError.Not_Allowed" bundle="${local}"/></h1>
    <button class="bigButton" onclick="goBack()">Go Back</button>
</div>
<f:colontitle/>
<script>
    function goBack() {
        window.history.back();
    }
</script>
</body>
</html>
