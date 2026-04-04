package com.test.demo.service;

import com.test.demo.model.EventPhotos;

public interface EventPhotosService {
    
    EventPhotos saveEventPhotos(EventPhotos eventPhotos);
    
    EventPhotos updateEventPhotos(Long id, EventPhotos eventPhotos);
    
    EventPhotos getEventPhotosByEventId(Long eventId);
    
    EventPhotos getEventPhotosById(Long id);
    
    void deleteEventPhotos(Long id);
}