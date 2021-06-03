package com.epam.project.command.imp;

import com.epam.project.CheckRole;
import com.epam.project.command.Command;
import com.epam.project.constants.ErrorConfig;
import com.epam.project.constants.ErrorConst;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.Activity;
import com.epam.project.entity.Role;
import com.epam.project.entity.Status;
import com.epam.project.entity.User;
import com.epam.project.exception.DataBaseConnectionException;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.exception.NoUserException;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import com.epam.project.service.imp.UserServiceImp;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        typesOfActivity.add("TIME_TRACKER");
        types.add("activity.start_time");
        types.add("activity.end_time");
        types.add("activity.name");
        types.add("activity.users");
    }

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        HttpSession session = request.getSession();
        ErrorConfig error = ErrorConfig.getInstance();
        if (!CheckRole.checkRole(session, Role.ADMIN)) {
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.ERROR_ADMIN));
            result.setPage(Path.ADMIN_ERROR_FWD);
            return result;
        }

        try {
            ActivityService activityService = ServiceFactory.getActivityService();
            int currentPage;
            int totalPages;
            List<Activity> activities;
            UserService userService = new UserServiceImp();
            session.setAttribute("userService", userService);
            currentPage = request.getParameter("currentPageAdmin") == null ? 1 : Integer.parseInt(request.getParameter("currentPageAdmin"));
            String parameter = types.stream().filter(s -> s.equals(request.getParameter("typeAdmin"))).collect(Collectors.toList()).get(0);
            String typeOfActivity = typesOfActivity.stream().filter(s -> s.equals(request.getParameter("typeActivityAdmin"))).collect(Collectors.toList()).get(0);

            if (parameter.equals("activity.users")) {
                if (typeOfActivity.equals("all")) {
                    activities = activityService.findAllActivityByStatsOrderWithoutLimit(Status.ACCEPT.name(), "activity.name");
                    totalPages = (activityService.calculateActivityNumberByStatusName(Status.ACCEPT.name()) / 5) + 1;
                } else {
                    activities = activityService.findAllActivityByTypeOfActivityAndStatusOrderWithoutLimit(typeOfActivity, "activity.name");
                    totalPages = (activityService.calculateActivityByTypeOfActivityAndStatusAccepted(typeOfActivity) / 5) + 1;
                }
                activities = sortByNumberOfUsers(currentPage, activities);

            } else {

                if (typeOfActivity.equals("all")) {
                    activities = activityService.findActivitiesByStatusName(Status.ACCEPT.name(), (currentPage - 1) * 5, 5, parameter);
                    totalPages = (activityService.calculateActivityNumberByStatusName(Status.ACCEPT.name()) / 5) + 1;
                } else {
                    totalPages = (activityService.calculateActivityByTypeOfActivityAndStatusAccepted(typeOfActivity) / 5) + 1;
                    activities = activityService.findActivitiesByTypeOfActivityAndStatusAccept(typeOfActivity, (currentPage - 1) * 5, 5, parameter);
                }

            }


            request.setAttribute("typeAdmin", parameter);
            request.setAttribute("typeActivityAdmin", typeOfActivity);
            request.setAttribute("AllActivities", activities);
            request.setAttribute("totalPagesAdmin", totalPages);
            request.setAttribute("currentPageAdmin", currentPage);
            result.setPage(Path.ADMIN_ALL_ACTIVITIES_FWD);

        } catch (NoSuchActivityException e) {
            log.error(e);
            result.setPage(Path.ERROR_FWD);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.NO_SUCH_ACTIVITY));

        } catch (DataBaseConnectionException e) {
            log.error(e);
            result.setPage(Path.ERROR_FWD);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.DATA_BASE_CONNECTION));

        }
        return result;
    }

    private List<Activity> sortByNumberOfUsers(int currentPage, List<Activity> activities) {
        activities = activities.stream().sorted((activity, t1) -> activity.compare(activity.getUsersId(), t1.getUsersId())).collect(Collectors.toList());
        Collections.reverse(activities);
        activities = activities.stream().skip((currentPage - 1) * 5).limit(5).collect(Collectors.toList());
        return activities;
    }
}
