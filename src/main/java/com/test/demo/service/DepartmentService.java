package com.test.demo.service;

import com.test.demo.model.Department;
import java.util.List;

public interface DepartmentService {
    Department createDepartment(Department department);
    List<Department> createDepartments(List<Department> departments);
    Department updateDepartment(Long id, Department department);
    Department getDepartmentById(Long id);
    List<Department> getActiveDepartments();
    void deleteDepartment(Long id);
}