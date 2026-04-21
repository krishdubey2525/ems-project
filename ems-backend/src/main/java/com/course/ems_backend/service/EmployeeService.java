package com.course.ems_backend.service;

import com.course.ems_backend.dto.Employeedto;

import java.util.List;

public interface EmployeeService {
    Employeedto  createEmployee(Employeedto employeedto);
    Employeedto  getEmployeeById(Long employeeId);
    List<Employeedto> getAllEmployees();
    Employeedto updateEmployee(Long employeeId,Employeedto updatedEmployee);
     void deleteEmployee(Long employeeId);
}
