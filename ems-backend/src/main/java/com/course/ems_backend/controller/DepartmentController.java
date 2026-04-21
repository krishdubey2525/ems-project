package com.course.ems_backend.controller;

import com.course.ems_backend.dto.DepartmentDto;
import com.course.ems_backend.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private DepartmentService departmentService;

    //Build Add department Rest Api
    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto){
        DepartmentDto department= departmentService.createDepartment(departmentDto);
        return  new ResponseEntity<>(department, HttpStatus.CREATED);
    }
    //Build get department rest api
    @GetMapping("{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById( @PathVariable("id") Long departmentId){
            DepartmentDto departmentDto=departmentService.getDepartmentById(departmentId);
            return ResponseEntity.ok(departmentDto);
    }
    //BUILD  get all  department rest api
    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        List<DepartmentDto> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }
    //build update department rest api
    @PutMapping("{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable("id") Long departmentId, @RequestBody DepartmentDto updatedDepartment){
        DepartmentDto departmentDto=departmentService.updateDepartment(departmentId,updatedDepartment);
        return ResponseEntity.ok(departmentDto);
    }

    //build delete department rest api
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable("id") Long departmentId){
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok("department deleted successfully!");
    }

}
