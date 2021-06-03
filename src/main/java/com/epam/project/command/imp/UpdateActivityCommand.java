package com.epam.project.command.imp;

import com.epam.project.TimeParser;
import com.epam.project.command.Command;
import com.epam.project.constants.ErrorConfig;
import com.epam.project.constants.ErrorConst;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.Activity;
import com.epam.project.entity.Status;
import com.epam.project.entity.TypeOfActivity;
import com.epam.project.entity.User;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

public class UpdateActivityCommand implements Command {
    private static final Logger log = Logger.getLogger(UpdateActivityCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        ResultOfExecution result = new ResultOfExecution();
        result.setDirection(Direction.FORWARD);
        HttpSession session = request.getSession();
        ErrorConfig error = ErrorConfig.getInstance();
        try {

            ActivityService activityService = ServiceFactory.getActivityService();
            User user = (User) session.getAttribute("user");
            if (user != null) {
                String name = request.getParameter("name");
                String typeOfActivity = request.getParameter("typeOfActivity");
                String descriptionEn = request.getParameter("description_en");
                String descriptionRu = request.getParameter("description_ru");
                String startTime = TimeParser.parseTimeFromJsp(request.getParameter("start_time"));
                String endTime = TimeParser.parseTimeFromJsp(request.getParameter("end_time"));
                Integer id = Integer.valueOf(request.getParameter("id"));
                int created_by = user.getId();


                //old Activity
                Activity activityOld = activityService.findActivityById(id);
                activityOld.setStatus(Status.WAIT);


                //newActivity
                Activity activityNew = new Activity();
                activityNew.setCreatedByUserID(created_by);
                activityNew.setStartTime(Timestamp.valueOf(startTime));
                activityNew.setEndTime(Timestamp.valueOf(endTime));
                activityNew.setDescriptionEng(descriptionEn);
                activityNew.setDescriptionRus(descriptionRu);
                activityNew.setTypeOfActivity(TypeOfActivity.valueOf(typeOfActivity));
                activityNew.setName(name);
                activityNew.setStatus(Status.ON_UPDATE);
                //reference what activity we update
                activityNew.setOldActivityId(id);


                if (activityService.addActivity(activityNew) && activityService.updateActivityWithoutValidation(activityOld)) {
                    result.setDirection(Direction.REDIRECT);
                    result.setPage(Path.USER_CABINET);
                } else {
                    request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.WRONG_INPUT_TIME));
                    result.setPage(Path.ERROR_FWD);
                }
            } else {
                request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.UNABLE_TO_FOUND_USER));
                result.setPage(Path.ERROR_FWD);
                return result;
            }
        } catch (NoSuchActivityException e) {
            request.setAttribute("errorMessage", error.getErrorMessage(ErrorConst.UNABLE_TO_FOUND_USER));
            result.setPage(Path.ERROR_FWD);
            return result;
        }
        return result;
    }
}

