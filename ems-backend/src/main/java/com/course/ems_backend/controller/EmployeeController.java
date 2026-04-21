package com.course.ems_backend.controller;

import com.course.ems_backend.dto.Employeedto;
import com.course.ems_backend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    //Build and create rest api
    @PostMapping
    public ResponseEntity<Employeedto> createEmployee(@RequestBody Employeedto employeedto){
        Employeedto savedemployee=employeeService.createEmployee(employeedto);
        return  new ResponseEntity<>(savedemployee,HttpStatus.CREATED);
    }

    //build get employee rest api
    @GetMapping("{id}")
    public ResponseEntity<Employeedto> getEmployeeBYiD(@PathVariable("id") Long employeeId){
        Employeedto employeedto=employeeService.getEmployeeById(employeeId);
        return new ResponseEntity<>(employeedto,HttpStatus.OK);
    }
    //get all employee rest api
    @GetMapping
    public ResponseEntity<List<Employeedto>> getAllEmployees(){
        List<Employeedto> employees=employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
    //build update employee rest api
    @PutMapping("{id}")
    public ResponseEntity<Employeedto> updateEmployee(@PathVariable("id") Long employeeId,@RequestBody Employeedto updatedEmployee){
        Employeedto employeedto=employeeService.updateEmployee(employeeId,updatedEmployee);
        return ResponseEntity.ok(employeedto);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId){
        employeeService.deleteEmployee(employeeId);
        return   ResponseEntity.ok("employee deleted successfully");
    }



}
