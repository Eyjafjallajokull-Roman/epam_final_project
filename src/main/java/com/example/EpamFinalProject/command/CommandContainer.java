package com.example.EpamFinalProject.command;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {

    public static final Logger logger = Logger.getLogger(CommandContainer.class);
    private static final Map<String, Command> commandMap = new TreeMap<>();

    static {
        //future commands
        logger.debug("Command container was successfully initialized");
        logger.trace("Number of commands --> " + commandMap.size());
    }

    public static Command get(String commandName) {
        if (commandName == null || !commandMap.containsKey(commandName)) {
            logger.trace("Command not found, name --> " + commandName);
            return commandMap.get("noCommand");
        }

        return commandMap.get(commandName);
    }

}
