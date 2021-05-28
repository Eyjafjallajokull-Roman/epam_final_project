package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.Activity;
import com.epam.project.entity.Status;
import com.epam.project.exception.DataBaseConnectionException;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import com.epam.project.service.imp.UserServiceImp;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminSortPageCommand implements Command {
    private static final Logger log = Logger.getLogger(UserSortPageCommand.class);
    private static final List<String> types;
    private static final List<String> typesOfActivity;

    static {
        types = new ArrayList<>();
        typesOfActivity = new ArrayList<>();
        typesOfActivity.add("REMINDER");
        typesOfActivity.add("EVENT");
        typesOfActivity.add("all");
        typesOfActivity.add("TASK");
        types.add("activity.start_time");
        types.add("activity.end_time");
        types.add("activity.name");
    }

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        ActivityService activityService = ServiceFactory.getActivityService();
        HttpSession session = request.getSession();
        int currentPage;
        int totalPages;
        List<Activity> activities;
        UserService userService = new UserServiceImp();
        try {
            session.setAttribute("userService", userService);
            currentPage = request.getParameter("currentPageAdmin") == null ? 1 : Integer.parseInt(request.getParameter("currentPageAdmin"));
            String parameter = types.stream().filter(s -> s.equals(request.getParameter("typeAdmin"))).collect(Collectors.toList()).get(0);
            String typeOfActivity = typesOfActivity.stream().filter(s -> s.equals(request.getParameter("typeActivityAdmin"))).collect(Collectors.toList()).get(0);

            //do methods
            if (typeOfActivity.equals("all")) {
                activities = activityService.findActivitiesByStatusName(Status.ACCEPT.name(), (currentPage - 1) * 5, 5, parameter);
                totalPages = (activityService.calculateActivityNumberByStatusName(Status.ACCEPT.name()) / 5) + 1;
            } else {
                totalPages = (activityService.calculateActivityByTypeOfActivityAndStatusAccepted(typeOfActivity) / 5) + 1;
                activities = activityService.findActivitiesByTypeOfActivityAndStatusAccept(typeOfActivity, (currentPage - 1) * 5, 5, parameter);
            }
            //do methods

            request.setAttribute("typeAdmin", parameter);
            request.setAttribute("typeActivityAdmin", typeOfActivity);
            request.setAttribute("AllActivities", activities);
            request.setAttribute("totalPagesAdmin", totalPages);
            request.setAttribute("currentPageAdmin", currentPage);
            result.setPage(Path.ADMIN_ALL_ACTIVITIES_FWD);

        } catch (NoSuchActivityException | DataBaseConnectionException e) {
            log.error(e);
            result.setPage(Path.ERROR_FWD);

        }
        return result;
    }
}
