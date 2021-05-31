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
import com.epam.project.exception.NoUserException;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminFindCreatedUser implements Command {
    private static final Logger log = Logger.getLogger(UserSortPageCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        UserService userService = ServiceFactory.getUserService();

        HttpSession session = request.getSession();
        ErrorConfig error = ErrorConfig.getInstance();
        if (!CheckRole.checkRole(session, Role.ADMIN)) {
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.ERROR_ADMIN));
            result.setPage(Path.ADMIN_ERROR_FWD);
            return result;
        }

        try {
            String email = request.getParameter("createdId");
            System.out.println(email);
            User userByLogin = userService.findUserByLogin(email);


            request.setAttribute("name", userByLogin.getName());
            request.setAttribute("surname", userByLogin.getSurname());
            request.setAttribute("email", userByLogin.getEmail());
            result.setPage(Path.ADMIN_SHOW_CREATED_ID_FWD);

        } catch (NoUserException e) {
            log.error(e);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.UNABLE_TO_FOUND_USER));
            result.setPage(Path.ERROR_FWD);
        }
        return result;
    }
}
