package com.epam.project.command.imp;

import com.epam.project.SendEmail;
import com.epam.project.command.Command;
import com.epam.project.constants.ErrorConfig;
import com.epam.project.constants.ErrorConst;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.Activity;
import com.epam.project.entity.User;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.exception.NoUserException;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import com.mysql.cj.Session;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddUserToActivity implements Command {
    private static final Logger log = Logger.getLogger(AddUserToActivity.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getHeader("referer").replaceFirst("http://localhost:8080", "");
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        ErrorConfig error = ErrorConfig.getInstance();
        HttpSession session = request.getSession();
        User userSession = (User) session.getAttribute("user");

        try {
            UserService userService = ServiceFactory.getUserService();
            ActivityService activityService = ServiceFactory.getActivityService();
            String email = request.getParameter("userEmail");
            Integer activityId = Integer.valueOf(request.getParameter("activityToInsert"));
            User user = userService.findUserByLogin(email);
            Activity activity = activityService.findActivityById(activityId);
            if (userService.addUserToActivity(activity, user)) {
                result.setSendEmail(new SendEmail("You have been added to activity: " + activity.getName() +
                        " by user: " + userSession.getEmail(),
                        "Add to new Activity", user.getEmail()));
                result.setPage(url);
                result.setDirection(Direction.REDIRECT);
            } else {
                request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.USER_ALREADY_IN_ACTIVITY));
                result.setPage(Path.ERROR_FWD);
            }

        } catch (NoUserException e) {
            log.error(e);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.UNABLE_TO_FOUND_USER));
            result.setPage(Path.ERROR_FWD);

        } catch (NoSuchActivityException e) {

            System.out.println("error");
            log.error(e);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.NO_SUCH_ACTIVITY));
            result.setPage(Path.ERROR_FWD);

        } catch (NullPointerException e) {

            log.error(e);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.DATA_BASE_CONNECTION));
            result.setPage(Path.ERROR_FWD);
        }

        return result;
    }
}
