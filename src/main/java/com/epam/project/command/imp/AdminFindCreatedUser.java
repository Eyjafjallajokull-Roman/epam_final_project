package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.User;
import com.epam.project.exception.NoUserException;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminFindCreatedUser implements Command {
    private static final Logger log = Logger.getLogger(UserSortPageCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        UserService userService = ServiceFactory.getUserService();
        String errorMessage;
        try {
            String email = request.getParameter("emailCreatedBy");
            User user = userService.findUserByLogin(email);


            request.setAttribute("name", user.getName());
            request.setAttribute("surname", user.getSurname());
            request.setAttribute("email", user.getEmail());
            result.setPage()
        } catch (NoUserException e) {
            log.error(e);
            errorMessage = "user was not found";
            request.setAttribute("errorMessage", errorMessage);
            result.setPage(Path.ERROR_FWD);
        }
        return null;
    }
}
