package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.Path;
import com.epam.project.entity.Activity;
import com.epam.project.entity.Role;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.User;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RegisterCommand implements Command {
    private static final Logger log = Logger.getLogger(RegisterCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.REDIRECT);
        HttpSession session = request.getSession();
        String errorMessage;
        ActivityService activityService = ServiceFactory.getActivityService();
        UserService userService = ServiceFactory.getUserService();

        try {
            session.setAttribute("userService", userService);
            session.setAttribute("activityService", activityService);
            String email = request.getParameter("email");
            String name = request.getParameter("name");
            String last = request.getParameter("lastName");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setName(name);
            user.setSurname(last);
            user.setRole(Role.CLIENT);
            userService = ServiceFactory.getUserService();
            if (confirmPassword.equals(password)) {
                if (userService.addUser(user)) {
                    session.setAttribute("userLogin", email);
                    session.setAttribute("user", user);
                } else {
                    errorMessage = "Not Valid Data";
                    request.setAttribute("errorMessage", errorMessage);
                    result.setPage(Path.ERROR_FWD);
                }
            } else {
                errorMessage = "Password mismatch";
                request.setAttribute("errorMessage", errorMessage);
                result.setPage(Path.ERROR_FWD);
            }

        } catch (Exception e) {
            errorMessage = "Unexpected exception";
            request.setAttribute("errorMessage", errorMessage);
            result.setPage(Path.ERROR);
        }

        return result;
    }
}
