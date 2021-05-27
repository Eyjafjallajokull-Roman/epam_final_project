package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.Activity;
import com.epam.project.entity.User;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.exception.NoUserException;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUserToActivity implements Command {
    private static final Logger log = Logger.getLogger(AddUserToActivity.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getHeader("referer").replaceFirst("http://localhost:8080", "");
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        UserService userService = ServiceFactory.getUserService();
        ActivityService activityService = ServiceFactory.getActivityService();
        String errorMessage;
        try {
            String email = request.getParameter("userEmail");
            Integer activityId = Integer.valueOf(request.getParameter("activityToInsert"));
            User user = userService.findUserByLogin(email);
            Activity activity = activityService.findActivityById(activityId);
            if (userService.addUserToActivity(activity, user)) {
                System.out.println("Я тут");
                result.setPage(url);
                result.setDirection(Direction.REDIRECT);
            } else {
                errorMessage = "This User already in Activity ";
                request.setAttribute("errorMessage", errorMessage);
                result.setPage(Path.ERROR_FWD);
            }
        } catch (NoUserException | NoSuchActivityException | NullPointerException e) {
            System.out.println("error");
            log.error(e);
            errorMessage = "No User/ No Such Activity";
            request.setAttribute("errorMessage", errorMessage);
            result.setPage(Path.ERROR_FWD);
        }

        return result;
    }
}
