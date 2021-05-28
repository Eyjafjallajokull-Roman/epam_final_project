package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.exception.NoUserException;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserFromActivity implements Command {
    private static final Logger log = Logger.getLogger(GetAllUsersAdminCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getHeader("referer").replaceFirst("http://localhost:8080", "");
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        result.setPage(Path.ERROR_FWD);
        String errorMessage;
        UserService userService = ServiceFactory.getUserService();
        try {
            Integer activityId = Integer.valueOf(request.getParameter("activityIdFUA"));
            Integer userId = userService.findUserByLogin(request.getParameter("email")).getId();
            System.out.println(request.getParameter("email"));
            System.out.println(userId);
            System.out.println(activityId);

            if (userService.deleteUserFromActivity(activityId, userId)) {
                result.setDirection(Direction.REDIRECT);
                result.setPage(url);
            } else {
                errorMessage = "Can`t delete user";
                request.setAttribute("errorMessage", errorMessage);
            }
        } catch (NoUserException e) {
            log.error(e);
            errorMessage = "No such user or Activity";
            request.setAttribute("errorMessage", errorMessage);
        }
        return result;
    }
}
