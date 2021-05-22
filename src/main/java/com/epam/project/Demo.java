package com.epam.project;

import com.epam.project.constants.Role;
import com.epam.project.constants.Status;
import com.epam.project.constants.TypeOfActivity;
import com.epam.project.entity.Activity;
import com.epam.project.entity.User;
import com.epam.project.exception.DataNotFoundException;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.exception.NoUserException;
import com.epam.project.service.ActivityService;
import com.epam.project.service.UserService;
import com.epam.project.service.imp.ActivityServiceImp;
import com.epam.project.service.imp.UserServiceImp;

import java.sql.Timestamp;
import java.time.LocalDateTime;


public class Demo {
    public static void main(String[] args) throws DataNotFoundException, NoUserException, NoSuchActivityException {
//        UserService userService = new UserServiceImp();
//        User user = new User();
//        user.setEmail("titanmki");
//        user.setPassword("12345");
//        user.setRole(Role.CLIENT);
//        user.setName("Roma");
//        user.setSurname("Sekh");
//        user.setId(3);
//
//        System.out.println(userService.addUser(user));


        ActivityService activityService = new ActivityServiceImp();
//        Activity activity = new Activity();
//        activity.setStatus(Status.ACCEPT);
//        activity.setTypeOfActivity(TypeOfActivity.EVENT);
//        activity.setName("name");
//        activity.setDescription("sadsadas");
//        activity.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
//        activity.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
//        activity.setUserID(2);
//
//        activityService.addActivity(activity);
        System.out.println(activityService.findAllActivitiesByCreatedId(2));


    }
}
