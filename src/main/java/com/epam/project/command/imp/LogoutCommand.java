package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements Command {
    private static final Logger log = Logger.getLogger(LogoutCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Command Starts logout");

        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.REDIRECT);
        try {
            result.invalidateSession();
            result.setPage(Path.USER_LOGIN);
        } catch (Exception e) {
            log.error(e);
            result.setDirection(Direction.FORWARD);
            result.setPage(Path.ERROR_FWD);
            String errorMessage = "Unpredictable exception";
            request.setAttribute("errorMessage", errorMessage);
        }
        return result;
    }
}
