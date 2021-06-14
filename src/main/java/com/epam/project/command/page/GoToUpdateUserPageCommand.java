package com.epam.project.command.page;

import com.epam.project.util.CheckRole;
import com.epam.project.command.Command;
import com.epam.project.command.imp.LoginCommand;
import com.epam.project.constants.ErrorConfig;
import com.epam.project.constants.ErrorConst;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.Role;
import com.epam.project.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GoToUpdateUserPageCommand implements Command {
    private static final Logger logger = Logger.getLogger(LoginCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        HttpSession session = request.getSession();
        ErrorConfig error = ErrorConfig.getInstance();


        User user = (User) session.getAttribute("user");
        if (!CheckRole.checkRole(session, Role.CLIENT)) {
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.ERROR_ADMIN));
            result.setPage(Path.ADMIN_ERROR_FWD);
            return result;
        }
        try {
            logger.info("GoToUpdateUserPageCommand command");
            result.setPage(Path.UPDATE_USER_PAGE_FWD);
        } catch (Exception e) {
            logger.error(e);
            result.setPage(Path.ERROR);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.PAGE_NOT_FOUND));
        }
        return result;
    }
}
