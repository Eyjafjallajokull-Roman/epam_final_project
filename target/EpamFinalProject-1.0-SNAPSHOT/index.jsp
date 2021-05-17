<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<div class="login-page">
    <div class="form">
        <form class="register-form">
            <input class="firstName" name="firstName" type="text" placeholder="first name"/>
            <input class="lastName" name="lastName" type="text" placeholder="last name"/>
            <input class="email" name="email" type="email" placeholder="email address"/>
            <input class="password" name="password" type="password" placeholder="password"/>
            <input class="confirmPassword" name="confirmPassword" type="password" placeholder="confirm password"/>
            <button type="button" id="register">Register</button>
            <p class="message">Already registered? <a href="#">Sign In</a></p>
        </form>
        <form class="login-form">
            <input class="email" name="email" type="email" placeholder="email address"/>
            <input class="password" name="password" type="password" placeholder="password"/>
            <button type="button" id="login">Login</button>
            <p class="message">Not registered? <a href="#">Create an account</a></p>
        </form>
    </div>
</div>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>