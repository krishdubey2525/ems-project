package com.course.ems_backend.repository;

import com.course.ems_backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    // This gets the total count of rows in the employee table
    @Query("SELECT count(e) FROM Employee e")
    long countTotalEmployees();

    // This counts how many unique departments are assigned to employees
    // If you have a separate Department table, we can also count that repository later
    @Query("SELECT count(distinct e.department) FROM Employee e")
    long countTotalDepartments();
}
