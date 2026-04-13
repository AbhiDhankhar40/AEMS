package com.test.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.demo.dto.ClubResponseDTO;
import com.test.demo.dto.ClubShortResponseDTO;
import com.test.demo.model.Club;
import com.test.demo.service.ClubService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @PostMapping("/add")
    public ResponseEntity<Club> createClub(@RequestBody Club club) {
        return ResponseEntity.ok(clubService.createClub(club));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Club>> createClubs(@RequestBody List<Club> clubs) {
        return ResponseEntity.ok(clubService.createClubs(clubs));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ClubResponseDTO>> getAllClubs() {
        return ResponseEntity.ok(clubService.getAllClubs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Club> getClubById(@PathVariable Long id) {
        return ResponseEntity.ok(clubService.getClubById(id));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<ClubResponseDTO>> getActiveClubsByDepartment(@PathVariable Integer departmentId) {
        return ResponseEntity.ok(clubService.getActiveClubsByDepartment(departmentId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ClubShortResponseDTO>> getClubsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(clubService.getClubsByUserId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Club> updateClub(@PathVariable Long id, @RequestBody Club club) {
        return ResponseEntity.ok(clubService.updateClub(id, club));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long id) {
        clubService.deleteClub(id);
        return ResponseEntity.noContent().build();
    }


    
}