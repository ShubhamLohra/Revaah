package com.wedding.eventcustomization.mapper;

import com.wedding.eventcustomization.dto.DecorationTypeDto;
import com.wedding.eventcustomization.entity.DecorationType;

import java.util.List;
import java.util.stream.Collectors;

public class DecorationTypeMapper {
    
    public static DecorationTypeDto toDto(DecorationType decorationType) {
        if (decorationType == null) {
            return null;
        }
        
        return DecorationTypeDto.builder()
                .id(decorationType.getId())
                .name(decorationType.getName())
                .description(decorationType.getDescription())
                .isActive(decorationType.getIsActive())
                .displayOrder(decorationType.getDisplayOrder())
                .eventId(decorationType.getEvent() != null ? decorationType.getEvent().getId() : null)
                .eventName(decorationType.getEvent() != null ? decorationType.getEvent().getName() : null)
                .createdAt(decorationType.getCreatedAt())
                .updatedAt(decorationType.getUpdatedAt())
                .images(decorationType.getImages() != null ? 
                    decorationType.getImages().stream()
                        .map(DecorationImageMapper::toDto)
                        .collect(Collectors.toList()) : null)
                .build();
    }
    
    public static List<DecorationTypeDto> toDtoList(List<DecorationType> decorationTypes) {
        if (decorationTypes == null) {
            return null;
        }
        
        return decorationTypes.stream()
                .map(DecorationTypeMapper::toDto)
                .collect(Collectors.toList());
    }
}
