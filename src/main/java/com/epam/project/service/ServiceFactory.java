package com.epam.project.service;

import com.epam.project.service.imp.ActivityServiceImp;
import com.epam.project.service.imp.UserServiceImp;

public class ServiceFactory {
    public ServiceFactory() {

    }

    public static UserService getUserService() {
        return new UserServiceImp();
    }

    public static ActivityService activityService() {
        return new ActivityServiceImp();
    }
}
