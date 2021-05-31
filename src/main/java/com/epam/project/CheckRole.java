package com.epam.project;

import com.epam.project.constants.ErrorConfig;
import com.epam.project.entity.Role;
import com.epam.project.entity.User;

import javax.servlet.http.HttpSession;

public class CheckRole {

    public static boolean checkRole(HttpSession session, Role role) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals(role)) {
            return false;
        }
        return true;
    }
}
