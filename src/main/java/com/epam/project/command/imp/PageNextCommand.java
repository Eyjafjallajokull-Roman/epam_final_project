package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.Activity;
import com.epam.project.entity.User;
import com.epam.project.exception.DataBaseConnectionException;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PageNextCommand implements Command {
    private static final Logger log = Logger.getLogger(LogoutCommand.class);
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
        try {
            User user = (User) session.getAttribute("user");

            String parameter = types.stream().filter(s -> s.equals(request.getParameter("type"))).collect(Collectors.toList()).get(0);
            String typeOfActivity = typesOfActivity.stream().filter(s -> s.equals(request.getParameter("typeActivity"))).collect(Collectors.toList()).get(0);
            //set current page
            if (request.getParameter("currentPage") == null) {
                currentPage = 1;
            } else {
                currentPage = Integer.parseInt(request.getParameter("currentPage"));
            }
//set how to sort
            if (typeOfActivity.equals("all")) {
                activities = activityService.findAllFromTo(user.getId(), (currentPage - 1) * 5, 5);
                totalPages = (activityService.calculateActivityNumberWithCondition(String.valueOf(user.getId())) / 5) + 1;
                System.out.println(totalPages + " if");
            } else {
                totalPages = (activityService.calculateActivityWithConditionAndWhereParam(typeOfActivity, String.valueOf(user.getId())) / 5) + 1;
                activities = activityService.findAllFromToWithWhereParam((currentPage - 1) * 5, 5, typeOfActivity, String.valueOf(user.getId()));
            }
            request.setAttribute("type", parameter);
            request.setAttribute("typeActivity", typeOfActivity);
            request.setAttribute("activities", activities);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", currentPage);
            result.setPage(Path.MY_ACTIVITIES_PAGE_FWD);

        } catch (NoSuchActivityException | DataBaseConnectionException e) {
            log.error(e);
            result.setPage(Path.ERROR_FWD);

        }
        return result;
    }


}
