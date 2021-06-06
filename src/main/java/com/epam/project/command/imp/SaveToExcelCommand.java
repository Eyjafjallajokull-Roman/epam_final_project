package com.epam.project.command.imp;

import com.epam.project.command.Command;
import com.epam.project.constants.ErrorConfig;
import com.epam.project.constants.Path;
import com.epam.project.controller.Direction;
import com.epam.project.controller.ResultOfExecution;
import com.epam.project.entity.User;
import com.epam.project.exception.NoUserException;
import com.epam.project.service.ServiceFactory;
import com.epam.project.service.UserService;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class SaveToExcelCommand implements Command {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SaveToExcelCommand.class);

    @Override
    public ResultOfExecution execute(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getHeader("referer").replaceFirst("http://localhost:8080", "");
        ResultOfExecution result = new ResultOfExecution();
        HttpSession session = request.getSession();
        ErrorConfig error = ErrorConfig.getInstance();
        try {
            UserService userService = ServiceFactory.getUserService();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=UserList.xls");
            XSSFWorkbook wb = new XSSFWorkbook();


            Sheet sheets = wb.createSheet("list");
            List<User> users = userService.findAllUsers();

            int rowNo = 0;
            Row row = sheets.createRow(rowNo);
            int cellNum = 0;
            Cell cell = row.createCell(cellNum++);
            cell.setCellValue("User Name");
            cell = row.createCell(cellNum++);
            cell.setCellValue("Surname");
            cell = row.createCell(cellNum++);
            cell.setCellValue("Email");

            //2 details
            for (User user : users) {
                cellNum = 0;
                row = sheets.createRow(rowNo++);

                cell = row.createCell(cellNum++);
                cell.setCellValue(user.getName());
                cell = row.createCell(cellNum++);
                cell.setCellValue(user.getSurname());
                cell = row.createCell(cellNum++);
                cell.setCellValue(user.getEmail());
            }

            wb.write(response.getOutputStream());
            wb.close();


            return result;
        } catch (NoUserException | IOException e) {
            log.info("SEKH");
        }
        return result;
    }
}
