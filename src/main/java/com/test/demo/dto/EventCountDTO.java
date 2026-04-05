package com.test.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventCountDTO {
    private long total;
    private long upcoming;
    private long completed;
}