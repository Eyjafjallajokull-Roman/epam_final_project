package com.epam.project.command.imp;

import com.epam.project.CheckRole;
import com.epam.project.command.Command;
import com.epam.project.constants.ErrorConfig;
import com.epam.project.constants.ErrorConst;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.Role;
import com.epam.project.entity.User;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserLeaveFromActivityCommand implements Command {
    private static final Logger log = Logger.getLogger(UserLeaveFromActivityCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getHeader("referer").replaceFirst("http://localhost:8080", "");
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        result.setPage(Path.ERROR_FWD);
        HttpSession session = request.getSession();
        ErrorConfig error = ErrorConfig.getInstance();

        if (!CheckRole.checkRole(session, Role.CLIENT)) {
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.ERROR_ADMIN));
            result.setPage(Path.ADMIN_ERROR_FWD);
            return result;
        }

        try {
            UserService userService = ServiceFactory.getUserService();
            User user = (User) session.getAttribute("user");
            Integer activityId = Integer.valueOf(request.getParameter("activityLeave"));

            if (userService.deleteUserFromActivity(activityId, user.getId())) {
                result.setDirection(Direction.REDIRECT);
                result.setPage(url);
            } else {
                request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.NO_SUCH_ACTIVITY));
            }

        } catch (NullPointerException e) {
            log.error(e);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.CAN_NOT_DELETE_USER_FROM_ACTIVITY));

        }
        return result;
    }
}
