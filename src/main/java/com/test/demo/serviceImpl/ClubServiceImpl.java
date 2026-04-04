package com.test.demo.serviceImpl;

import com.test.demo.model.Club;
import com.test.demo.repository.ClubRepository;
import com.test.demo.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;

    @Override
    public Club createClub(Club club) {
        return clubRepository.save(club);
    }

    @Override
    public List<Club> createClubs(List<Club> clubs) {
        return clubRepository.saveAll(clubs);
    }

    @Override
    public Club updateClub(Long id, Club club) {
        Club existing = getClubById(id);
        
        existing.setName(club.getName());
        existing.setDepartment(club.getDepartment());
        existing.setBlock(club.getBlock());
        existing.setAdvisorName(club.getAdvisorName());
        existing.setAdvisorDesignation(club.getAdvisorDesignation());
        existing.setAdvisorMobile(club.getAdvisorMobile());
        existing.setAdvisorEmail(club.getAdvisorEmail());
        existing.setCoordinatorName(club.getCoordinatorName());
        existing.setCoordinatorDesignation(club.getCoordinatorDesignation());
        existing.setCoordinatorEmail(club.getCoordinatorEmail());
        existing.setCoordinatorMobile(club.getCoordinatorMobile());
        existing.setCoordinator2Name(club.getCoordinator2Name());
        existing.setCoordinator2Designation(club.getCoordinator2Designation());
        existing.setCoordinator2Email(club.getCoordinator2Email());
        existing.setCoordinator2Mobile(club.getCoordinator2Mobile());
        existing.setPresident(club.getPresident());
        existing.setPresidentMobile(club.getPresidentMobile());
        existing.setVicePresident(club.getVicePresident());
        existing.setSecretary(club.getSecretary());
        existing.setJointSecretary(club.getJointSecretary());
        existing.setDocCurator(club.getDocCurator());
        existing.setCreativeHead(club.getCreativeHead());
        existing.setTreasurer(club.getTreasurer());
        existing.setMemberName(club.getMemberName());

        return clubRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Club getClubById(Long id) {
        return clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Club> getActiveClubsByDepartment(Integer departmentId) {
        return clubRepository.findByDepartmentAndStatus(departmentId, "Active");
    }

    @Override
    public void deleteClub(Long id) {
        clubRepository.deleteById(id);
    }
}