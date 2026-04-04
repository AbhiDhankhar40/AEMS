package com.test.demo.serviceImpl;

import com.test.demo.model.EventPhotos;
import com.test.demo.repository.EventPhotosRepository;
import com.test.demo.service.EventPhotosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventPhotosServiceImpl implements EventPhotosService {

    private final EventPhotosRepository eventPhotosRepository;

    @Override
    public EventPhotos saveEventPhotos(EventPhotos eventPhotos) {
        return eventPhotosRepository.save(eventPhotos);
    }

    @Override
    public EventPhotos updateEventPhotos(Long id, EventPhotos eventPhotos) {
        EventPhotos existing = getEventPhotosById(id);
        
        existing.setEventId(eventPhotos.getEventId());
        existing.setPoster(eventPhotos.getPoster());
        existing.setGeoTag(eventPhotos.getGeoTag());
        existing.setBanner(eventPhotos.getBanner());
        existing.setPic1(eventPhotos.getPic1());
        existing.setPic2(eventPhotos.getPic2());
        existing.setPic3(eventPhotos.getPic3());
        
        return eventPhotosRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public EventPhotos getEventPhotosByEventId(Long eventId) {
        return eventPhotosRepository.findByEventId(eventId)
                .orElseThrow(() -> new RuntimeException("Photos not found for event id: " + eventId));
    }

    @Override
    @Transactional(readOnly = true)
    public EventPhotos getEventPhotosById(Long id) {
        return eventPhotosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event photos not found with id: " + id));
    }

    @Override
    public void deleteEventPhotos(Long id) {
        eventPhotosRepository.deleteById(id);
    }
}