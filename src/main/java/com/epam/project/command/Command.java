package com.epam.project.command;

import com.epam.project.controller.ResultOfExecution;
import com.epam.project.exception.DataNotFoundException;
import com.epam.project.exception.NoUserException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response);



}
