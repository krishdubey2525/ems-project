package com.course.ems_backend.controller;

import com.course.ems_backend.entity.Employee;
import com.course.ems_backend.repository.EmployeeRepository;
import com.course.ems_backend.util.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*") // Essential for React to connect
@RestController
@RequestMapping("/api/reports")

public class ReportController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/excel")
    public ResponseEntity<Resource> getEmployeesExcel() throws IOException {
        String filename = "employees_list.xlsx";

        // 1. Fetch data from database
        List<Employee> employees = employeeRepository.findAll();

        // 2. Convert data to Excel stream using our Helper
        InputStreamResource file = new InputStreamResource(ExcelHelper.employeesToExcel(employees));

        // 3. Return as a downloadable file stream
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }
}
