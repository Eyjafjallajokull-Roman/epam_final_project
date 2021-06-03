<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<html>
<head>
    <title>Title</title>
    <style>
        <%@include file="style/style.css"%>
    </style>
</head>
<body>


<div id="error">
    <h1 id="error">Error : ${errorMessage} </h1>

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
