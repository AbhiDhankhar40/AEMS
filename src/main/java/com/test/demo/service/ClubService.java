package com.test.demo.service;

import com.test.demo.dto.ClubShortResponseDTO;
import com.test.demo.model.Club;
import java.util.List;

public interface ClubService {
    
    Club createClub(Club club);
    
    List<Club> createClubs(List<Club> clubs);
    
    Club updateClub(Long id, Club club);
    
    Club getClubById(Long id);
    
    List<Club> getAllClubs();
    
    List<Club> getActiveClubsByDepartment(Integer departmentId);

    List<ClubShortResponseDTO> getClubsByUserId(Long userId);
    
    void deleteClub(Long id);
}