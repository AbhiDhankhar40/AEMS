package com.test.demo.serviceImpl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.demo.dto.ClubResponseDTO;
import com.test.demo.dto.ClubShortResponseDTO;
import com.test.demo.model.Club;
import com.test.demo.model.Department;
import com.test.demo.model.UserMaster;
import com.test.demo.repository.ClubRepository;
import com.test.demo.repository.DepartmentRepository;
import com.test.demo.repository.UserMasterRepository;
import com.test.demo.service.ClubService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;
    private final UserMasterRepository userMasterRepository;
    private final DepartmentRepository departmentRepository;

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
    public List<ClubResponseDTO> getAllClubs() {
        List<Club> clubs = clubRepository.findAll();
        return mapToClubResponseDTOs(clubs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubResponseDTO> getActiveClubsByDepartment(Integer departmentId) {
        List<Club> clubs = clubRepository.findByDepartmentAndStatus(departmentId, "Active");
        return mapToClubResponseDTOs(clubs);
    }

    private List<ClubResponseDTO> mapToClubResponseDTOs(List<Club> clubs) {
        if (clubs.isEmpty()) return List.of();

        // Fetch all relevant department IDs to avoid N+1 queries
        List<Long> departmentIds = clubs.stream()
                .map(Club::getDepartment)
                .filter(Objects::nonNull)
                .map(Integer::longValue)
                .distinct()
                .collect(Collectors.toList());

        // Map department IDs to names in one batch query
        Map<Integer, String> departmentNamesMap = departmentRepository.findAllById(departmentIds).stream()
                .collect(Collectors.toMap(
                        d -> d.getId().intValue(),
                        Department::getDepartmentName,
                        (v1, v2) -> v1
                ));

        return clubs.stream()
                .map(club -> convertToDTO(club, departmentNamesMap))
                .collect(Collectors.toList());
    }

    private ClubResponseDTO convertToDTO(Club club, Map<Integer, String> departmentMap) {
        String deptName;
		if (club.getDepartment() != null)
			deptName = departmentMap.getOrDefault(club.getDepartment(), "");
		else
			deptName = "";

        return ClubResponseDTO.builder()
                .id(club.getId())
                .name(club.getName())
                .department(club.getDepartment())
                .departmentName(deptName)
                .block(club.getBlock())
                .advisorName(club.getAdvisorName())
                .advisorDesignation(club.getAdvisorDesignation())
                .advisorMobile(club.getAdvisorMobile())
                .advisorEmail(club.getAdvisorEmail())
                .coordinatorName(club.getCoordinatorName())
                .coordinatorDesignation(club.getCoordinatorDesignation())
                .coordinatorEmail(club.getCoordinatorEmail())
                .coordinatorMobile(club.getCoordinatorMobile())
                .coordinator2Name(club.getCoordinator2Name())
                .coordinator2Designation(club.getCoordinator2Designation())
                .coordinator2Email(club.getCoordinator2Email())
                .coordinator2Mobile(club.getCoordinator2Mobile())
                .president(club.getPresident())
                .presidentMobile(club.getPresidentMobile())
                .vicePresident(club.getVicePresident())
                .secretary(club.getSecretary())
                .jointSecretary(club.getJointSecretary())
                .docCurator(club.getDocCurator())
                .creativeHead(club.getCreativeHead())
                .treasurer(club.getTreasurer())
                .memberName(club.getMemberName())
                .status(club.getStatus())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubShortResponseDTO> getClubsByUserId(Long userId) {
        UserMaster user = userMasterRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Club> clubs;
        String userType = user.getUserType();

        if ("Super Admin".equalsIgnoreCase(userType)) {
            clubs = clubRepository.findByStatus("Active");
        } else if ("Admin".equalsIgnoreCase(userType)) {
            clubs = clubRepository.findByDepartmentAndStatus(user.getDepartment(), "Active");
        } else {
            List<Long> clubIds = user.getClubIds().stream().map(Integer::longValue).collect(Collectors.toList());
            clubs = clubRepository.findByIdInAndStatus(clubIds, "Active");
        }

        return clubs.stream()
                .map(club -> ClubShortResponseDTO.builder()
                        .clubId(club.getId())
                        .clubName(club.getName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteClub(Long id) {
        clubRepository.deleteById(id);
    }
}