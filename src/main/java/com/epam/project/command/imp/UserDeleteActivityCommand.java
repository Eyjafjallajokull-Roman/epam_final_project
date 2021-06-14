package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.ErrorConfig;
import com.epam.project.constants.ErrorConst;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.Activity;
import com.epam.project.entity.Status;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserDeleteActivityCommand implements Command {
    private static final Logger log = Logger.getLogger(UserDeleteActivityCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getHeader("referer").replaceFirst("http://localhost:8080", "");
        ResultOfExecution result = new ResultOfExecution();
        ErrorConfig error = ErrorConfig.getInstance();
        ActivityService activityService = ServiceFactory.getActivityService();
        result.setDirection(Direction.FORWARD);
        try {
            log.info("UserDeclineActivityCommand command");
            Activity activity = activityService.findActivityById(Integer.valueOf(request.getParameter("idDelete")));
            activity.setStatus(Status.ON_DELETE);
            if (activityService.updateActivityWithoutValidation(activity)) {
                result.setPage(url);
                result.setDirection(Direction.REDIRECT);
            }
        } catch (NoSuchActivityException e) {
            log.error(e);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.ERROR_DECLINE_ACTIVITY));
            result.setPage(Path.ERROR_FWD);
        }
        return result;
    }
}
