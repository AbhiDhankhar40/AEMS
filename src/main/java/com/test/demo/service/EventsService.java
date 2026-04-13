package com.test.demo.service;

import com.test.demo.dto.EventShortResponseDTO;
import com.test.demo.dto.EventCountDTO;
import com.test.demo.dto.EventResponseDTO;
import com.test.demo.model.Events;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface EventsService {
    
    Events createEvent(Events event, MultipartFile poster, MultipartFile geoTag, MultipartFile banner, MultipartFile pic1, MultipartFile pic2, MultipartFile pic3) throws IOException;
    
    List<Events> createEvents(List<Events> events);
    
    Events updateEvent(Long id, Events event, MultipartFile poster, MultipartFile geoTag, MultipartFile banner, MultipartFile pic1, MultipartFile pic2, MultipartFile pic3) throws IOException;
    
    Events getEventById(Long id);
    
    List<EventResponseDTO> getAllEvents();
    
    void deleteEvent(Long id);

    List<Events> getEventsByIds(List<Long> ids);

    List<EventResponseDTO> getEventsByUserId(Long userId);

    EventCountDTO getEventCountsByUserId(Long userId);

    List<EventShortResponseDTO> getEventsByClubIds(List<Integer> clubIds);
}