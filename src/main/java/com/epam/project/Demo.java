package com.epam.project;

import com.epam.project.entity.Activity;
import com.epam.project.exception.DataNotFoundException;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.exception.NoUserException;
import com.epam.project.service.ActivityService;
import com.epam.project.service.UserService;
import com.epam.project.service.imp.ActivityServiceImp;
import com.epam.project.service.imp.UserServiceImp;
import com.epam.project.taghandler.PasswordEncoder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class Demo {
    public static void main(String[] args) throws DataNotFoundException, NoUserException, NoSuchActivityException {
        UserService userService = new UserServiceImp();
        ActivityService activityService = new ActivityServiceImp();

//        User user = new User();
//        user.setEmail("titanmki");
//        user.setPassword("12345");
//        user.setRole(Role.CLIENT);
//        user.setName("Roma");
//        user.setSurname("Sekh");
//        user.setId(3);
//
//        System.out.println(userService.addUser(user));


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
//        System.out.println(activityService.findAllActivitiesByCreatedId(2));

//        System.out.println(activityService.findFirstFiveActivitiesByUserId(2));

//        Activity activty = activityService.findActivityById(6);
//        long diff = activty.getEndTime().getTime() - activty.getStartTime().getTime();





        List<Activity> activityList = activityService.findAllActivity();
        List<Activity> collect = activityList.stream().sorted((activity, t1) -> activity.compare(activity.getUsersId(), t1.getUsersId())).collect(Collectors.toList());
        Collections.reverse(collect);
//        System.out.println(collect);

        List<Activity> collect1 = collect.stream().skip(0).limit(6).collect(Collectors.toList());
        System.out.println(collect1);

    }


}
