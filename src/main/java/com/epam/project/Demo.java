package com.epam.project;

import com.epam.project.entity.User;
import com.epam.project.exception.DataNotFoundException;
import com.epam.project.exception.NoUserException;
import com.epam.project.service.UserService;
import com.epam.project.service.imp.UserServiceImp;


public class Demo {
    public static void main(String[] args) throws DataNotFoundException, NoUserException {
        UserService userService = new UserServiceImp();
        User user = new User();
        user.setLogin("titanmki");
        user.setPassword("12345");

        System.out.println(userService.findUser(user.getLogin(),user.getPassword()));


//        System.out.println(activityDaoImp.findAllActivity());
//        System.out.println(userDaoImp.findUsersByRole(Role.CLIENT));
//        userDaoImp.createUser(user);
//        user = userDaoImp.findUserById(3);
//        user.setName("jepa");
//        userDaoImp.updateUser(user);
//        userDaoImp.deleteUser(user);
//        System.out.println(userDaoImp.findAllUsers());

//        System.out.println(userDaoImp.findUserById(1));
//        System.out.println(activityDaoImp.findActivityById(1));
//        System.out.println(activityDaoImp.findActivityByTypeOfActivity(TypeOfActivity.EVENT));
//     Activity activity =  activityDaoImp.findActivityById(1);
//     activityDaoImp.createActivity(activity);
//     activity.setName("gavno");
//        activityDaoImp.updateActivity(activity);
//        activityDaoImp.deleteActivity(activity);

//        System.out.println(userService.findAllUsers());


    }
}
