package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.ErrorConfig;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.Activity;
import com.epam.project.entity.Role;
import com.epam.project.entity.User;
import com.epam.project.exception.NoSuchActivityException;
import com.epam.project.exception.NoUserException;
import com.epam.project.service.ActivityService;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SaveToExcelCommand implements Command {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SaveToExcelCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getHeader("referer").replaceFirst("http://localhost:8080", "");
        ResultOfExecution result = new ResultOfExecution();
        HttpSession session = request.getSession();
        ErrorConfig error = ErrorConfig.getInstance();
        List<Activity> activities;
        try {
            User sessionUser = (User) session.getAttribute("user");
            UserService userService = ServiceFactory.getUserService();
            ActivityService activityService = ServiceFactory.getActivityService();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=ActivitiesList.xls");
            XSSFWorkbook wb = new XSSFWorkbook();
            Sheet sheets = wb.createSheet("Users");
            sheets.setDefaultColumnWidth(30);
            Row row = sheets.createRow(0);
            int indexRow = 1;

            if (sessionUser.getRole().equals(Role.ADMIN)) {
                activities = activityService.findAllActivity();
            } else {
                activities = activityService.findActivitiesWhereCreatedIdWithoutLimits(sessionUser.getId(), "activity.name");
            }

            List<String> activitiesAttr = Arrays.asList(
                    "Name", "DescriptionEng", "DescriptionRus", "StartTime", "EndTime", "TypeOfActivity", "CreatedBy");
            createHeaderRow(activitiesAttr, row, wb);
            row = sheets.createRow(indexRow++);
            for (Activity a : activities) {

                row.createCell(0).setCellValue(a.getName());
                row.createCell(1).setCellValue(a.getDescriptionEng());
                row.createCell(2).setCellValue(a.getDescriptionRus());
                row.createCell(3).setCellValue(a.getStartTime().toString());
                row.createCell(4).setCellValue(a.getEndTime().toString());
                row.createCell(5).setCellValue(a.getTypeOfActivity().name());
                row.createCell(6).setCellValue(userService.findUserById(a.getCreatedByUserID()).getEmail());
                row = sheets.createRow(indexRow++);

            }

//            int rowNo = 0;
//            row = sheets.createRow(rowNo);
//            int cellNum = 0;
//            Cell cell = row.createCell(cellNum++);
//            cell.setCellValue("User Name");
//            cell = row.createCell(cellNum++);
//            cell.setCellValue("Surname");
//            cell = row.createCell(cellNum++);
//            cell.setCellValue("Email");
//
//            //2 details
//            for (User user : users) {
//                cellNum = 0;
//                row = sheets.createRow(rowNo++);
//
//                cell = row.createCell(cellNum++);
//                cell.setCellValue(user.getName());
//                cell = row.createCell(cellNum++);
//                cell.setCellValue(user.getSurname());
//                cell = row.createCell(cellNum++);
//                cell.setCellValue(user.getEmail());
//            }

            wb.write(response.getOutputStream());
            wb.close();


            return result;
        } catch (NoSuchActivityException | IOException | NoUserException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void createHeaderRow(List<String> args, Row row, Workbook workbook) {
        for (int i = 0; i < args.size(); i++) {
            row.createCell(i).setCellValue(args.get(i));
            row.getCell(i).setCellStyle(getHeaderStyle(workbook));
        }
    }

    private CellStyle getHeaderStyle(Workbook workbook) {
        Font fontBold = workbook.createFont();
        fontBold.setFontName("Arial");
        fontBold.setBold(true);
        fontBold.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LEMON_CHIFFON.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFont(fontBold);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        return headerStyle;
    }


}
