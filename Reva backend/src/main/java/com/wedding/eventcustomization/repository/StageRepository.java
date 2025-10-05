package com.wedding.eventcustomization.repository;

import com.wedding.eventcustomization.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
    
    List<Stage> findByEventIdAndIsActiveTrueOrderByNameAsc(Long eventId);
    
    Optional<Stage> findByIdAndIsActiveTrue(Long id);
    
    @Query("SELECT s FROM Stage s WHERE s.event.id = :eventId AND s.isActive = true AND LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Stage> findByEventIdAndNameContainingIgnoreCaseAndIsActiveTrue(@Param("eventId") Long eventId, @Param("name") String name);
    
    boolean existsByNameIgnoreCaseAndEventIdAndIdNot(String name, Long eventId, Long id);
    
    boolean existsByNameIgnoreCaseAndEventId(String name, Long eventId);
}
