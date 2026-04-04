package com.test.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String organiser;

    private Integer department;

     private Integer clubId;

    private LocalDate date;

    private LocalTime time;

    private String venue;

    @Column(columnDefinition = "TEXT")
    private String objective;

    private String speakers;

    private String participants;
   
    @Column(columnDefinition = "TEXT")
    private String programmeSchedule;

     @Column(columnDefinition = "TEXT")
    private String highLights;

    @Column(columnDefinition = "TEXT")
    private String purpose;

    @Column(columnDefinition = "TEXT")
    private String outcome;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    private Double budget;

    @Column(columnDefinition = "TEXT")
    private String conclusion;

    private String acknowledgement;

    private String status;
}
