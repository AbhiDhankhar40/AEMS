package com.test.demo.controller;

import com.test.demo.dto.EventResponseDTO;
import com.test.demo.model.Events;
import com.test.demo.service.EventsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventsController {

    private final EventsService eventsService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Events> createEvent(
            @RequestPart("event") Events event,
            @RequestPart(value = "poster", required = false) MultipartFile poster,
            @RequestPart(value = "geoTag", required = false) MultipartFile geoTag,
            @RequestPart(value = "banner", required = false) MultipartFile banner,
            @RequestPart(value = "pic1", required = false) MultipartFile pic1,
            @RequestPart(value = "pic2", required = false) MultipartFile pic2,
            @RequestPart(value = "pic3", required = false) MultipartFile pic3) throws IOException {
        
        return ResponseEntity.ok(eventsService.createEvent(event, poster, geoTag, banner, pic1, pic2, pic3));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Events>> createEvents(@RequestBody List<Events> events) {
        return ResponseEntity.ok(eventsService.createEvents(events));
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getAllEvents() {
        return ResponseEntity.ok(eventsService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Events> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventsService.getEventById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Events> updateEvent(@PathVariable Long id, @RequestBody Events event) {
        return ResponseEntity.ok(eventsService.updateEvent(id, event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventsService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}