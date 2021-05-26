package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.Activity;
import com.epam.project.entity.User;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


public class ShowAllActivitiesByUser implements Command {
    public static final Logger log = Logger.getLogger(ShowAllActivitiesByUser.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        HttpSession session = request.getSession();
        String errorMessage = null;
        try {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                List<Activity> activityList = ServiceFactory.getActivityService().findAllActivitiesByCreatedId(user.getId());
                request.setAttribute("activities", activityList);
                result.setPage(Path.USER_CABINET_FWD);
                System.out.println("cool");
            }

        } catch (Exception e) {
            log.error(e);
            errorMessage = "something go wrong";
            result.setPage(Path.ERROR_FWD);
            request.setAttribute("errorMessage", errorMessage);
        }
        return result;
    }
}
