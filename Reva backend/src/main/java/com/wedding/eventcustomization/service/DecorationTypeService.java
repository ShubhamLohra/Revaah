package com.wedding.eventcustomization.service;

import com.wedding.eventcustomization.dto.DecorationTypeDto;
import com.wedding.eventcustomization.entity.DecorationType;
import com.wedding.eventcustomization.entity.Event;
import com.wedding.eventcustomization.exception.ResourceNotFoundException;
import com.wedding.eventcustomization.mapper.DecorationTypeMapper;
import com.wedding.eventcustomization.repository.DecorationTypeRepository;
import com.wedding.eventcustomization.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DecorationTypeService {
    
    private final DecorationTypeRepository decorationTypeRepository;
    private final EventRepository eventRepository;
    
    public List<DecorationTypeDto> getAllDecorationTypes() {
        log.info("Fetching all decoration types");
        List<DecorationType> decorationTypes = decorationTypeRepository.findByIsActiveTrue();
        return DecorationTypeMapper.toDtoList(decorationTypes);
    }
    
    public List<DecorationTypeDto> getDecorationTypesByEventId(Long eventId) {
        log.info("Fetching decoration types for event ID: {}", eventId);
        List<DecorationType> decorationTypes = decorationTypeRepository.findByEventIdAndIsActiveTrue(eventId);
        return DecorationTypeMapper.toDtoList(decorationTypes);
    }
    
    public DecorationTypeDto getDecorationTypeById(Long id) {
        log.info("Fetching decoration type with ID: {}", id);
        DecorationType decorationType = decorationTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DecorationType not found with id: " + id));
        return DecorationTypeMapper.toDto(decorationType);
    }
    
    public DecorationTypeDto createDecorationType(DecorationTypeDto decorationTypeDto) {
        log.info("Creating decoration type: {}", decorationTypeDto.getName());
        
        Event event = eventRepository.findById(decorationTypeDto.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + decorationTypeDto.getEventId()));
        
        DecorationType decorationType = DecorationType.builder()
                .name(decorationTypeDto.getName())
                .description(decorationTypeDto.getDescription())
                .isActive(decorationTypeDto.getIsActive() != null ? decorationTypeDto.getIsActive() : true)
                .displayOrder(decorationTypeDto.getDisplayOrder())
                .event(event)
                .build();
        
        DecorationType savedDecorationType = decorationTypeRepository.save(decorationType);
        log.info("Created decoration type with ID: {}", savedDecorationType.getId());
        
        return DecorationTypeMapper.toDto(savedDecorationType);
    }
    
    public DecorationTypeDto updateDecorationType(Long id, DecorationTypeDto decorationTypeDto) {
        log.info("Updating decoration type with ID: {}", id);
        
        DecorationType existingDecorationType = decorationTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DecorationType not found with id: " + id));
        
        if (decorationTypeDto.getEventId() != null) {
            Event event = eventRepository.findById(decorationTypeDto.getEventId())
                    .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + decorationTypeDto.getEventId()));
            existingDecorationType.setEvent(event);
        }
        
        if (decorationTypeDto.getName() != null) {
            existingDecorationType.setName(decorationTypeDto.getName());
        }
        if (decorationTypeDto.getDescription() != null) {
            existingDecorationType.setDescription(decorationTypeDto.getDescription());
        }
        if (decorationTypeDto.getIsActive() != null) {
            existingDecorationType.setIsActive(decorationTypeDto.getIsActive());
        }
        if (decorationTypeDto.getDisplayOrder() != null) {
            existingDecorationType.setDisplayOrder(decorationTypeDto.getDisplayOrder());
        }
        
        DecorationType updatedDecorationType = decorationTypeRepository.save(existingDecorationType);
        log.info("Updated decoration type with ID: {}", updatedDecorationType.getId());
        
        return DecorationTypeMapper.toDto(updatedDecorationType);
    }
    
    public void deleteDecorationType(Long id) {
        log.info("Deleting decoration type with ID: {}", id);
        
        DecorationType decorationType = decorationTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DecorationType not found with id: " + id));
        
        decorationType.setIsActive(false);
        decorationTypeRepository.save(decorationType);
        
        log.info("Soft deleted decoration type with ID: {}", id);
    }
}
