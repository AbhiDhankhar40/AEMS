package com.test.demo.service;

import java.util.List;

import com.test.demo.dto.ClubResponseDTO;
import com.test.demo.dto.ClubShortResponseDTO;
import com.test.demo.model.Club;

public interface ClubService {
    
    Club createClub(Club club);
    
    List<Club> createClubs(List<Club> clubs);
    
    Club updateClub(Long id, Club club);
    
    Club getClubById(Long id);
    
    List<ClubResponseDTO> getAllClubs();
    
    List<ClubResponseDTO> getActiveClubsByDepartment(Integer departmentId);

    List<ClubShortResponseDTO> getClubsByUserId(Long userId);
    
    void deleteClub(Long id);
}