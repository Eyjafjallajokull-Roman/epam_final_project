package com.epam.project.constants;

public interface Path {
    //path for redirect
    String ADMIN_CABINET = "/project/admin/adminCabinet.jsp";
    String USER_CABINET = "/project/page/cabinet.jsp";
    String USER_LOGIN = "/project/login.jsp";
    String USER_REGISTER = "/project/register.jsp";
    String ERROR = "/project/error.jsp";
    String SECURITY_ERROR = "/project/securityError.jsp";
    String MY_ACTIVITIES_PAGE = "/project/page/MyActivities.jsp";
    String DECLINED_ACTIVITIES = "/project/page/DeclinedActivities.jsp";
    String ACTIVITY_PAGE = "/project/page/createActivity.jsp";
    //path for forward
    String ACTIVITY_PAGE_FWD = "/page/createActivity.jsp";
    String ERROR_FWD = "/error.jsp";
    String ADMIN_ERROR_FWD = "/securityError.jsp";
    String UPDATE_USER_PAGE_FWD = "/page/updateUser.jsp";
    String USER_CABINET_FWD = "/page/cabinet.jsp";
    String MY_ACTIVITIES_PAGE_FWD = "/page/MyActivities.jsp";
    String DECLINED_ACTIVITIES_FWD = "/page/DeclinedActivities.jsp";


    String ADMIN_SHOW_CREATED_ID_FWD = "/admin/ShowUserCreatedActivity.jsp";
    String ADMIN_ACTIVITIES_ON_CHECK_FWD = "/admin/ActivitiesOnCheck.jsp";
    String ADMIN_ALL_ACTIVITIES_FWD = "/admin/AllActivities.jsp";
    String ADMIN_ALL_USERS_FWD = "/admin/AllUsers.jsp";
    String ADMIN_USERS_BY_ACTIVITY = "/admin/FindUserByActivity.jsp";
    String ADMIN_ACTIVITY_BY_USER = "/admin/FindActivityByUser.jsp";
    String ADMIN_USER_CREATED_BY_USER_ID = "/admin/CreatedByUserActivity.jsp";


}
