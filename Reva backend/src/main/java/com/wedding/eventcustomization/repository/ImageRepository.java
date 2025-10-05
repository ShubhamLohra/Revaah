package com.wedding.eventcustomization.repository;

import com.wedding.eventcustomization.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    
    List<Image> findByStageIdAndIsActiveTrueOrderByDisplayOrderAsc(Long stageId);
    
    Optional<Image> findByIdAndIsActiveTrue(Long id);
    
    @Query("SELECT i FROM Image i WHERE i.stage.id = :stageId AND i.isActive = true AND LOWER(i.name) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY i.displayOrder ASC")
    List<Image> findByStageIdAndNameContainingIgnoreCaseAndIsActiveTrue(@Param("stageId") Long stageId, @Param("name") String name);
    
    boolean existsByNameIgnoreCaseAndStageIdAndIdNot(String name, Long stageId, Long id);
    
    boolean existsByNameIgnoreCaseAndStageId(String name, Long stageId);
}
