package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.controller.ResultOfExecution;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CabinetActivitiesCommand implements Command {
    private static final Logger logger = Logger.getLogger(LoginCommand.class);


    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {

        logger.debug("Login Command Start");
        HttpSession session = request.getSession();
        ResultOfExecution result = new ResultOfExecution();
        logger.info("Getting 5 activities...");




        return result;
    }
}
