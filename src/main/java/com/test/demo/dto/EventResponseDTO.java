package com.test.demo.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class EventResponseDTO {
    private String eventName;
    private String organizer;
    private String departmentName;
    private String clubName;
    private LocalDate date;
    private LocalTime time;
    private String venue;
    private String highlights;
    private String purpose;
    private String banner;
    private String poster;
}