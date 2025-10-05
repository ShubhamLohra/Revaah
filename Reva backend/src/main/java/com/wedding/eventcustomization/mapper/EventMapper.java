package com.wedding.eventcustomization.mapper;

import com.wedding.eventcustomization.dto.EventDto;
import com.wedding.eventcustomization.dto.DecorationTypeDto;
import com.wedding.eventcustomization.entity.Event;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {
    
    public EventDto toDto(Event event) {
        if (event == null) {
            return null;
        }
        
        return EventDto.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .isActive(event.getIsActive())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .decorationTypes(event.getDecorationTypes() != null ? 
                    event.getDecorationTypes().stream()
                        .map(decorationType -> DecorationTypeDto.builder()
                                .id(decorationType.getId())
                                .name(decorationType.getName())
                                .description(decorationType.getDescription())
                                .eventId(decorationType.getEvent().getId())
                                .eventName(decorationType.getEvent().getName())
                                .isActive(decorationType.getIsActive())
                                .createdAt(decorationType.getCreatedAt())
                                .updatedAt(decorationType.getUpdatedAt())
                                .build())
                        .collect(Collectors.toList()) : null)
                .build();
    }
    
    public Event toEntity(EventDto eventDto) {
        if (eventDto == null) {
            return null;
        }
        
        return Event.builder()
                .id(eventDto.getId())
                .name(eventDto.getName())
                .description(eventDto.getDescription())
                .isActive(eventDto.getIsActive())
                .build();
    }
    
    public List<EventDto> toDtoList(List<Event> events) {
        if (events == null) {
            return null;
        }
        
        return events.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
