package com.wedding.eventcustomization.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PdfGenerationRequest {
    
    @NotNull(message = "Event ID is required")
    private Long eventId;
    
    @NotBlank(message = "Event name is required")
    private String eventName;
    
    @NotNull(message = "Selected images are required")
    private List<SelectedImage> selectedImages;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SelectedImage {
        @NotNull(message = "Stage ID is required")
        private Long stageId;
        
        @NotBlank(message = "Stage name is required")
        private String stageName;
        
        @NotNull(message = "Image ID is required")
        private Long imageId;
        
        @NotBlank(message = "Image name is required")
        private String imageName;
        
        @NotBlank(message = "Image URL is required")
        private String imageUrl;
        
        private String description;
    }
}
