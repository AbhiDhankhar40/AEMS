package com.test.demo.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users",
       uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String userType;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer department;

    @Column(nullable = false)
    private String status;

    @ElementCollection
    @CollectionTable(name = "user_club_access", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "club_id")
    private List<Integer> clubIds;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
