package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.Path;
import com.epam.project.entity.Role;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.User;
import com.epam.project.exception.NoUserException;
import com.epam.project.exception.WrongPasswordExeption;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import com.epam.project.util.PasswordEncoder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private static final Logger logger = Logger.getLogger(LoginCommand.class);


    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {

        logger.debug("Login Command Start");
        HttpSession session = request.getSession();
        ResultOfExecution resultOfExecution = new ResultOfExecution();
        resultOfExecution.setDirection(Direction.FORWARD);
        String email = request.getParameter("email");
        logger.trace("Request parameter login: " + email);
        String password = request.getParameter("password");
        ActivityService activityService = ServiceFactory.getActivityService();
        UserService userService = ServiceFactory.getUserService();


        //error handler
        String errorMessage = null;



        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            logger.error("errorMessage: " + errorMessage);
            resultOfExecution.setPage(Path.ERROR_FWD);
            return resultOfExecution;
        }
        try {
            String hashPassword = PasswordEncoder.hashPassword(password);
            User user = ServiceFactory.getUserService().findUser(email, hashPassword);
            session.setAttribute("user", user);
            session.setAttribute("userService", userService);
            session.setAttribute("activityService", activityService);

            if (user.getRole() == Role.CLIENT) {
                resultOfExecution.setDirection(Direction.REDIRECT);
                resultOfExecution.setPage(Path.USER_CABINET);
            } else {
                resultOfExecution.setDirection(Direction.REDIRECT);
                resultOfExecution.setPage(Path.ADMIN_CABINET);
            }

        } catch (NoUserException | WrongPasswordExeption e) {
            logger.error(e);
            errorMessage = "Cannot find user with such login/password";
            request.setAttribute("errorMessage", errorMessage);
            resultOfExecution.setPage(Path.ERROR_FWD);
            return resultOfExecution;
        }

        return resultOfExecution;
    }
}
