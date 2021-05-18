package com.epam.project.command;

import com.epam.project.command.imp.LoginCommand;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {

    public static final Logger logger = Logger.getLogger(CommandContainer.class);
    private static final Map<String, Command> commandMap = new TreeMap<>();
    private static CommandContainer instance = null;

    static {
        commandMap.put("login", new LoginCommand());
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
