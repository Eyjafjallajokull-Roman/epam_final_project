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
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AcceptDeclineActivityToAddCommand implements Command {
    private static final Logger log = Logger.getLogger(AddNewActivityCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getHeader("referer").replaceFirst("http://localhost:8080", "");
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.REDIRECT);
        HttpSession session = request.getSession();
        ErrorConfig error = ErrorConfig.getInstance();
        if (!CheckRole.checkRole(session, Role.ADMIN))  {
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.ERROR_ADMIN));
            result.setPage(Path.ADMIN_ERROR_FWD);
            return result;
        }

        try {
            ActivityService activityService = ServiceFactory.getActivityService();
            Activity activity;
            String typeOf = request.getParameter("typeOf");
            String option = request.getParameter("option");
            Integer id = Integer.valueOf(request.getParameter("id"));
            activity = activityService.findActivityById(id);

            if (typeOf.equals(Status.ON_CHECK.name())) {
                activity.setStatus(option.equals("acceptActivity") ? Status.ACCEPT : Status.DECLINE);
            } else {

                if (option.equals("acceptActivity")) {
                    activityService.deleteActivity(activity);
                } else {
                    activity.setStatus(Status.ACCEPT);
                }
            }

            activityService.updateActivityWithoutValidation(activity);
            request.setAttribute("typeOf", typeOf);
            result.setPage(url);
        } catch (NoSuchActivityException e) {
            log.error(e);
            result.setPage(Path.ERROR_FWD);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.ERROR_DECLINE_ACTIVITY));
        }

        return result;
    }
}
