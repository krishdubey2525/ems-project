package com.course.ems_backend.service.impl;

import com.course.ems_backend.dto.Employeedto;
import com.course.ems_backend.entity.Department;
import com.course.ems_backend.entity.Employee;
import com.course.ems_backend.exception.ResourceNotFoundException;
import com.course.ems_backend.mapper.EmployeeMapper;
import com.course.ems_backend.repository.DepartmentRepository;
import com.course.ems_backend.repository.EmployeeRepository;
import com.course.ems_backend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;

    @Override
    public Employeedto createEmployee(Employeedto employeedto) {
        // Mapper already handles assignedTask, deadline, and taskStatus conversion
        Employee employee = EmployeeMapper.mapToEmployee(employeedto);

        Department department = departmentRepository.findById(employeedto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department does not exist with id: " + employeedto.getDepartmentId()));

        employee.setDepartment(department);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public Employeedto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with given id does not exist: " + employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<Employeedto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());
    }

    @Override
    public Employeedto updateEmployee(Long employeeId, Employeedto updatedEmployee) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee does not exist with given id: " + employeeId)
        );

        // Update basic info
        employee.setFirstname(updatedEmployee.getFirstname());
        employee.setLastname(updatedEmployee.getLastname());
        employee.setEmail(updatedEmployee.getEmail());

        // Update Task and Deadline
        employee.setAssignedTask(updatedEmployee.getAssignedTask());

        // ✅ Update Task Status
        employee.setTaskStatus(updatedEmployee.getTaskStatus());

        // Safety check for date parsing
        if (updatedEmployee.getDeadline() != null && !updatedEmployee.getDeadline().isEmpty()) {
            employee.setDeadline(LocalDate.parse(updatedEmployee.getDeadline()));
        } else {
            employee.setDeadline(null);
        }

        // Update Department association
        Department department = departmentRepository.findById(updatedEmployee.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department does not exist with id: " + updatedEmployee.getDepartmentId()));
        employee.setDepartment(department);

        Employee updatedObj = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updatedObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee does not exist with given id: " + employeeId)
        );
        employeeRepository.deleteById(employeeId);
    }
}