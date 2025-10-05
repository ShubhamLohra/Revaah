package com.wedding.eventcustomization.mapper;

import com.wedding.eventcustomization.dto.ImageDto;
import com.wedding.eventcustomization.dto.StageDto;
import com.wedding.eventcustomization.entity.Stage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StageMapper {
    
    public StageDto toDto(Stage stage) {
        if (stage == null) {
            return null;
        }
        
        return StageDto.builder()
                .id(stage.getId())
                .name(stage.getName())
                .description(stage.getDescription())
                .eventId(stage.getEvent() != null ? stage.getEvent().getId() : null)
                .eventName(stage.getEvent() != null ? stage.getEvent().getName() : null)
                .isActive(stage.getIsActive())
                .createdAt(stage.getCreatedAt())
                .updatedAt(stage.getUpdatedAt())
                .images(stage.getImages() != null ? 
                    stage.getImages().stream()
                        .map(image -> ImageDto.builder()
                                .id(image.getId())
                                .name(image.getName())
                                .imageUrl(image.getImageUrl())
                                .description(image.getDescription())
                                .stageId(image.getStage().getId())
                                .stageName(image.getStage().getName())
                                .isActive(image.getIsActive())
                                .displayOrder(image.getDisplayOrder())
                                .createdAt(image.getCreatedAt())
                                .updatedAt(image.getUpdatedAt())
                                .build())
                        .collect(Collectors.toList()) : null)
                .build();
    }
    
    public Stage toEntity(StageDto stageDto) {
        if (stageDto == null) {
            return null;
        }
        
        return Stage.builder()
                .id(stageDto.getId())
                .name(stageDto.getName())
                .description(stageDto.getDescription())
                .isActive(stageDto.getIsActive())
                .build();
    }
    
    public List<StageDto> toDtoList(List<Stage> stages) {
        if (stages == null) {
            return null;
        }
        
        return stages.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
