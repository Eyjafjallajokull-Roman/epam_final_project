package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.ErrorConfig;
import com.epam.project.constants.ErrorConst;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.User;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import com.epam.project.PasswordEncoder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateUserCommand implements Command {
    private static final Logger log = Logger.getLogger(UpdateUserCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ResultOfExecution result = new ResultOfExecution();
        ErrorConfig error = ErrorConfig.getInstance();
        HttpSession session = request.getSession();
        result.setDirection(Direction.FORWARD);
        try {
            UserService userService = ServiceFactory.getUserService();
            User user = (User) session.getAttribute("user");
            String name = request.getParameter("name").trim();
            String surName = request.getParameter("surname").trim();
            String password = (request.getParameter("password").trim());

            //if user don`t type password
            if (password == null || password.isEmpty()) {
                user.setName(name);
                user.setSurname(surName);
                if (userService.updateUserWithoutEmail(user)) {
                    result.setDirection(Direction.REDIRECT);
                    result.setPage(Path.USER_CABINET);
                } else {
                    log.error("User was not found");
                    request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.UNABLE_TO_FOUND_USER));
                    result.setPage(Path.ERROR_FWD);
                }
            } else {
                String newPassword = (request.getParameter("newPassword").trim());
                String confirmPassword = (request.getParameter("confirmPassword").trim());

                if (PasswordEncoder.hashPassword(password).equals(user.getPassword()) && confirmPassword.equals(newPassword)
                        && !PasswordEncoder.hashPassword(password).equals(PasswordEncoder.hashPassword(newPassword))) {
                    user.setName(name);
                    user.setSurname(surName);
                    user.setPassword(newPassword);
                    if (userService.updateUser(user)) {
                        result.setDirection(Direction.REDIRECT);
                        result.setPage(Path.USER_CABINET);
                    } else {
                        log.error("User was not found");
                        request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.UNABLE_TO_FOUND_USER));
                        result.setPage(Path.ERROR_FWD);
                    }
                } else {
                    log.error("incorrect password");
                    request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.INCORRECT_PASSWORD));
                    result.setPage(Path.ERROR_FWD);
                }
            }
        } catch (NullPointerException e) {
            log.error(e);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.Data_WAS_NOT_FOUND));
            result.setPage(Path.ERROR_FWD);
        }
        return result;
    }


}
