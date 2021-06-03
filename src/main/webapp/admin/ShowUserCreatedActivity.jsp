<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/tagfile.jspf" %>
<html>
<head>
    <title>Title</title>
    <style>
        <%@include file="../style/style.css"%>
        <%@include file="/style/cabinetStyle.css"%>

    </style>
</head>
<body>
<div class="page">
    <div class="header">
        <div class="leftHeader">
            <div class="logo">
                <a href="adminCabinet.jsp"><%@include file="../icons/load.svg" %></a>
            </div>
        </div>
        <div class="btnAct">
        </div>
        <div class="rightHeader">
            <div class="hamburger-menu">
                <input id="menu__toggle" type="checkbox"/>
                <label class="menu__btn" for="menu__toggle">
                    <span></span>
                </label>
                <ul class="menu__box">
                    <li>
                        <form class="menu__item" name="logout" method="post" action="/project/controller">
                            <input type="hidden" name="command" value="logout"/>
                            <button class="langBtn" type="submit">LOGOUT</button>
                        </form>
                    </li>
                    <li>
                        <div class="checkLanguage">
                            <button onclick="myFunction()" class="langBtn">Language</button>
                            <div id="ChangeLanguage" class="languages-list">
                                <select id="language" name="language">
                                    <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
                                    <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
                                </select>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>


    <table>
        <tr>
            <th>Name</th>
            <th>SurName</th>
            <th>Email</th>
            <th>Commands</th>
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
</div>

<f:colontitle/>
<script src="../js/language.js"></script>
</body>
</html>
