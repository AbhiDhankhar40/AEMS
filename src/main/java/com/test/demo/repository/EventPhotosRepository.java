package com.test.demo.repository;

import com.test.demo.model.EventPhotos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventPhotosRepository extends JpaRepository<EventPhotos, Long> {
    
    Optional<EventPhotos> findByEventId(Long eventId);
}