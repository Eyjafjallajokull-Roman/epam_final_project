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
import javax.servlet.http.HttpSession;

public class ChooseTypeOfActivity implements Command {
    private static final Logger logger = Logger.getLogger(ChooseTypeOfActivity.class);
    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        HttpSession session = request.getSession();
        ErrorConfig error = ErrorConfig.getInstance();
        try {
            String typeOfActivity = request.getParameter("typeOfActivity");
            String type = request.getParameter("type");


            request.setAttribute("typeOfActivity", typeOfActivity);
            request.setAttribute("type", type);
            request.setAttribute("typeP", type);
            result.setPage(Path.ACTIVITY_PAGE_FWD);
        } catch (NullPointerException e) {
            logger.error(e);
            result.setPage(Path.ERROR);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.PAGE_NOT_FOUND));
        }
        return result;
    }
}
