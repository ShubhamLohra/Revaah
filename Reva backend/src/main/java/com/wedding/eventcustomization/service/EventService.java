package com.wedding.eventcustomization.service;

import com.wedding.eventcustomization.dto.EventDto;
import com.wedding.eventcustomization.entity.Event;
import com.wedding.eventcustomization.exception.ResourceNotFoundException;
import com.wedding.eventcustomization.exception.ResourceAlreadyExistsException;
import com.wedding.eventcustomization.mapper.EventMapper;
import com.wedding.eventcustomization.repository.EventRepository;
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
public class EventService {
    
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    
    public List<EventDto> getAllEvents() {
        log.info("Fetching all active events");
        List<Event> events = eventRepository.findByIsActiveTrueOrderByNameAsc();
        log.info("Found {} active events", events.size());
        return events.stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public EventDto getEventById(Long id) {
        log.info("Fetching event with id: {}", id);
        Event event = eventRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> {
                    log.error("Event not found with id: {}", id);
                    return new ResourceNotFoundException("Event not found with id: " + id);
                });
        log.info("Successfully fetched event: {}", event.getName());
        return eventMapper.toDto(event);
    }
    
    public EventDto createEvent(EventDto eventDto) {
        log.info("Creating new event: {}", eventDto.getName());
        
        if (eventRepository.existsByNameIgnoreCaseAndIdNot(eventDto.getName(), 0L)) {
            log.error("Event with name '{}' already exists", eventDto.getName());
            throw new ResourceAlreadyExistsException("Event with name '" + eventDto.getName() + "' already exists");
        }
        
        Event event = eventMapper.toEntity(eventDto);
        event.setIsActive(true);
        Event savedEvent = eventRepository.save(event);
        log.info("Successfully created event with id: {}", savedEvent.getId());
        return eventMapper.toDto(savedEvent);
    }
    
    public EventDto updateEvent(Long id, EventDto eventDto) {
        log.info("Updating event with id: {}", id);
        
        Event existingEvent = eventRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> {
                    log.error("Event not found with id: {}", id);
                    return new ResourceNotFoundException("Event not found with id: " + id);
                });
        
        if (eventRepository.existsByNameIgnoreCaseAndIdNot(eventDto.getName(), id)) {
            log.error("Event with name '{}' already exists", eventDto.getName());
            throw new ResourceAlreadyExistsException("Event with name '" + eventDto.getName() + "' already exists");
        }
        
        existingEvent.setName(eventDto.getName());
        existingEvent.setDescription(eventDto.getDescription());
        if (eventDto.getIsActive() != null) {
            existingEvent.setIsActive(eventDto.getIsActive());
        }
        
        Event updatedEvent = eventRepository.save(existingEvent);
        log.info("Successfully updated event with id: {}", updatedEvent.getId());
        return eventMapper.toDto(updatedEvent);
    }
    
    public void deleteEvent(Long id) {
        log.info("Soft deleting event with id: {}", id);
        
        Event event = eventRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> {
                    log.error("Event not found with id: {}", id);
                    return new ResourceNotFoundException("Event not found with id: " + id);
                });
        
        event.setIsActive(false);
        eventRepository.save(event);
        log.info("Successfully soft deleted event with id: {}", id);
    }
    
    public List<EventDto> searchEvents(String name) {
        log.info("Searching events with name containing: {}", name);
        List<Event> events = eventRepository.findByNameContainingIgnoreCaseAndIsActiveTrue(name);
        log.info("Found {} events matching search criteria", events.size());
        return events.stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }
}
