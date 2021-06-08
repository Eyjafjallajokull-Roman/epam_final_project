package com.epam.project.command.imp;

import com.epam.project.util.CheckRole;
import com.epam.project.command.Command;
import com.epam.project.constants.ErrorConfig;
import com.epam.project.constants.ErrorConst;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.Role;
import com.epam.project.exception.NoUserException;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteUserFromActivity implements Command {
    private static final Logger log = Logger.getLogger(GetAllUsersAdminCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getHeader("referer").replaceFirst("http://localhost:8080", "");
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        result.setPage(Path.ERROR_FWD);
        HttpSession session = request.getSession();
        ErrorConfig error = ErrorConfig.getInstance();

        if (!CheckRole.checkRole(session, Role.ADMIN)) {
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.ERROR_ADMIN));
            result.setPage(Path.ADMIN_ERROR_FWD);
            return result;
        }

        try {
            UserService userService = ServiceFactory.getUserService();
            Integer activityId = Integer.valueOf(request.getParameter("activityIdFUA"));
            Integer userId = userService.findUserByLogin(request.getParameter("email")).getId();


            if (userService.deleteUserFromActivity(activityId, userId)) {
                result.setDirection(Direction.REDIRECT);
                result.setPage(url);
            } else {
                request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.NO_SUCH_ACTIVITY));
            }

        } catch (NoUserException e) {
            log.error(e);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.CAN_NOT_DELETE_USER_FROM_ACTIVITY));

        }
        return result;
    }
}
