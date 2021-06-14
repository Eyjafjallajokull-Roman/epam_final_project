package com.epam.project.command.imp;

import com.epam.project.util.TimeParser;
import com.epam.project.command.Command;
import com.epam.project.constants.ErrorConfig;
import com.epam.project.constants.ErrorConst;
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
import java.util.ArrayList;
import java.util.List;


public class AddNewActivityCommand implements Command {
    private static final Logger log = Logger.getLogger(AddNewActivityCommand.class);
    private static final List<String> typesOfActivity;

    static {
        typesOfActivity = new ArrayList<>();
        typesOfActivity.add("REMINDER");
        typesOfActivity.add("EVENT");
        typesOfActivity.add("all");
        typesOfActivity.add("TASK");
        typesOfActivity.add("TIME_TRACKER");
    }

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        HttpSession session = request.getSession();
        ErrorConfig error = ErrorConfig.getInstance();

        try {
            User user = (User) session.getAttribute("user");
            String typeOfActivity = request.getParameter("typeOfActivity");
            String name = request.getParameter("name");
            String descriptionEn = request.getParameter("description_en");
            String descriptionRu = request.getParameter("description_ru");
            int created_by = user.getId();
            Activity activity = new Activity();
            ActivityService activityService = ServiceFactory.getActivityService();
            log.info("GetAllParams in AddNewActivityCommand");

            if (typeOfActivity.equals("REMINDER")) {
                String endTime = TimeParser.parseTimeFromJsp(request.getParameter("end_time"));
                log.info("Type of activity :" + typeOfActivity);
                activity.setEndTime(Timestamp.valueOf(endTime));
            }
            else if (typeOfActivity.equals("TIME_TRACKER")) {
                log.info("Type of activity :" + typeOfActivity);
            } else {
                String startTime = TimeParser.parseTimeFromJsp(request.getParameter("start_time"));
                String endTime = TimeParser.parseTimeFromJsp(request.getParameter("end_time"));
                activity.setEndTime(Timestamp.valueOf(endTime));
                activity.setStartTime(Timestamp.valueOf(startTime));
                log.info("Type of activity :" + typeOfActivity);
            }

            activity.setDescriptionEng(descriptionEn);
            activity.setDescriptionRus(descriptionRu);
            activity.setTypeOfActivity(TypeOfActivity.valueOf(typeOfActivity));
            activity.setName(name);
            activity.setStatus(Status.ON_CHECK);
            activity.setCreatedByUserID(created_by);

            if (activityService.addActivity(activity)) {
                result.setDirection(Direction.REDIRECT);
                result.setPage(Path.USER_CABINET);
                log.info("Activity has been created witn name:" + activity.getName());
            } else {
                log.error("Wrong Input Data");
                request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.WRONG_INPUT_TIME));
                result.setPage(Path.ERROR_FWD);
            }

        } catch (NullPointerException e) {
            log.error(e);
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.Data_WAS_NOT_FOUND));
            result.setPage(Path.ERROR_FWD);
        }
        return result;
    }
}
