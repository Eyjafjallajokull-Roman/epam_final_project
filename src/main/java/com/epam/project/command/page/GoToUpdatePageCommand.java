package com.epam.project.command.page;

import com.epam.project.command.Command;
import com.epam.project.command.imp.LoginCommand;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoToUpdatePageCommand implements Command {
    private static final Logger logger = Logger.getLogger(LoginCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {

        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        try {
            result.setPage(Path.ACTIVITY_PAGE_FWD);
            request.setAttribute("type", "update");


        } catch (Exception e) {
            logger.error(e);
            result.setPage(Path.ERROR);
        }
        return result;
    }
}
