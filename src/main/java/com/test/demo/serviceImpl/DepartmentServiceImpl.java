package com.test.demo.serviceImpl;

import com.test.demo.model.Department;
import com.test.demo.repository.DepartmentRepository;
import com.test.demo.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> createDepartments(List<Department> departments) {
        return departmentRepository.saveAll(departments);
    }

    @Override
    public Department updateDepartment(Long id, Department department) {
        Department existing = getDepartmentById(id);
        existing.setDepartmentName(department.getDepartmentName());
        existing.setStatus(department.isStatus());
        return departmentRepository.save(existing);
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
    }

    @Override
    public List<Department> getActiveDepartments() {
        return departmentRepository.findByStatusTrue();
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}