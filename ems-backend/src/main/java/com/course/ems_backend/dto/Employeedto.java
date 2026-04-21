package com.course.ems_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employeedto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Long departmentId;
    private String assignedTask; // Must match the key name in React
    private String deadline;
    private String taskStatus;
}
