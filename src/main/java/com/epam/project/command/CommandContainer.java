package com.epam.project.command;

import com.epam.project.command.imp.*;
import com.epam.project.command.page.*;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {

    public static final Logger logger = Logger.getLogger(CommandContainer.class);
    private static final Map<String, Command> commandMap = new TreeMap<>();
    private static CommandContainer instance = null;

    static {
        //user
        commandMap.put("login", new LoginCommand());
        commandMap.put("logout", new LogoutCommand());
        commandMap.put("register", new RegisterCommand());
        commandMap.put("createActivity", new AddNewActivityCommand());
        commandMap.put("updateActivity", new UpdateActivityCommand());
        commandMap.put("all-activities", new ShowAllActivitiesByUser());
        commandMap.put("createActivityPage", new GoToCreatePageCommand());
        commandMap.put("updateActivityPage", new GoToUpdatePageCommand());
        commandMap.put("deleteActivityUser", new UserDeleteActivityCommand());
        commandMap.put("updateUserPage", new GoToUpdateUserPageCommand());
        commandMap.put("updateUser", new UpdateUserCommand());
        commandMap.put("goToLogin", new GotoLoginPageCommand());
        commandMap.put("goToRegister", new GoToRegisterPageCommand());
        commandMap.put("pageNext", new UserSortPageCommand());
        commandMap.put("goToMyActivities", new GoToMyActivityPage());

        commandMap.put("addUserToActivity", new AddUserToActivity());
        //admin
        commandMap.put("activitiesOnCheck", new AdminActivitiesOnCheckCommand());
        commandMap.put("AcDecActivity", new AcceptDeclineActivityToAddCommand());
        commandMap.put("pageNextAdmin", new AdminSortPageCommand());
        commandMap.put("pageNextUser", new GetAllUsersAdminCommand());
        commandMap.put("deleteUserFromActivity", new DeleteUserFromActivity());
        commandMap.put("pageNextFUA", new FindUserByActivityAdminCommand());
        commandMap.put("pageNextFUActivity", new FindAllActivityByUserAdminCommand());
        commandMap.put("showCreatedUser", new AdminFindCreatedUser());
        commandMap.put("showActivitiesByCreatedId", new AdminFindWhatUserCreated());

        //future commands


        logger.debug("Command container was successfully initialized");
        logger.trace("Number of commands --> " + commandMap.size());
    }

    public static CommandContainer getInstance() {
        if (instance == null) {
            instance = new CommandContainer();
        }
        return instance;
    }

    public Command get(String commandName) {
        if (commandName == null || !commandMap.containsKey(commandName)) {
            logger.trace("Command not found, name --> " + commandName);
            return commandMap.get("noCommand");
        }

        return commandMap.get(commandName);
    }

}
