package com.wedding.eventcustomization.repository;

import com.wedding.eventcustomization.entity.DecorationImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecorationImageRepository extends JpaRepository<DecorationImage, Long> {
    
    List<DecorationImage> findByDecorationTypeId(Long decorationTypeId);
    
    List<DecorationImage> findByDecorationTypeIdAndNameContainingIgnoreCase(Long decorationTypeId, String name);
    
    List<DecorationImage> findByIsActiveTrue();
    
    List<DecorationImage> findByDecorationTypeIdAndIsActiveTrue(Long decorationTypeId);
}
