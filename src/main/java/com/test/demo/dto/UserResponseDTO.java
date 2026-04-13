package com.test.demo.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class UserResponseDTO {
    private Long id;
    private String username;
    private String userType;
    private String name;
    private Integer department;
    private String departmentName;
    private String status;
    private List<Integer> clubIds;
    private List<String> clubNames;
}