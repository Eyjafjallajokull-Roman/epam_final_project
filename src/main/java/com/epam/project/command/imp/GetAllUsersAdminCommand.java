package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.User;
import com.epam.project.exception.DataBaseConnectionException;
import com.epam.project.exception.DataNotFoundException;
import com.epam.project.exception.NoUserException;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetAllUsersAdminCommand implements Command {
    private static final Logger log = Logger.getLogger(GetAllUsersAdminCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getHeader("referer").replaceFirst("http://localhost:8080", "");
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        String errorMessage;
        int currentPage;
        int totalPages;
        String param;

        List<User> users;
        UserService userService = ServiceFactory.getUserService();
        try {
            currentPage = request.getParameter("currentPageU") == null ? 1
                    : Integer.parseInt(request.getParameter("currentPageU"));
            param = request.getParameter("typeU");


            users = userService.findAllUsersWithLimit((currentPage - 1) * 5, 5, param);
            totalPages = (userService.calculateAllUsers() / 5) + 1;

            request.setAttribute("typeU", param);
            request.setAttribute("users", users);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPageU", currentPage);
            result.setPage(Path.ADMIN_ALL_USERS_FWD);
        } catch (NoUserException | DataBaseConnectionException e) {
            errorMessage = " Something go wrong";
            request.setAttribute("errorMessage", errorMessage);
            log.error(e);
            result.setPage(Path.ERROR_FWD);
        }
        return result;
    }
}
