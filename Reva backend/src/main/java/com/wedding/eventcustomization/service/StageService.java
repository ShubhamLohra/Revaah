package com.wedding.eventcustomization.service;

import com.wedding.eventcustomization.dto.StageDto;
import com.wedding.eventcustomization.entity.Event;
import com.wedding.eventcustomization.entity.Stage;
import com.wedding.eventcustomization.exception.ResourceNotFoundException;
import com.wedding.eventcustomization.exception.ResourceAlreadyExistsException;
import com.wedding.eventcustomization.mapper.StageMapper;
import com.wedding.eventcustomization.repository.EventRepository;
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
public class StageService {
    
    private final StageRepository stageRepository;
    private final EventRepository eventRepository;
    private final StageMapper stageMapper;
    
    public List<StageDto> getAllStagesByEventId(Long eventId) {
        log.info("Fetching all active stages for event id: {}", eventId);
        
        // Verify event exists and is active
        Event event = eventRepository.findByIdAndIsActiveTrue(eventId)
                .orElseThrow(() -> {
                    log.error("Event not found with id: {}", eventId);
                    return new ResourceNotFoundException("Event not found with id: " + eventId);
                });
        
        List<Stage> stages = stageRepository.findByEventIdAndIsActiveTrueOrderByNameAsc(eventId);
        log.info("Found {} active stages for event: {}", stages.size(), event.getName());
        return stages.stream()
                .map(stageMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public StageDto getStageById(Long id) {
        log.info("Fetching stage with id: {}", id);
        Stage stage = stageRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> {
                    log.error("Stage not found with id: {}", id);
                    return new ResourceNotFoundException("Stage not found with id: " + id);
                });
        log.info("Successfully fetched stage: {}", stage.getName());
        return stageMapper.toDto(stage);
    }
    
    public StageDto createStage(StageDto stageDto) {
        log.info("Creating new stage: {} for event id: {}", stageDto.getName(), stageDto.getEventId());
        
        // Verify event exists and is active
        Event event = eventRepository.findByIdAndIsActiveTrue(stageDto.getEventId())
                .orElseThrow(() -> {
                    log.error("Event not found with id: {}", stageDto.getEventId());
                    return new ResourceNotFoundException("Event not found with id: " + stageDto.getEventId());
                });
        
        if (stageRepository.existsByNameIgnoreCaseAndEventIdAndIdNot(stageDto.getName(), stageDto.getEventId(), 0L)) {
            log.error("Stage with name '{}' already exists for event id: {}", stageDto.getName(), stageDto.getEventId());
            throw new ResourceAlreadyExistsException("Stage with name '" + stageDto.getName() + "' already exists for this event");
        }
        
        Stage stage = stageMapper.toEntity(stageDto);
        stage.setEvent(event);
        stage.setIsActive(true);
        Stage savedStage = stageRepository.save(stage);
        log.info("Successfully created stage with id: {}", savedStage.getId());
        return stageMapper.toDto(savedStage);
    }
    
    public StageDto updateStage(Long id, StageDto stageDto) {
        log.info("Updating stage with id: {}", id);
        
        Stage existingStage = stageRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> {
                    log.error("Stage not found with id: {}", id);
                    return new ResourceNotFoundException("Stage not found with id: " + id);
                });
        
        // Verify event exists and is active
        Event event = eventRepository.findByIdAndIsActiveTrue(stageDto.getEventId())
                .orElseThrow(() -> {
                    log.error("Event not found with id: {}", stageDto.getEventId());
                    return new ResourceNotFoundException("Event not found with id: " + stageDto.getEventId());
                });
        
        if (stageRepository.existsByNameIgnoreCaseAndEventIdAndIdNot(stageDto.getName(), stageDto.getEventId(), id)) {
            log.error("Stage with name '{}' already exists for event id: {}", stageDto.getName(), stageDto.getEventId());
            throw new ResourceAlreadyExistsException("Stage with name '" + stageDto.getName() + "' already exists for this event");
        }
        
        existingStage.setName(stageDto.getName());
        existingStage.setDescription(stageDto.getDescription());
        existingStage.setEvent(event);
        if (stageDto.getIsActive() != null) {
            existingStage.setIsActive(stageDto.getIsActive());
        }
        
        Stage updatedStage = stageRepository.save(existingStage);
        log.info("Successfully updated stage with id: {}", updatedStage.getId());
        return stageMapper.toDto(updatedStage);
    }
    
    public void deleteStage(Long id) {
        log.info("Soft deleting stage with id: {}", id);
        
        Stage stage = stageRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> {
                    log.error("Stage not found with id: {}", id);
                    return new ResourceNotFoundException("Stage not found with id: " + id);
                });
        
        stage.setIsActive(false);
        stageRepository.save(stage);
        log.info("Successfully soft deleted stage with id: {}", id);
    }
    
    public List<StageDto> searchStagesByEventId(Long eventId, String name) {
        log.info("Searching stages for event id: {} with name containing: {}", eventId, name);
        
        // Verify event exists and is active
        Event event = eventRepository.findByIdAndIsActiveTrue(eventId)
                .orElseThrow(() -> {
                    log.error("Event not found with id: {}", eventId);
                    return new ResourceNotFoundException("Event not found with id: " + eventId);
                });
        
        List<Stage> stages = stageRepository.findByEventIdAndNameContainingIgnoreCaseAndIsActiveTrue(eventId, name);
        log.info("Found {} stages matching search criteria for event: {}", stages.size(), event.getName());
        return stages.stream()
                .map(stageMapper::toDto)
                .collect(Collectors.toList());
    }
}
