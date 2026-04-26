package com.test.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.demo.model.Department;
import com.test.demo.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/add")
    public ResponseEntity<DepartmentResponse> createDepartment(@RequestBody DepartmentRequest request) {
        Department department = departmentService.createDepartment(mapToEntity(request));
        return ResponseEntity.ok(mapToResponse(department));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<DepartmentResponse>> createDepartments(@RequestBody List<DepartmentRequest> requests) {
        List<Department> departments = requests.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(departmentService.createDepartments(departments).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(@PathVariable Long id,
                                                               @RequestBody DepartmentRequest request) {
        Department department = departmentService.updateDepartment(id, mapToEntity(request));
        return ResponseEntity.ok(mapToResponse(department));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(mapToResponse(departmentService.getDepartmentById(id)));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getActiveDepartments().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    private Department mapToEntity(DepartmentRequest request) {
        Department department = new Department();
        department.setDepartmentName(request.departmentName());
        department.setStatus("active".equalsIgnoreCase(request.status()));
        return department;
    }

    private DepartmentResponse mapToResponse(Department department) {
        return new DepartmentResponse(
                department.getId(),
                department.getDepartmentName(),
                department.isStatus() ? "Active" : "Inactive"
        );
    }

    public record DepartmentRequest(String departmentName, String status) {}

    public record DepartmentResponse(Long id, String departmentName, String status) {}
}
