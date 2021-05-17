package com.example.EpamFinalProject;

import com.example.EpamFinalProject.dao.imp.UserDaoImp;
import com.example.EpamFinalProject.entity.Activity;
import com.example.EpamFinalProject.entity.User;

public class Demo {
    public static void main(String[] args) {
        DBManager dbManager = DBManager.getInstance();
        User user = new User();
        user.setName("nazar");
        user.setSurname("sekh");
        user.setLogin("sekh321");
        user.setRoleId(1);
        user.setPassword("1234");
        UserDaoImp userDaoImp = new UserDaoImp();
        System.out.println(userDaoImp.findAllUsers());

    }
}
