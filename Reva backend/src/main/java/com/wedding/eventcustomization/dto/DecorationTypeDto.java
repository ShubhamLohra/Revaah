package com.wedding.eventcustomization.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecorationTypeDto {
    
    private Long id;
    private String name;
    private String description;
    private Boolean isActive;
    private Integer displayOrder;
    private Long eventId;
    private String eventName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<DecorationImageDto> images;
}
