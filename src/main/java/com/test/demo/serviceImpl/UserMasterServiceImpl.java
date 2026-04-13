package com.test.demo.serviceImpl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.demo.dto.UserResponseDTO;
import com.test.demo.model.Club;
import com.test.demo.model.Department;
import com.test.demo.model.UserMaster;

import com.test.demo.repository.ClubRepository;
import com.test.demo.repository.DepartmentRepository;
import com.test.demo.repository.UserMasterRepository;
import com.test.demo.service.UserMasterService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserMasterServiceImpl implements UserMasterService {

    private final UserMasterRepository repository;
    private final DepartmentRepository departmentRepository;
    private final ClubRepository clubRepository;


    @Override
    public UserMaster createUser(UserMaster user) {

        if (repository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        return repository.save(user);
    }

    @Override
    public UserMaster updateUser(Long id, UserMaster user) {

        UserMaster existing = getUserById(id);
        existing.setName(user.getName());

        return repository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public UserMaster getUserById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        List<UserMaster> users = repository.findAll();
        if (users.isEmpty()) return List.of();

        List<Long> departmentIds = users.stream()
                .map(UserMaster::getDepartment)
                .filter(Objects::nonNull)
                .map(Integer::longValue)
                .distinct()
                .collect(Collectors.toList());

        Map<Integer, String> departmentNamesMap = departmentRepository.findAllById(departmentIds).stream()
                .collect(Collectors.toMap(
                        d -> d.getId().intValue(),
                        Department::getDepartmentName,
                        (v1, v2) -> v1
                ));

        // Fetch all relevant club names in one batch query to avoid N+1 issues
        List<Long> allClubIds = users.stream()
                .filter(u -> u.getClubIds() != null)
                .flatMap(u -> u.getClubIds().stream())
                .filter(Objects::nonNull)
                .map(Integer::longValue)
                .distinct()
                .collect(Collectors.toList());

        Map<Integer, String> clubNamesMap = allClubIds.isEmpty() ? Map.of() :
                clubRepository.findAllById(allClubIds).stream()
                        .collect(Collectors.toMap(
                                c -> c.getId().intValue(),
                                Club::getName,
                                (v1, v2) -> v1
                        ));

        return users.stream()
                .map(user -> {
                    List<String> clubNames = user.getClubIds() != null ?
                            user.getClubIds().stream()
                                    .map(id -> clubNamesMap.getOrDefault(id, "Unknown Club"))
                                    .collect(Collectors.toList()) :
                            List.of();

                    return UserResponseDTO.builder()
                            .id(user.getId())
                            .username(user.getUsername())
                            .userType(user.getUserType())
                            .name(user.getName())
                            .department(user.getDepartment())
                            .departmentName(departmentNamesMap.getOrDefault(user.getDepartment(), ""))
                            .status(user.getStatus())
                            .clubIds(user.getClubIds())
                            .clubNames(clubNames)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {

        UserMaster existing = getUserById(id);
        repository.delete(existing);
    }


}
