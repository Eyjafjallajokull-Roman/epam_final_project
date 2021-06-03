package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.ErrorConfig;
import com.epam.project.constants.ErrorConst;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.Activity;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteActivityCommand implements Command {
    private static final Logger log = Logger.getLogger(DeleteActivityCommand.class);
    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ResultOfExecution result = new ResultOfExecution();
        ErrorConfig error = ErrorConfig.getInstance();
        ActivityService activityService = ServiceFactory.getActivityService();
        result.setDirection(Direction.REDIRECT);
        try {
            Activity activity = activityService.findActivityById(Integer.valueOf(request.getParameter("idDelete")));
            activityService.deleteActivity(activity);
            result.setPage(Path.DECLINED_ACTIVITIES);
        } catch (NoSuchActivityException e) {
            log.error(e);
            result.setDirection(Direction.FORWARD);
            result.setPage(Path.ERROR_FWD);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.NO_SUCH_ACTIVITY));
        }
        return result;
    }
}
