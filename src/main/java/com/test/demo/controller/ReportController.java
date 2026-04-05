package com.test.demo.controller;

import com.test.demo.model.Events;
import com.test.demo.service.EventsService;
import com.test.demo.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports") // Base path for report-related APIs
@RequiredArgsConstructor
public class ReportController {

    private final EventsService eventsService;
    private final ReportService reportService;

    @PostMapping("/events/excel")
    public ResponseEntity<byte[]> downloadEventsExcel(@RequestBody List<Long> eventIds) {
        List<Events> events = eventsService.getEventsByIds(eventIds);
        byte[] data = reportService.generateEventsExcel(events);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=events_report.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
    }
}
