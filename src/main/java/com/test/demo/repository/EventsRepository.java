package com.test.demo.repository;

import com.test.demo.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<Events, Long> {
    List<Events> findAllByIdIn(List<Long> ids);

    List<Events> findTop10ByOrderByIdDesc();

    List<Events> findByDepartmentOrderByIdDesc(Integer department);

    List<Events> findTop10ByClubIdInOrderByIdDesc(List<Integer> clubIds);

    List<Events> findByClubIdIn(List<Integer> clubIds);

    long countByStatus(String status);

    long countByDepartment(Integer department);

    long countByDepartmentAndStatus(Integer department, String status);

    long countByClubIdIn(List<Integer> clubIds);

    long countByClubIdInAndStatus(List<Integer> clubIds, String status);
}