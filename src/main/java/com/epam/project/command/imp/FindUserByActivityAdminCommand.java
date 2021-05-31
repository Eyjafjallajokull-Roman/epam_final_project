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
import com.epam.project.exception.DataBaseConnectionException;
import com.epam.project.exception.DataNotFoundException;
import com.epam.project.exception.NoUserException;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FindUserByActivityAdminCommand implements Command {
    private static final Logger log = Logger.getLogger(GetAllUsersAdminCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        HttpSession session = request.getSession();
        ErrorConfig error = ErrorConfig.getInstance();
        if (!CheckRole.checkRole(session, Role.ADMIN)) {
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.ERROR_ADMIN));
            result.setPage(Path.ADMIN_ERROR_FWD);
            return result;
        }

        try {
            int currentPage;
            int totalPages;
            String param;
            String activityId;
            List<User> users;
            UserService userService = ServiceFactory.getUserService();
            currentPage = request.getParameter("currentPageFUA") == null ? 1
                    : Integer.parseInt(request.getParameter("currentPageFUA"));
            param = request.getParameter("typeFUA");
            if (param == null) {
                param = "user.name";
            }
            activityId = request.getParameter("activityIdFUA");
            System.out.println(param);
            users = userService.findAllConnectingUsersByActivity(Integer.valueOf(activityId), (currentPage - 1) * 5, 5,param);

            System.out.println((currentPage - 1) * 5);
            totalPages = (userService.calculateUsersInActivity(activityId) / 5) + 1;

            request.setAttribute("activityIdFUA", activityId);
            request.setAttribute("typeFUA", param);
            request.setAttribute("users", users);
            request.setAttribute("totalPagesFUA", totalPages);
            request.setAttribute("currentPageFUA", currentPage);
            result.setPage(Path.ADMIN_USERS_BY_ACTIVITY);
        } catch (NoUserException e) {
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.UNABLE_TO_FOUND_USER));
            log.error(e);
            result.setPage(Path.ERROR_FWD);
        } catch (DataBaseConnectionException e) {
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.DATA_BASE_CONNECTION));
            log.error(e);
            result.setPage(Path.ERROR_FWD);
        } catch (DataNotFoundException e) {
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.Data_WAS_NOT_FOUND));
            log.error(e);
            result.setPage(Path.ERROR_FWD);
        }
        return result;
    }
}
