package com.test.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClubResponseDTO {
    private Long id;
    private String name;
    private Integer department;
    private String departmentName;
    private String block;
    private String advisorName;
    private String advisorDesignation;
    private Long advisorMobile;
    private String advisorEmail;
    private String coordinatorName;
    private String coordinatorDesignation;
    private String coordinatorEmail;
    private Long coordinatorMobile;
    private String coordinator2Name;
    private String coordinator2Designation;
    private String coordinator2Email;
    private Long coordinator2Mobile;
    private String president;
    private Long presidentMobile;
    private String vicePresident;
    private String secretary;
    private String jointSecretary;
    private String docCurator;
    private String creativeHead;
    private String treasurer;
    private String memberName;
    private String status;
}