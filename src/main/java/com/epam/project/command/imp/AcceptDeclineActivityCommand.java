package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.Activity;
import com.epam.project.entity.Status;
import com.epam.project.entity.User;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AcceptDeclineActivityCommand implements Command {
    private static final Logger log = Logger.getLogger(AddNewActivityCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getHeader("referer").replaceFirst("http://localhost:8080","");
        System.out.println(request.getAttribute("javax.servlet.forward.request_uri"));
        System.out.println(request.getHeader("referer"));
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.REDIRECT);
        ActivityService activityService = ServiceFactory.getActivityService();


        try {

            Integer id = Integer.valueOf(request.getParameter("id"));
            System.out.println(id);
            Activity activity = activityService.findActivityById(id);
            activity.setStatus(request.getParameter("option").equals("acceptActivity") ? Status.ACCEPT : Status.DECLINE);
            activityService.updateActivity(activity);
            result.setPage(url);

        } catch (NoSuchActivityException e) {
            log.error(e);
            result.setPage(Path.ERROR_FWD);
        }

        return result;
    }
}
