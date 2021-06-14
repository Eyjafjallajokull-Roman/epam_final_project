package com.epam.project.command.page;

import com.epam.project.command.Command;
import com.epam.project.command.imp.LoginCommand;
import com.epam.project.constants.ErrorConfig;
import com.epam.project.constants.ErrorConst;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoToRegisterPageCommand implements Command {
    private static final Logger logger = Logger.getLogger(LoginCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.REDIRECT);
        ErrorConfig error = ErrorConfig.getInstance();
        try {
            logger.info("GoToRegisterPageCommand");
            result.setPage(Path.USER_REGISTER);
        } catch (Exception e) {
            logger.error(e);
            result.setPage(Path.ERROR);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.PAGE_NOT_FOUND));
        }
        return result;
    }
}
