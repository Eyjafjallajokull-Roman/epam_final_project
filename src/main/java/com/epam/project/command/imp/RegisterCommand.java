package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.Path;
import com.epam.project.constants.Role;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.User;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegisterCommand implements Command {
    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.REDIRECT);
        HttpSession session = request.getSession();
        String errorMessage = null;

        try {
            String email = request.getParameter("email");
            String name = request.getParameter("name");
            String last = request.getParameter("lastName");
            String password = request.getParameter("password");

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setName(name);
            user.setSurname(last);
            user.setRole(Role.CLIENT);
            UserService userService = ServiceFactory.getUserService();
            if (userService.addUser(user)) {
                session.setAttribute("userLogin", email);
                session.setAttribute("user", user);
                result.setPage(Path.USER_CABINET);
            } else {
                errorMessage = "CanNot Register";
                request.setAttribute("errorMessage", errorMessage);
                result.setPage(Path.ERROR);
            }

        } catch (Exception e) {
            errorMessage = "Unexpected exeption";
            request.setAttribute("errorMessage", errorMessage);
            result.setPage(Path.ERROR);
        }

        return result;
    }
}
