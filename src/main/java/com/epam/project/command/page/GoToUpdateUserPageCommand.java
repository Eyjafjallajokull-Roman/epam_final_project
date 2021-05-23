package com.epam.project.command.page;

import com.epam.project.command.Command;
import com.epam.project.command.imp.LoginCommand;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GoToUpdateUserPageCommand implements Command {
    private static final Logger logger = Logger.getLogger(LoginCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        String errorMessage = "Exeption error";
        ResultOfExecution result = new ResultOfExecution();
        HttpSession session = request.getSession();
        result.setDirection(Direction.FORWARD);
        try {

            result.setPage(Path.UPDATE_USER_PAGE_FWD);
        } catch (Exception e) {
            logger.error(e);
            result.setPage(Path.ERROR);
            request.setAttribute("errorMessage", errorMessage);
        }
        return result;
    }
}
