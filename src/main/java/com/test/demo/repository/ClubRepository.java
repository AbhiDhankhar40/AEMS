package com.test.demo.repository;

import com.test.demo.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findByDepartmentAndStatus(Integer department, String status);

    List<Club> findByDepartment(Integer department);

    List<Club> findByStatus(String status);

    List<Club> findByIdInAndStatus(List<Long> ids, String status);
}