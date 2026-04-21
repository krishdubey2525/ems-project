package com.course.ems_backend.mapper;

import com.course.ems_backend.dto.Employeedto;
import com.course.ems_backend.entity.Employee;
import java.time.LocalDate;

public class EmployeeMapper {

    public static Employeedto mapToEmployeeDto(Employee employee) {
        Long departmentId = (employee.getDepartment() != null) ? employee.getDepartment().getId() : null;

        return new Employeedto(
                employee.getId(),
                employee.getFirstname(),
                employee.getLastname(),
                employee.getEmail(),
                departmentId,
                employee.getAssignedTask(),
                // Converts LocalDate to String so it fits your DTO constructor
                employee.getDeadline() != null ? employee.getDeadline().toString() : null, // ✅ Added missing comma
                employee.getTaskStatus() // ✅ Added status
        );
    }

    public static Employee mapToEmployee(Employeedto employeedto) {
        Employee employee = new Employee();
        employee.setId(employeedto.getId());
        employee.setFirstname(employeedto.getFirstname());
        employee.setLastname(employeedto.getLastname());
        employee.setEmail(employeedto.getEmail());
        employee.setAssignedTask(employeedto.getAssignedTask());
        employee.setTaskStatus(employeedto.getTaskStatus()); // ✅ Added status for creation logic

        // Converts String back to LocalDate for the Entity/Database
        if (employeedto.getDeadline() != null && !employeedto.getDeadline().isEmpty()) {
            employee.setDeadline(LocalDate.parse(employeedto.getDeadline()));
        }

        return employee;
    }
}