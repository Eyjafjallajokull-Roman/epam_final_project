package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.User;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateUserCommand implements Command {
    private static final Logger log = Logger.getLogger(UpdateUserCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ResultOfExecution result = new ResultOfExecution();
        UserService userService = ServiceFactory.getUserService();
        HttpSession session = request.getSession();
        result.setDirection(Direction.FORWARD);
        String errorMessage;
        try {
            User user = (User) session.getAttribute("user");
            String name = request.getParameter("name");
            String surName = request.getParameter("surname");
            String password = request.getParameter("password");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            if (password == null || password.isEmpty()) {
                user.setName(name);
                user.setSurname(surName);
                updateUser(request, result, userService, user);
            } else {
                if (password.equals(user.getPassword()) && confirmPassword.equals(newPassword) && !password.equals(newPassword)) {
                    user.setName(name);
                    user.setSurname(surName);
                    user.setPassword(newPassword);
                    updateUser(request, result, userService, user);
                } else {
                    log.error("incorrect password");
                    errorMessage = "incorrect password";
                    request.setAttribute("errorMessage", errorMessage);
                    result.setPage(Path.ERROR_FWD);
                }
            }
        } catch (Exception e) {
            log.error(e);
            errorMessage = "Something go wrong";
            request.setAttribute("errorMessage", errorMessage);
            result.setPage(Path.ERROR_FWD);
        }
        return result;
    }

    private void updateUser(HttpServletRequest request, ResultOfExecution result, UserService userService, User user) {
        String errorMessage;
        if (userService.updateUser(user)) {
            result.setDirection(Direction.REDIRECT);
            result.setPage(Path.USER_CABINET);
        } else {
            log.error("user was not found");
            errorMessage = "user was not found";
            request.setAttribute("errorMessage", errorMessage);
            result.setPage(Path.ERROR_FWD);
        }
    }
}
