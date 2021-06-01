package com.epam.project.command.page;

import com.epam.project.command.Command;
import com.epam.project.constants.ErrorConfig;
import com.epam.project.constants.ErrorConst;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoToUserDeclineCommand implements Command {
    private static final Logger logger = Logger.getLogger(GoToUserDeclineCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ErrorConfig error = ErrorConfig.getInstance();
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        try {

            result.setPage(Path.DECLINED_ACTIVITIES_FWD);

        } catch (Exception e) {
            logger.error(e);
            result.setPage(Path.ERROR);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.PAGE_NOT_FOUND));
        }
        return result;
    }
}
