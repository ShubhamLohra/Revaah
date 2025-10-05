package com.wedding.eventcustomization.mapper;

import com.wedding.eventcustomization.dto.ImageDto;
import com.wedding.eventcustomization.entity.Image;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImageMapper {
    
    public ImageDto toDto(Image image) {
        if (image == null) {
            return null;
        }
        
        return ImageDto.builder()
                .id(image.getId())
                .name(image.getName())
                .imageUrl(image.getImageUrl())
                .description(image.getDescription())
                .stageId(image.getStage() != null ? image.getStage().getId() : null)
                .stageName(image.getStage() != null ? image.getStage().getName() : null)
                .isActive(image.getIsActive())
                .displayOrder(image.getDisplayOrder())
                .createdAt(image.getCreatedAt())
                .updatedAt(image.getUpdatedAt())
                .build();
    }
    
    public Image toEntity(ImageDto imageDto) {
        if (imageDto == null) {
            return null;
        }
        
        return Image.builder()
                .id(imageDto.getId())
                .name(imageDto.getName())
                .imageUrl(imageDto.getImageUrl())
                .description(imageDto.getDescription())
                .isActive(imageDto.getIsActive())
                .displayOrder(imageDto.getDisplayOrder())
                .build();
    }
    
    public List<ImageDto> toDtoList(List<Image> images) {
        if (images == null) {
            return null;
        }
        
        return images.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
