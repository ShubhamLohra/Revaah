package com.wedding.eventcustomization.controller;

import com.wedding.eventcustomization.dto.ApiResponse;
import com.wedding.eventcustomization.dto.DecorationImageDto;
import com.wedding.eventcustomization.service.DecorationImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/decoration-images")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Decoration Image", description = "API for managing decoration images")
public class DecorationImageController {
    
    private final DecorationImageService decorationImageService;
    
    @GetMapping
    @Operation(summary = "Get all decoration images", description = "Retrieve all active decoration images")
    public ResponseEntity<ApiResponse<List<DecorationImageDto>>> getAllDecorationImages() {
        try {
            log.info("Getting all decoration images");
            List<DecorationImageDto> decorationImages = decorationImageService.getAllDecorationImages();
            return ResponseEntity.ok(ApiResponse.success("Decoration images retrieved successfully", decorationImages));
        } catch (Exception e) {
            log.error("Error getting all decoration images", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve decoration images: " + e.getMessage()));
        }
    }
    
    @GetMapping("/decoration-type/{decorationTypeId}")
    @Operation(summary = "Get decoration images by decoration type", description = "Retrieve all decoration images for a specific decoration type")
    public ResponseEntity<ApiResponse<List<DecorationImageDto>>> getDecorationImagesByDecorationTypeId(@PathVariable Long decorationTypeId) {
        try {
            log.info("Getting decoration images for decoration type ID: {}", decorationTypeId);
            List<DecorationImageDto> decorationImages = decorationImageService.getDecorationImagesByDecorationTypeId(decorationTypeId);
            return ResponseEntity.ok(ApiResponse.success("Decoration images retrieved successfully", decorationImages));
        } catch (Exception e) {
            log.error("Error getting decoration images for decoration type ID: {}", decorationTypeId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve decoration images: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get decoration image by ID", description = "Retrieve a specific decoration image by its ID")
    public ResponseEntity<ApiResponse<DecorationImageDto>> getDecorationImageById(@PathVariable Long id) {
        try {
            log.info("Getting decoration image with ID: {}", id);
            DecorationImageDto decorationImage = decorationImageService.getDecorationImageById(id);
            return ResponseEntity.ok(ApiResponse.success("Decoration image retrieved successfully", decorationImage));
        } catch (Exception e) {
            log.error("Error getting decoration image with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve decoration image: " + e.getMessage()));
        }
    }
    
    @PostMapping
    @Operation(summary = "Create decoration image", description = "Create a new decoration image")
    public ResponseEntity<ApiResponse<DecorationImageDto>> createDecorationImage(@Valid @RequestBody DecorationImageDto decorationImageDto) {
        try {
            log.info("Creating decoration image: {}", decorationImageDto.getName());
            DecorationImageDto createdDecorationImage = decorationImageService.createDecorationImage(decorationImageDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Decoration image created successfully", createdDecorationImage));
        } catch (Exception e) {
            log.error("Error creating decoration image", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create decoration image: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update decoration image", description = "Update an existing decoration image")
    public ResponseEntity<ApiResponse<DecorationImageDto>> updateDecorationImage(@PathVariable Long id, @Valid @RequestBody DecorationImageDto decorationImageDto) {
        try {
            log.info("Updating decoration image with ID: {}", id);
            DecorationImageDto updatedDecorationImage = decorationImageService.updateDecorationImage(id, decorationImageDto);
            return ResponseEntity.ok(ApiResponse.success("Decoration image updated successfully", updatedDecorationImage));
        } catch (Exception e) {
            log.error("Error updating decoration image with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to update decoration image: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete decoration image", description = "Soft delete a decoration image")
    public ResponseEntity<ApiResponse<Void>> deleteDecorationImage(@PathVariable Long id) {
        try {
            log.info("Deleting decoration image with ID: {}", id);
            decorationImageService.deleteDecorationImage(id);
            return ResponseEntity.ok(ApiResponse.success("Decoration image deleted successfully", null));
        } catch (Exception e) {
            log.error("Error deleting decoration image with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to delete decoration image: " + e.getMessage()));
        }
    }
}
