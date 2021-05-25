package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.Activity;
import com.epam.project.exception.DataBaseConnectionException;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AdminActivitiesOnCheckCommand implements Command {
    private static final Logger logger = Logger.getLogger(AdminActivitiesOnCheckCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        ActivityService activityService = ServiceFactory.getActivityService();
        UserService userService = ServiceFactory.getUserService();
        int currentPage;
        int totalPages;
        String param = request.getParameter("type");
        List<Activity> activities;

        try {


            currentPage = (request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage")));
            activities = activityService.findActivitiesByStatusName(param, (currentPage - 1) * 5, 5);
            totalPages = (activityService.calculateActivityNumberByStatusName(param) / 5) + 1;


            request.setAttribute("userService", ServiceFactory.getUserService());
//            request.setAttribute("users", users);
            request.setAttribute("activities", activities);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("type", param);
            result.setPage(Path.ADMIN_ACTIVITIES_ON_CHECK_FWD);
        } catch (NoSuchActivityException | DataBaseConnectionException e) {
            logger.error(e);
            result.setPage(Path.ERROR_FWD);
        }
        return result;
    }
}
