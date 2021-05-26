package com.epam.project.command.page;

import com.epam.project.TimeParser;
import com.epam.project.command.Command;
import com.epam.project.command.imp.LoginCommand;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.Activity;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoToUpdatePageCommand implements Command {
    private static final Logger logger = Logger.getLogger(LoginCommand.class);


    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = ServiceFactory.getActivityService();
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        String errorMessage;
        try {
            Integer id = Integer.valueOf(request.getParameter("idUpdate"));

            Activity activity = activityService.findActivityById(id);

            result.setPage(Path.ACTIVITY_PAGE_FWD);



            request.setAttribute("name", activity.getName());
            request.setAttribute("id", id);
            request.setAttribute("typeOfActivity", activity.getTypeOfActivity());
            request.setAttribute("description_en", activity.getDescriptionEng());
            request.setAttribute("description_ru", activity.getDescriptionRus());
            //thing how to insert timestamp
            request.setAttribute("start_time", activity.getStartTime());
            request.setAttribute("end_time", activity.getEndTime());
            request.setAttribute("type", "update");

        } catch (Exception e) {
            errorMessage = "Not Such activity";
            request.setAttribute("errorMessage", errorMessage);
            logger.error(e);
            result.setPage(Path.ERROR_FWD);
        }
        return result;
    }
}
