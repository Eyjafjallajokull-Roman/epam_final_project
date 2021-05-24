package com.epam.project.command.imp;

import com.epam.project.TimeParser;
import com.epam.project.command.Command;
import com.epam.project.constants.Path;
import com.epam.project.entity.Status;
import com.epam.project.entity.TypeOfActivity;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.Activity;
import com.epam.project.entity.User;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;


public class AddNewActivityCommand implements Command {
    private static final Logger log = Logger.getLogger(AddNewActivityCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.REDIRECT);
        HttpSession session = request.getSession();

        String errorMessage = null;
        try {
            //todo if else and throw exeption

            User user = (User) session.getAttribute("user");
            if (user != null) {
                String name = request.getParameter("name");
                String typeOfActivity = request.getParameter("typeOfActivity");
                String descriptionEn = request.getParameter("description_en");
                String descriptionRu = request.getParameter("description_ru");
                String startTime = TimeParser.parseTimeFromJsp(request.getParameter("start_time"));
                String endTime = TimeParser.parseTimeFromJsp(request.getParameter("end_time"));
                int created_by = user.getId();

                Activity activity = new Activity();
                activity.setCreatedByUserID(created_by);
                activity.setStartTime(Timestamp.valueOf(startTime));
                activity.setEndTime(Timestamp.valueOf(endTime));
                activity.setDescriptionEng(descriptionEn);
                activity.setDescriptionRus(descriptionRu);
                activity.setTypeOfActivity(TypeOfActivity.valueOf(typeOfActivity));
                activity.setName(name);
                activity.setStatus(Status.ON_CHECK);
                ActivityService activityService = ServiceFactory.getActivityService();

                if (activityService.addActivity(activity)) {
                    result.setPage(Path.USER_CABINET);
                }
                //todo validate
            } else {
                errorMessage = "User was not found";
                request.setAttribute("errorMessage", errorMessage);
                result.setPage(Path.ERROR);
                return result;
            }
        } catch (Exception e) {
            log.error(e);
            errorMessage = "Can not add activity";
            request.setAttribute("errorMessage", errorMessage);
            result.setPage(Path.ERROR);
        }
        return result;
    }
}
