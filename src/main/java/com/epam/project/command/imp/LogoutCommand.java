package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.ErrorConfig;
import com.epam.project.constants.ErrorConst;
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
        ErrorConfig error = ErrorConfig.getInstance();
        try {
            result.invalidateSession();
            result.setPage(Path.USER_LOGIN);
        } catch (Exception e) {
            log.error(e);
            result.setDirection(Direction.FORWARD);
            result.setPage(Path.ERROR_FWD);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.Data_WAS_NOT_FOUND));
        }
        return result;
    }
}
