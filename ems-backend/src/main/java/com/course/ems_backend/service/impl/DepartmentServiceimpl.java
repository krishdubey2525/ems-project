package com.course.ems_backend.service.impl;

import com.course.ems_backend.dto.DepartmentDto;
import com.course.ems_backend.entity.Department;
import com.course.ems_backend.exception.ResourceNotFoundException;
import com.course.ems_backend.mapper.DepartmentMapper;
import com.course.ems_backend.repository.DepartmentRepository;
import com.course.ems_backend.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DepartmentServiceimpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        Department department= DepartmentMapper.mapToDepartment(departmentDto);
        Department savedDepartment =departmentRepository.save(department);
        return DepartmentMapper.mapToDepartmentDto(savedDepartment);
    }

    @Override
    public DepartmentDto getDepartmentById(Long departmentId) {
        Department department=  departmentRepository.findById(departmentId).orElseThrow(
                ()->new  ResourceNotFoundException("Department is not exists with given id:"+departmentId)
        );
        return DepartmentMapper.mapToDepartmentDto(department);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments=departmentRepository.findAll();
        return departments.stream().map(department -> DepartmentMapper.mapToDepartmentDto(department))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto updatedDepartment) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department does not exist with id: " + departmentId));

        department.setDepartmentName(updatedDepartment.getDepartmentName()); // ✅ Correct field
        department.setDepartmentDescription(updatedDepartment.getDepartmentDescription()); // ✅ Set description too

        Department savedDepartment = departmentRepository.save(department);

        return DepartmentMapper.mapToDepartmentDto(savedDepartment);
    }


    @Override
    public void deleteDepartment(Long departmentId) {
        departmentRepository.findById(departmentId).orElseThrow(
                ()->new ResourceNotFoundException("Department is not exists with a given id: "+departmentId)
        );
        departmentRepository.deleteById(departmentId);
    }
}
