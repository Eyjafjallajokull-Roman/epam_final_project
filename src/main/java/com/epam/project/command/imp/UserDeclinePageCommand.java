package com.epam.project.command.imp;

import com.epam.project.util.CheckRole;
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
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserDeclinePageCommand implements Command {
    private static final Logger log = Logger.getLogger(UserSortPageCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        HttpSession session = request.getSession();
        ErrorConfig error = ErrorConfig.getInstance();
        if (!CheckRole.checkRole(session, Role.CLIENT)) {
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.ERROR_ADMIN));
            result.setPage(Path.ADMIN_ERROR_FWD);
            return result;
        }
        try {
            log.info("UserDeclinePageCommand command");
            User user = (User) session.getAttribute("user");
            ActivityService activityService = ServiceFactory.getActivityService();
            int currentPage;
            int totalPages;
            List<Activity> activities;

            currentPage = request.getParameter("currentPage") == null ? 1
                    : Integer.parseInt(request.getParameter("currentPage"));

            activities = activityService.findAllActivitiesByCreatedId(user.getId(), "start_time", (currentPage - 1) * 5, 5, Status.DECLINE.name());
            totalPages = (activityService.calculateActivityByCreatedId(user.getId(), Status.DECLINE.name()) / 5) + 1;

            request.setAttribute("activities", activities);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", currentPage);
            result.setPage(Path.DECLINED_ACTIVITIES_FWD);
        } catch (NoSuchActivityException e) {
            log.error(e);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.UNABLE_TO_FOUND_USER));
            result.setPage(Path.ERROR_FWD);
        } catch (DataBaseConnectionException e) {
            log.error(e);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.DATA_BASE_CONNECTION));
            result.setPage(Path.ERROR_FWD);
        }
        return result;
    }
}
