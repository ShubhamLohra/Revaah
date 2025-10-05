package com.wedding.eventcustomization.repository;

import com.wedding.eventcustomization.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    List<Event> findByIsActiveTrueOrderByNameAsc();
    
    Optional<Event> findByIdAndIsActiveTrue(Long id);
    
    Optional<Event> findByNameIgnoreCase(String name);
    
    @Query("SELECT e FROM Event e WHERE e.isActive = true AND LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Event> findByNameContainingIgnoreCaseAndIsActiveTrue(@Param("name") String name);
    
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}
