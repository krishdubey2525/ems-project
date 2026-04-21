package com.course.ems_backend.controller;

import com.course.ems_backend.dto.DashBoardStatsDto;
import com.course.ems_backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public ResponseEntity<DashBoardStatsDto> getAppStats() {
        long empCount = employeeRepository.countTotalEmployees();
        long deptCount = employeeRepository.countTotalDepartments();

        DashBoardStatsDto stats = new DashBoardStatsDto(empCount, deptCount);
        return ResponseEntity.ok(stats);
    }
}
