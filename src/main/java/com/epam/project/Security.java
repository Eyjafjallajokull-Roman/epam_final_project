package com.epam.project;

import com.epam.project.entity.Role;
import com.epam.project.entity.User;

import javax.servlet.http.HttpSession;

public class Security {


    public static boolean checkPermission(HttpSession session, Role... roles) {
        if (session.getAttribute("user") == null) {
            return false;
        }

        User user = (User) session.getAttribute("user");
        for (Role role : roles) {
            if (user.getRole() == role) {
                return true;
            }
        }
        return false;
    }
}
