package com.test.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventShortResponseDTO {
    private Long eventId;
    private String eventName;
    private Integer clubId;
    private String clubName;
}