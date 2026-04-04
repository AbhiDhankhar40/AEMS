package com.test.demo.serviceImpl;

import com.test.demo.dto.EventResponseDTO;
import com.test.demo.model.EventPhotos;
import com.test.demo.model.Events;
import com.test.demo.repository.EventsRepository;
import com.test.demo.service.ClubService;
import com.test.demo.service.DepartmentService;
import com.test.demo.service.EventPhotosService;
import com.test.demo.service.EventsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EventsServiceImpl implements EventsService {

    private final EventsRepository eventsRepository;
    private final EventPhotosService eventPhotosService;
    private final DepartmentService departmentService;
    private final ClubService clubService;

    @Override
    public Events createEvent(Events event, MultipartFile poster, MultipartFile geoTag, MultipartFile banner, MultipartFile pic1, MultipartFile pic2, MultipartFile pic3) throws IOException {
        Events savedEvent = eventsRepository.save(event);

        EventPhotos photos = EventPhotos.builder()
                .eventId(savedEvent.getId())
                .poster(poster != null ? Base64.getEncoder().encodeToString(poster.getBytes()) : null)
                .geoTag(geoTag != null ? Base64.getEncoder().encodeToString(geoTag.getBytes()) : null)
                .banner(banner != null ? Base64.getEncoder().encodeToString(banner.getBytes()) : null)
                .pic1(pic1 != null ? Base64.getEncoder().encodeToString(pic1.getBytes()) : null)
                .pic2(pic2 != null ? Base64.getEncoder().encodeToString(pic2.getBytes()) : null)
                .pic3(pic3 != null ? Base64.getEncoder().encodeToString(pic3.getBytes()) : null)
                .build();

        eventPhotosService.saveEventPhotos(photos);
        
        return savedEvent;
    }

    @Override
    public List<Events> createEvents(List<Events> events) {
        return eventsRepository.saveAll(events);
    }

    @Override
    public Events updateEvent(Long id, Events event) {
        Events existing = getEventById(id);
        
        existing.setTitle(event.getTitle());
        existing.setOrganiser(event.getOrganiser());
        existing.setDepartment(event.getDepartment());
        existing.setDate(event.getDate());
        existing.setTime(event.getTime());
        existing.setVenue(event.getVenue());
        existing.setObjective(event.getObjective());
        existing.setSpeakers(event.getSpeakers());
        existing.setParticipants(event.getParticipants());
        existing.setProgrammeSchedule(event.getProgrammeSchedule());
        existing.setHighLights(event.getHighLights());
        existing.setPurpose(event.getPurpose());
        existing.setOutcome(event.getOutcome());
        existing.setFeedback(event.getFeedback());
        existing.setBudget(event.getBudget());
        existing.setConclusion(event.getConclusion());
        existing.setAcknowledgement(event.getAcknowledgement());
        existing.setStatus(event.getStatus());

        return eventsRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Events getEventById(Long id) {
        return eventsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponseDTO> getAllEvents() {
        return eventsRepository.findAll().stream().map(event -> {
            String deptName = "";
            if (event.getDepartment() != null) {
                try {
                    deptName = departmentService.getDepartmentById(event.getDepartment().longValue()).getDepartmentName();
                } catch (Exception e) {}
            }

            String clubName = "";
            if (event.getClubId() != null) {
                try {
                    clubName = clubService.getClubById(event.getClubId().longValue()).getName();
                } catch (Exception e) {}
            }

            String banner = null;
            String poster = null;
            try {
                EventPhotos photos = eventPhotosService.getEventPhotosByEventId(event.getId());
                banner = photos.getBanner();
                poster = photos.getPoster();
            } catch (Exception e) {}

            return EventResponseDTO.builder()
                    .eventName(event.getTitle())
                    .organizer(event.getOrganiser())
                    .departmentName(deptName)
                    .clubName(clubName)
                    .date(event.getDate())
                    .time(event.getTime())
                    .venue(event.getVenue())
                    .highlights(event.getHighLights())
                    .purpose(event.getPurpose())
                    .banner(banner)
                    .poster(poster)
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteEvent(Long id) {
        eventsRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Events> getEventsByIds(List<Long> ids) {
        return eventsRepository.findAllByIdIn(ids);
    }
}