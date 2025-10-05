package com.wedding.eventcustomization.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    
    private Long id;
    
    @NotBlank(message = "Image name is required")
    @Size(max = 100, message = "Image name must not exceed 100 characters")
    private String name;
    
    @NotBlank(message = "Image URL is required")
    @Size(max = 500, message = "Image URL must not exceed 500 characters")
    private String imageUrl;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
    
    @NotNull(message = "Stage ID is required")
    private Long stageId;
    
    private String stageName;
    
    private Boolean isActive;
    
    private Integer displayOrder;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}
