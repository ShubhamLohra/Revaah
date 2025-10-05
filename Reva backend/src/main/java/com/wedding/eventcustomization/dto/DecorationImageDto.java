package com.wedding.eventcustomization.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecorationImageDto {
    
    private Long id;
    private String name;
    private String imageUrl;
    private String description;
    private Boolean isActive;
    private Integer displayOrder;
    private Long decorationTypeId;
    private String decorationTypeName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
