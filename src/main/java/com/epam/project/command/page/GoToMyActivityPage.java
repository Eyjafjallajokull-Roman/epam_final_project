package com.epam.project.command.page;

import com.epam.project.command.Command;
import com.epam.project.command.imp.LoginCommand;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoToMyActivityPage implements Command {
    private static final Logger logger = Logger.getLogger(LoginCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        String errorMessage = "Exception error";
        ResultOfExecution result = new ResultOfExecution();
        try {
            result.setDirection(Direction.REDIRECT);
            result.setPage(Path.MY_ACTIVITIES_PAGE);
        } catch (Exception e) {
            result.setDirection(Direction.FORWARD);
            logger.error(e);
            result.setPage(Path.ERROR_FWD);
            request.setAttribute("errorMessage", errorMessage);
        }
        return result;
    }
}
