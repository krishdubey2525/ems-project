package com.course.ems_backend.service;

import com.course.ems_backend.dto.DepartmentDto;
import com.course.ems_backend.entity.Department;

import java.util.List;

public interface DepartmentService {

    DepartmentDto createDepartment(DepartmentDto departmentDto);

    DepartmentDto getDepartmentById(Long departmentId);

    List<DepartmentDto> getAllDepartments();

    DepartmentDto updateDepartment(Long departmentId,DepartmentDto updatedDepartment);
    void deleteDepartment(Long departmentId);

}
