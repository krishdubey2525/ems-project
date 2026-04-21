package com.course.ems_backend.util;

import com.course.ems_backend.entity.Employee;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelHelper {

    // ✅ Updated HEADERS to include the new columns
    public static String[] HEADERS = { "Id", "First Name", "Last Name", "Email", "Assigned Task", "Deadline", "Status" };
    public static String SHEET = "Employees";

    public static ByteArrayInputStream employeesToExcel(List<Employee> employees) throws IOException {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet(SHEET);

            // Create a Header Row
            Row headerRow = sheet.createRow(0);

            CellStyle headerCellStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerCellStyle.setFont(headerFont);

            for (int col = 0; col < HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERS[col]);
                cell.setCellStyle(headerCellStyle);
            }

            // Fill Data Rows
            int rowIdx = 1;
            for (Employee employee : employees) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(employee.getId());
                row.createCell(1).setCellValue(employee.getFirstname());
                row.createCell(2).setCellValue(employee.getLastname());
                row.createCell(3).setCellValue(employee.getEmail());

                // ✅ Add the new fields to cells 4, 5, and 6
                row.createCell(4).setCellValue(employee.getAssignedTask() != null ? employee.getAssignedTask() : "N/A");

                // Handle Date (convert LocalDate to String for Excel)
                String deadlineStr = (employee.getDeadline() != null) ? employee.getDeadline().toString() : "N/A";
                row.createCell(5).setCellValue(deadlineStr);

                row.createCell(6).setCellValue(employee.getTaskStatus() != null ? employee.getTaskStatus() : "PENDING");
            }

            // Auto-size all columns (now 0 to 6)
            for (int i = 0; i < HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}