package com.test.demo.serviceImpl;

import com.test.demo.dto.EventShortResponseDTO;
import com.test.demo.dto.EventCountDTO;
import com.test.demo.dto.EventResponseDTO;
import com.test.demo.model.Club;
import com.test.demo.model.Department;
import com.test.demo.model.EventPhotos;
import com.test.demo.model.Events;
import com.test.demo.repository.ClubRepository;
import com.test.demo.repository.DepartmentRepository;
import com.test.demo.repository.EventPhotosRepository;
import com.test.demo.repository.EventsRepository;
import com.test.demo.repository.UserMasterRepository;
import com.test.demo.model.UserMaster;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EventsServiceImpl implements EventsService {

    private final EventsRepository eventsRepository;
    private final DepartmentRepository departmentRepository;
    private final ClubRepository clubRepository;
    private final EventPhotosRepository eventPhotosRepository;
    private final UserMasterRepository userMasterRepository;
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
        return eventsRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventResponseDTO> getEventsByUserId(Long userId) {
        UserMaster user = userMasterRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Events> events;
        String userType = user.getUserType();

        if ("Super Admin".equalsIgnoreCase(userType)) {
            events = eventsRepository.findTop10ByOrderByIdDesc();
        } else if ("Admin".equalsIgnoreCase(userType)) {
            events = eventsRepository.findByDepartmentOrderByIdDesc(user.getDepartment());
        } else {
            events = eventsRepository.findTop10ByClubIdInOrderByIdDesc(user.getClubIds());
        }

        return events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EventCountDTO getEventCountsByUserId(Long userId) {
        UserMaster user = userMasterRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String userType = user.getUserType();
        long total, upcoming, completed;

        if ("Super Admin".equalsIgnoreCase(userType)) {
            total = eventsRepository.count();
            upcoming = eventsRepository.countByStatus("Upcoming");
            completed = eventsRepository.countByStatus("Completed");
        } else if ("Admin".equalsIgnoreCase(userType)) {
            total = eventsRepository.countByDepartment(user.getDepartment());
            upcoming = eventsRepository.countByDepartmentAndStatus(user.getDepartment(), "Upcoming");
            completed = eventsRepository.countByDepartmentAndStatus(user.getDepartment(), "Completed");
        } else {
            total = eventsRepository.countByClubIdIn(user.getClubIds());
            upcoming = eventsRepository.countByClubIdInAndStatus(user.getClubIds(), "Upcoming");
            completed = eventsRepository.countByClubIdInAndStatus(user.getClubIds(), "Completed");
        }

        return EventCountDTO.builder().total(total).upcoming(upcoming).completed(completed).build();
    }

    private EventResponseDTO convertToDTO(Events event) {
        String deptName = Optional.ofNullable(event.getDepartment())
                .flatMap(id -> departmentRepository.findById(id.longValue()))
                .map(Department::getDepartmentName)
                .orElse("");

        String clubName = Optional.ofNullable(event.getClubId())
                .flatMap(id -> clubRepository.findById(id.longValue()))
                .map(Club::getName)
                .orElse("");

        Optional<EventPhotos> photos = eventPhotosRepository.findByEventId(event.getId());

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
                .banner(photos.map(EventPhotos::getBanner).orElse(null))
                .poster(photos.map(EventPhotos::getPoster).orElse(null))
                .build();
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

    @Override
    @Transactional(readOnly = true)
    public List<EventShortResponseDTO> getEventsByClubIds(List<Integer> clubIds) {
        List<Events> events = eventsRepository.findByClubIdIn(clubIds);
        
        List<Long> longClubIds = clubIds.stream().map(Integer::longValue).collect(Collectors.toList());
        Map<Integer, String> clubNamesMap = clubRepository.findAllById(longClubIds).stream()
                .collect(Collectors.toMap(c -> c.getId().intValue(), Club::getName));

        return events.stream().map(e -> EventShortResponseDTO.builder()
                .eventId(e.getId())
                .eventName(e.getTitle())
                .clubId(e.getClubId())
                .clubName(clubNamesMap.getOrDefault(e.getClubId(), "Unknown Club"))
                .build()).collect(Collectors.toList());
    }
}