package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.entity.User;
import com.epam.project.exception.NoUserException;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private static final Logger logger = Logger.getLogger(LoginCommand.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        logger.debug("Login Command Start");
        HttpSession session = request.getSession();

        //obtain login and password
        String login = request.getParameter("login");
        logger.trace("Request parameter login: " + login);
        String password = request.getParameter("password");

        //error handler
        String errorMessage = null;
        String forward = "/error.jsp";
        //todo error page

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            logger.error("errorMessage: " + errorMessage);
            return forward;
        }
        try {
            User user = ServiceFactory.getUserService().findUser(login, password);
            logger.trace("Found in DB: user --> " + user);
            request.setAttribute("userLogin", login);
            forward = "/cabinet.jsp";

        } catch (NoUserException e) {
            logger.error(e);
            errorMessage = "Cannot find user with such login/password";
            request.setAttribute("errorMessage", errorMessage);
            return forward;
        }

        return forward;
    }
}
