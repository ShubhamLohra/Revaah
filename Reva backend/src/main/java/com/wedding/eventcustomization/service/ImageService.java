package com.wedding.eventcustomization.service;

import com.wedding.eventcustomization.dto.ImageDto;
import com.wedding.eventcustomization.entity.Image;
import com.wedding.eventcustomization.entity.Stage;
import com.wedding.eventcustomization.exception.ResourceNotFoundException;
import com.wedding.eventcustomization.exception.ResourceAlreadyExistsException;
import com.wedding.eventcustomization.mapper.ImageMapper;
import com.wedding.eventcustomization.repository.ImageRepository;
import com.wedding.eventcustomization.repository.StageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ImageService {
    
    private final ImageRepository imageRepository;
    private final StageRepository stageRepository;
    private final ImageMapper imageMapper;
    
    public List<ImageDto> getAllImagesByStageId(Long stageId) {
        log.info("Fetching all active images for stage id: {}", stageId);
        
        // Verify stage exists and is active
        Stage stage = stageRepository.findByIdAndIsActiveTrue(stageId)
                .orElseThrow(() -> {
                    log.error("Stage not found with id: {}", stageId);
                    return new ResourceNotFoundException("Stage not found with id: " + stageId);
                });
        
        List<Image> images = imageRepository.findByStageIdAndIsActiveTrueOrderByDisplayOrderAsc(stageId);
        log.info("Found {} active images for stage: {}", images.size(), stage.getName());
        return images.stream()
                .map(imageMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public ImageDto getImageById(Long id) {
        log.info("Fetching image with id: {}", id);
        Image image = imageRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> {
                    log.error("Image not found with id: {}", id);
                    return new ResourceNotFoundException("Image not found with id: " + id);
                });
        log.info("Successfully fetched image: {}", image.getName());
        return imageMapper.toDto(image);
    }
    
    public ImageDto createImage(ImageDto imageDto) {
        log.info("Creating new image: {} for stage id: {}", imageDto.getName(), imageDto.getStageId());
        
        // Verify stage exists and is active
        Stage stage = stageRepository.findByIdAndIsActiveTrue(imageDto.getStageId())
                .orElseThrow(() -> {
                    log.error("Stage not found with id: {}", imageDto.getStageId());
                    return new ResourceNotFoundException("Stage not found with id: " + imageDto.getStageId());
                });
        
        if (imageRepository.existsByNameIgnoreCaseAndStageIdAndIdNot(imageDto.getName(), imageDto.getStageId(), 0L)) {
            log.error("Image with name '{}' already exists for stage id: {}", imageDto.getName(), imageDto.getStageId());
            throw new ResourceAlreadyExistsException("Image with name '" + imageDto.getName() + "' already exists for this stage");
        }
        
        Image image = imageMapper.toEntity(imageDto);
        image.setStage(stage);
        image.setIsActive(true);
        if (imageDto.getDisplayOrder() == null) {
            image.setDisplayOrder(0);
        }
        Image savedImage = imageRepository.save(image);
        log.info("Successfully created image with id: {}", savedImage.getId());
        return imageMapper.toDto(savedImage);
    }
    
    public ImageDto updateImage(Long id, ImageDto imageDto) {
        log.info("Updating image with id: {}", id);
        
        Image existingImage = imageRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> {
                    log.error("Image not found with id: {}", id);
                    return new ResourceNotFoundException("Image not found with id: " + id);
                });
        
        // Verify stage exists and is active
        Stage stage = stageRepository.findByIdAndIsActiveTrue(imageDto.getStageId())
                .orElseThrow(() -> {
                    log.error("Stage not found with id: {}", imageDto.getStageId());
                    return new ResourceNotFoundException("Stage not found with id: " + imageDto.getStageId());
                });
        
        if (imageRepository.existsByNameIgnoreCaseAndStageIdAndIdNot(imageDto.getName(), imageDto.getStageId(), id)) {
            log.error("Image with name '{}' already exists for stage id: {}", imageDto.getName(), imageDto.getStageId());
            throw new ResourceAlreadyExistsException("Image with name '" + imageDto.getName() + "' already exists for this stage");
        }
        
        existingImage.setName(imageDto.getName());
        existingImage.setImageUrl(imageDto.getImageUrl());
        existingImage.setDescription(imageDto.getDescription());
        existingImage.setStage(stage);
        if (imageDto.getDisplayOrder() != null) {
            existingImage.setDisplayOrder(imageDto.getDisplayOrder());
        }
        if (imageDto.getIsActive() != null) {
            existingImage.setIsActive(imageDto.getIsActive());
        }
        
        Image updatedImage = imageRepository.save(existingImage);
        log.info("Successfully updated image with id: {}", updatedImage.getId());
        return imageMapper.toDto(updatedImage);
    }
    
    public void deleteImage(Long id) {
        log.info("Soft deleting image with id: {}", id);
        
        Image image = imageRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> {
                    log.error("Image not found with id: {}", id);
                    return new ResourceNotFoundException("Image not found with id: " + id);
                });
        
        image.setIsActive(false);
        imageRepository.save(image);
        log.info("Successfully soft deleted image with id: {}", id);
    }
    
    public List<ImageDto> searchImagesByStageId(Long stageId, String name) {
        log.info("Searching images for stage id: {} with name containing: {}", stageId, name);
        
        // Verify stage exists and is active
        Stage stage = stageRepository.findByIdAndIsActiveTrue(stageId)
                .orElseThrow(() -> {
                    log.error("Stage not found with id: {}", stageId);
                    return new ResourceNotFoundException("Stage not found with id: " + stageId);
                });
        
        List<Image> images = imageRepository.findByStageIdAndNameContainingIgnoreCaseAndIsActiveTrue(stageId, name);
        log.info("Found {} images matching search criteria for stage: {}", images.size(), stage.getName());
        return images.stream()
                .map(imageMapper::toDto)
                .collect(Collectors.toList());
    }
}
