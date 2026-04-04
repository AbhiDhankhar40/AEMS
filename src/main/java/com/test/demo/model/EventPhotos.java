package com.test.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "event_photos")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventPhotos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventId;
    @Column(columnDefinition = "TEXT") 
    private String poster;
    @Column(columnDefinition = "TEXT") 
    private String geoTag;
    @Column(columnDefinition = "TEXT") 
    private String banner;
    @Column(columnDefinition = "TEXT") 
    private String pic1;
    @Column(columnDefinition = "TEXT") 
    private String pic2;
    @Column(columnDefinition = "TEXT") 
    private String pic3;
}
