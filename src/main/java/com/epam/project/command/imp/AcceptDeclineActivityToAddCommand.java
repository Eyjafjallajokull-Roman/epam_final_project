package com.epam.project.command.imp;

import com.epam.project.util.CheckRole;
import com.epam.project.util.SendEmail;
import com.epam.project.command.Command;
import com.epam.project.constants.ErrorConfig;
import com.epam.project.constants.ErrorConst;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.*;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.exception.NoUserException;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AcceptDeclineActivityToAddCommand implements Command {
    private static final Logger log = Logger.getLogger(AcceptDeclineActivityToAddCommand.class);


    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {

        String url = request.getHeader("referer").replaceFirst("http://localhost:8080", "");
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.REDIRECT);
        HttpSession session = request.getSession();
        ErrorConfig error = ErrorConfig.getInstance();

        if (!CheckRole.checkRole(session, Role.ADMIN)) {
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.ERROR_ADMIN));
            result.setPage(Path.ADMIN_ERROR_FWD);
            return result;
        }

        try {
            UserService userService = ServiceFactory.getUserService();
            ActivityService activityService = ServiceFactory.getActivityService();
            Activity activity;
            String typeOf = request.getParameter("typeOf");
            String option = request.getParameter("option");
            Integer id = Integer.valueOf(request.getParameter("id"));
            activity = activityService.findActivityById(id);
            User user = userService.findUserById(activity.getCreatedByUserID());
            log.info("GetAllParams in AcceptDeclineActivityToAddCommand");
//разбить на методы
//todo methods
            if (typeOf.equals(Status.ON_CHECK.name())) {
                activity.setStatus(option.equals("acceptActivity") ? Status.ACCEPT : Status.DECLINE);
                if (option.equals("acceptActivity")) {
                    activity.setStatus(Status.ACCEPT);
                    result.setSendEmail(new SendEmail("Your activity with name:" + activity.getName()
                            + " was successfully added to your list  ", "Activity On Check", user.getEmail()));
                } else {
                    result.setSendEmail(new SendEmail("Your activity with name:" + activity.getName()
                            + " was declined, you can update it or delete on your 'Declined Activity' page  ",
                            "Activity On Check", user.getEmail()));
                }

            } else if (typeOf.equals(Status.ON_DELETE.name())) {
                if (option.equals("acceptActivity")) {
                    activityService.deleteActivity(activity);
                    result.setSendEmail(new SendEmail("Your activity with name:" + activity.getName()
                            + " was successfully deleted  ", "Activity On Delete", user.getEmail()));
                } else {
                    activity.setStatus(Status.ACCEPT);
                    result.setSendEmail(new SendEmail("Your activity with name:" + activity.getName()
                            + " was declined, it will go back to your list ", "Activity On Delete", user.getEmail()));
                }
            } else {
                if (option.equals("acceptActivity")) {
                    Activity activityNew = activityService.findActivityById(activity.getId());
                    activity.setId(activity.getOldActivityId());
                    activity.setStatus(Status.ACCEPT);
                    activityService.deleteActivity(activityNew);
                    result.setSendEmail(new SendEmail("Your activity with name:" + activity.getName()
                            + " was successfully updated  ", "Activity On Update", user.getEmail()));
                } else {
                    //rollback old activity
                    Activity oldActivity = activityService.findActivityById(activity.getOldActivityId());
                    oldActivity.setStatus(Status.ACCEPT);
                    activityService.updateActivityWithoutValidation(oldActivity);
                    //delete new activity
                    activityService.deleteActivity(activity);
                    result.setSendEmail(new SendEmail("Your activity with name:" + activity.getName()
                            + " was  declined, it will go back to your list without changes ", "Activity On Update", user.getEmail()));

                }
            }

            if (activity.getTypeOfActivity().equals(TypeOfActivity.TIME_TRACKER)) {
                activity.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
            }
            activityService.updateActivityWithoutValidation(activity);
            request.setAttribute("typeOf", typeOf);
            result.setPage(url);
        } catch (NoSuchActivityException | NoUserException e) {
            log.error(e);
            result.setPage(Path.ERROR_FWD);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.ERROR_DECLINE_ACTIVITY));
        }

        return result;
    }
}
