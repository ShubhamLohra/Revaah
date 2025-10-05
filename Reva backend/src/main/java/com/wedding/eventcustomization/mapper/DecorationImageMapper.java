package com.wedding.eventcustomization.mapper;

import com.wedding.eventcustomization.dto.DecorationImageDto;
import com.wedding.eventcustomization.entity.DecorationImage;

import java.util.List;
import java.util.stream.Collectors;

public class DecorationImageMapper {
    
    public static DecorationImageDto toDto(DecorationImage decorationImage) {
        if (decorationImage == null) {
            return null;
        }
        
        return DecorationImageDto.builder()
                .id(decorationImage.getId())
                .name(decorationImage.getName())
                .imageUrl(decorationImage.getImageUrl())
                .description(decorationImage.getDescription())
                .isActive(decorationImage.getIsActive())
                .displayOrder(decorationImage.getDisplayOrder())
                .decorationTypeId(decorationImage.getDecorationType() != null ? decorationImage.getDecorationType().getId() : null)
                .decorationTypeName(decorationImage.getDecorationType() != null ? decorationImage.getDecorationType().getName() : null)
                .createdAt(decorationImage.getCreatedAt())
                .updatedAt(decorationImage.getUpdatedAt())
                .build();
    }
    
    public static List<DecorationImageDto> toDtoList(List<DecorationImage> decorationImages) {
        if (decorationImages == null) {
            return null;
        }
        
        return decorationImages.stream()
                .map(DecorationImageMapper::toDto)
                .collect(Collectors.toList());
    }
}
