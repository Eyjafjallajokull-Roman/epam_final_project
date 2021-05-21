package com.epam.project.controller;

import com.epam.project.command.Command;
import com.epam.project.command.CommandContainer;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    private static final Logger logger = Logger.getLogger(Controller.class);
    private final CommandContainer commandContainer = CommandContainer.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Controller starts");

        String commandName = req.getParameter("command");
        logger.trace("Request parameter: command --> " + commandName);

        Command command = commandContainer.get(commandName);
        ResultOfExecution result = command.execute(req, resp);

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        logger.debug("Controller end");
        if (result.isInvalidate())
            req.getSession(false).invalidate();
        if (result.getDirection() == Direction.FORWARD)
            req.getRequestDispatcher(result.getPage()).forward(req, resp);
        if (result.getDirection() == Direction.REDIRECT) {
            resp.sendRedirect(result.getPage());
        }
    }
}
