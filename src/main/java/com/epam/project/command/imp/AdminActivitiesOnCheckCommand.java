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
import com.epam.project.exception.DataBaseConnectionException;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AdminActivitiesOnCheckCommand implements Command {
    private static final Logger logger = Logger.getLogger(AdminActivitiesOnCheckCommand.class);

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
            String param = request.getParameter("type");
            String paramOrder = request.getParameter("typeParam");
            List<Activity> activities;
            currentPage = (request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage")));
            activities = activityService.findActivitiesByStatusName(param, (currentPage - 1) * 5, 5, paramOrder);
            totalPages = (activityService.calculateActivityNumberByStatusName(param) / 5) + 1;

            request.setAttribute("userService", ServiceFactory.getUserService());
//            request.setAttribute("users", users);
            request.setAttribute("activities", activities);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("type", param);
            result.setPage(Path.ADMIN_ACTIVITIES_ON_CHECK_FWD);
        } catch (NoSuchActivityException e) {
            logger.error(e);
            result.setPage(Path.ERROR_FWD);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.NO_SUCH_ACTIVITY));

        } catch (DataBaseConnectionException e) {
            logger.error(e);
            result.setPage(Path.ERROR_FWD);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.DATA_BASE_CONNECTION));

        }
        return result;
    }
}
