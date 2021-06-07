package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.ErrorConfig;
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
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SetTimeTrackerDone implements Command {
    private static final Logger logger = Logger.getLogger(SetTimeTrackerDone.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getHeader("referer").replaceFirst("http://localhost:8080", "");
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        HttpSession session = request.getSession();
        ErrorConfig error = ErrorConfig.getInstance();
        try {
            ActivityService activityService = ServiceFactory.getActivityService();
            Activity activity = activityService.findActivityById(Integer.valueOf(request.getParameter("activityIdToDone")));
            activity.setEndTime(Timestamp.valueOf(LocalDateTime.now()));

            if (activityService.updateActivityWithoutValidation(activity)) {
                result.setDirection(Direction.REDIRECT);
                result.setPage(url);
            }else {
                result.setPage(Path.ERROR_FWD);
            }

        } catch (NoSuchActivityException e) {
            logger.error(e);
            result.setPage(Path.ERROR_FWD);
        }
        return result;
    }
}
