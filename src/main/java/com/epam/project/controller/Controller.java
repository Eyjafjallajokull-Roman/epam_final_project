package com.epam.project.controller;

import com.epam.project.SendEmail;
import com.epam.project.command.Command;
import com.epam.project.command.CommandContainer;
import org.apache.log4j.Logger;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    private static final Logger logger = Logger.getLogger(Controller.class);
    private final CommandContainer commandContainer = CommandContainer.getInstance();
    private String host;
    private String port;
    private String user;
    private String pass;

    public void init() {
        // reads SMTP server setting from web.xml file
        ServletContext context = getServletContext();
        host = context.getInitParameter("host");
        port = context.getInitParameter("port");
        user = context.getInitParameter("user");
        pass = context.getInitParameter("pass");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            handleRequest(req, resp);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            handleRequest(req, resp);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, MessagingException {
        logger.debug("Controller starts");

        String commandName = req.getParameter("command");
        logger.trace("Request parameter: command --> " + commandName);

        Command command = commandContainer.get(commandName);
        ResultOfExecution result = command.execute(req, resp);

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");



        logger.debug("Controller end");
        if (result.isInvalidate()) {
            req.getSession(false).invalidate();
        }
        if (result.getDirection() == Direction.FORWARD) {
            req.getRequestDispatcher(result.getPage()).forward(req, resp);
        }
        if (result.getDirection() == Direction.REDIRECT) {
            resp.sendRedirect(result.getPage());
        }
        if (result.getDirection() == null) {
// do nothing
        }

        if (result.getSendEmail() != null) {
            SendEmail.sendEmail(host, port, user, pass, result.getSendEmail().getToAddress(), result
                    .getSendEmail().getSubject(), result.getSendEmail().getMessage());
        }
    }
}
