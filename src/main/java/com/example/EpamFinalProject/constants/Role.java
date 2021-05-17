package com.example.EpamFinalProject.constants;

import com.example.EpamFinalProject.entity.User;

public enum Role {
    CLIENT, ADMIN;


    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
