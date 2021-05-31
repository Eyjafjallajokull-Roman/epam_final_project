package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.ErrorConfig;
import com.epam.project.constants.ErrorConst;
import com.epam.project.constants.Path;
import com.epam.project.entity.Activity;
import com.epam.project.entity.Role;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.User;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import com.epam.project.taghandler.PasswordEncoder;
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
        result.setDirection(Direction.FORWARD);
        HttpSession session = request.getSession();
        ErrorConfig error = ErrorConfig.getInstance();


        try {
            ActivityService activityService = ServiceFactory.getActivityService();
            UserService userService = ServiceFactory.getUserService();
            session.setAttribute("userService", userService);
            session.setAttribute("activityService", activityService);
            String email = request.getParameter("email");
            String name = request.getParameter("name").trim();
            String last = request.getParameter("lastName").trim();
            String password = (request.getParameter("password"));
            String confirmPassword = (request.getParameter("confirmPassword"));


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
                    result.setPage(Path.USER_CABINET);
                    result.setDirection(Direction.REDIRECT);
                } else {
                    request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.Data_WAS_NOT_FOUND));
                    result.setPage(Path.ERROR_FWD);
                }
            } else {
                request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.PASSWORD_MISMATCH));
                result.setPage(Path.ERROR_FWD);
            }

        } catch (NullPointerException e) {
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.Data_WAS_NOT_FOUND));
            result.setPage(Path.ERROR_FWD);
        }

        return result;
    }
}
