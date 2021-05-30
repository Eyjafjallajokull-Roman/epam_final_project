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
import java.util.stream.Collectors;

public class UserSortPageCommand implements Command {
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
        types.add("start_time");
        types.add("end_time");
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
        try {
            User user = (User) session.getAttribute("user");
            currentPage = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
            String parameter = types.stream().filter(s -> s.equals(request.getParameter("type"))).collect(Collectors.toList()).get(0);
            String typeOfActivity = typesOfActivity.stream().filter(s -> s.equals(request.getParameter("typeActivity"))).collect(Collectors.toList()).get(0);

            if (typeOfActivity.equals("all")) {
                activities = activityService.findActivitiesWhereCreatedIdWithLimit(String.valueOf(user.getId()), (currentPage - 1) * 5, 5, parameter);
                totalPages = (activityService.calculateActivityNumberWithCreatedByIdCondition(user.getId()) / 5) + 1;
                System.out.println(totalPages);
            } else {
                totalPages = (activityService.calculateActivityNumberWithCreatedByIdConditionAndTypeActivity(user.getId(), typeOfActivity) / 5) + 1;
                activities = activityService.findAllActivityByCreatedIdAndTypeActivity((currentPage - 1) * 5, 5, String.valueOf(user.getId()), typeOfActivity, parameter);

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
