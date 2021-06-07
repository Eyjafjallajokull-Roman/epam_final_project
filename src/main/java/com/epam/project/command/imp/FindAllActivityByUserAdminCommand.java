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
import com.epam.project.exception.DataBaseConnectionException;
import com.epam.project.exception.DataNotFoundException;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.exception.NoUserException;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FindAllActivityByUserAdminCommand implements Command {
    private static final Logger log = Logger.getLogger(GetAllUsersAdminCommand.class);

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
            int currentPage;
            int totalPages;
            String param;
            String typeOfActivity;
            String email;
            List<Activity> activities;
            UserService userService = ServiceFactory.getUserService();
            ActivityService activityService = ServiceFactory.getActivityService();
            email = request.getParameter("email");
            Integer userId = userService.findUserByLogin(email).getId();
            currentPage = request.getParameter("currentPageFUA") == null ? 1
                    : Integer.parseInt(request.getParameter("currentPageFUA"));


            if (request.getParameter("typeFUA") == null || request.getParameter("typeActivityFUA") == null) {
                param = "activity.name";
                typeOfActivity = "all";
            } else {
                param = types.stream().filter(s -> s.equals(request.getParameter("typeFUA"))).collect(Collectors.toList()).get(0);
                typeOfActivity = typesOfActivity.stream().filter(s -> s.equals(request.getParameter("typeActivityFUA"))).collect(Collectors.toList()).get(0);
            }

            if (param.equals("activity.users")) {
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
                    activities = activityService.findAllConnectingActivityByUserIdAndStatus(userId, Status.ACCEPT.name(), (currentPage - 1) * 5, 5, param);
                    totalPages = (activityService.calculateConnectingActivityByUserIdAndStatus(userId, Status.ACCEPT.name()) / 5) + 1;
                } else {
                    activities = activityService.findAllConnectingActivityByUserIdAndStatusAndTypeActivity(userId, Status.ACCEPT.name(), typeOfActivity, (currentPage - 1) * 5, 5, param);
                    totalPages = (activityService.calculateConnectingActivityByUsersBIdAndStatusAndType(userId, Status.ACCEPT.name(), typeOfActivity) / 5) + 1;
                }
            }

            request.setAttribute("email", email);
            request.setAttribute("typeFUA", param);
            request.setAttribute("typeOfActivityFUA", typeOfActivity);
            request.setAttribute("activities", activities);
            request.setAttribute("totalPagesFUA", totalPages);
            request.setAttribute("currentPageFUA", currentPage);
            result.setPage(Path.ADMIN_ACTIVITY_BY_USER);
        } catch (NoSuchActivityException e) {
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.UNABLE_TO_FOUND_USER));
            log.error(e);
            result.setPage(Path.ERROR_FWD);
        } catch (DataNotFoundException e) {
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.Data_WAS_NOT_FOUND));
            log.error(e);
            result.setPage(Path.ERROR_FWD);
        } catch (DataBaseConnectionException e) {
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.DATA_BASE_CONNECTION));
            log.error(e);
            result.setPage(Path.ERROR_FWD);
        } catch (NoUserException e) {
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.NO_SUCH_ACTIVITY));
            log.error(e);
            result.setPage(Path.ERROR_FWD);
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
