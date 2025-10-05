package com.wedding.eventcustomization.repository;

import com.wedding.eventcustomization.entity.DecorationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecorationTypeRepository extends JpaRepository<DecorationType, Long> {
    
    List<DecorationType> findByEventId(Long eventId);
    
    List<DecorationType> findByEventIdAndNameContainingIgnoreCase(Long eventId, String name);
    
    List<DecorationType> findByIsActiveTrue();
    
    List<DecorationType> findByEventIdAndIsActiveTrue(Long eventId);
}
