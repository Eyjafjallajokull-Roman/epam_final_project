package com.epam.project.util;

import com.epam.project.constants.ErrorConfig;
import com.epam.project.entity.Role;
import com.epam.project.entity.User;

import javax.servlet.http.HttpSession;

/**
 * Check if User allowed for this command
 */
public class CheckRole {

    public static boolean checkRole(HttpSession session, Role role) {
        User user = (User) session.getAttribute("user");
        return user != null && user.getRole().equals(role);
    }
}
