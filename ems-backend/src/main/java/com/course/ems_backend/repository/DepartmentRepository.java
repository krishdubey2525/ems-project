package com.course.ems_backend.repository;

import com.course.ems_backend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository  extends JpaRepository<Department,Long> {
}
