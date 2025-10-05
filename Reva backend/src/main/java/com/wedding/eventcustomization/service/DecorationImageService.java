package com.wedding.eventcustomization.service;

import com.wedding.eventcustomization.dto.DecorationImageDto;
import com.wedding.eventcustomization.entity.DecorationImage;
import com.wedding.eventcustomization.entity.DecorationType;
import com.wedding.eventcustomization.exception.ResourceNotFoundException;
import com.wedding.eventcustomization.mapper.DecorationImageMapper;
import com.wedding.eventcustomization.repository.DecorationImageRepository;
import com.wedding.eventcustomization.repository.DecorationTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DecorationImageService {
    
    private final DecorationImageRepository decorationImageRepository;
    private final DecorationTypeRepository decorationTypeRepository;
    
    public List<DecorationImageDto> getAllDecorationImages() {
        log.info("Fetching all decoration images");
        List<DecorationImage> decorationImages = decorationImageRepository.findByIsActiveTrue();
        return DecorationImageMapper.toDtoList(decorationImages);
    }
    
    public List<DecorationImageDto> getDecorationImagesByDecorationTypeId(Long decorationTypeId) {
        log.info("Fetching decoration images for decoration type ID: {}", decorationTypeId);
        List<DecorationImage> decorationImages = decorationImageRepository.findByDecorationTypeIdAndIsActiveTrue(decorationTypeId);
        return DecorationImageMapper.toDtoList(decorationImages);
    }
    
    public DecorationImageDto getDecorationImageById(Long id) {
        log.info("Fetching decoration image with ID: {}", id);
        DecorationImage decorationImage = decorationImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DecorationImage not found with id: " + id));
        return DecorationImageMapper.toDto(decorationImage);
    }
    
    public DecorationImageDto createDecorationImage(DecorationImageDto decorationImageDto) {
        log.info("Creating decoration image: {}", decorationImageDto.getName());
        
        DecorationType decorationType = decorationTypeRepository.findById(decorationImageDto.getDecorationTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("DecorationType not found with id: " + decorationImageDto.getDecorationTypeId()));
        
        DecorationImage decorationImage = DecorationImage.builder()
                .name(decorationImageDto.getName())
                .imageUrl(decorationImageDto.getImageUrl())
                .description(decorationImageDto.getDescription())
                .isActive(decorationImageDto.getIsActive() != null ? decorationImageDto.getIsActive() : true)
                .displayOrder(decorationImageDto.getDisplayOrder())
                .decorationType(decorationType)
                .build();
        
        DecorationImage savedDecorationImage = decorationImageRepository.save(decorationImage);
        log.info("Created decoration image with ID: {}", savedDecorationImage.getId());
        
        return DecorationImageMapper.toDto(savedDecorationImage);
    }
    
    public DecorationImageDto updateDecorationImage(Long id, DecorationImageDto decorationImageDto) {
        log.info("Updating decoration image with ID: {}", id);
        
        DecorationImage existingDecorationImage = decorationImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DecorationImage not found with id: " + id));
        
        if (decorationImageDto.getDecorationTypeId() != null) {
            DecorationType decorationType = decorationTypeRepository.findById(decorationImageDto.getDecorationTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("DecorationType not found with id: " + decorationImageDto.getDecorationTypeId()));
            existingDecorationImage.setDecorationType(decorationType);
        }
        
        if (decorationImageDto.getName() != null) {
            existingDecorationImage.setName(decorationImageDto.getName());
        }
        if (decorationImageDto.getImageUrl() != null) {
            existingDecorationImage.setImageUrl(decorationImageDto.getImageUrl());
        }
        if (decorationImageDto.getDescription() != null) {
            existingDecorationImage.setDescription(decorationImageDto.getDescription());
        }
        if (decorationImageDto.getIsActive() != null) {
            existingDecorationImage.setIsActive(decorationImageDto.getIsActive());
        }
        if (decorationImageDto.getDisplayOrder() != null) {
            existingDecorationImage.setDisplayOrder(decorationImageDto.getDisplayOrder());
        }
        
        DecorationImage updatedDecorationImage = decorationImageRepository.save(existingDecorationImage);
        log.info("Updated decoration image with ID: {}", updatedDecorationImage.getId());
        
        return DecorationImageMapper.toDto(updatedDecorationImage);
    }
    
    public void deleteDecorationImage(Long id) {
        log.info("Deleting decoration image with ID: {}", id);
        
        DecorationImage decorationImage = decorationImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DecorationImage not found with id: " + id));
        
        decorationImage.setIsActive(false);
        decorationImageRepository.save(decorationImage);
        
        log.info("Soft deleted decoration image with ID: {}", id);
    }
}
