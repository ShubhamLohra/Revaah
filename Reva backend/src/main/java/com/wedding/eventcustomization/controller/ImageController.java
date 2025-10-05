package com.wedding.eventcustomization.controller;

import com.wedding.eventcustomization.dto.ApiResponse;
import com.wedding.eventcustomization.dto.ImageDto;
import com.wedding.eventcustomization.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController  // Disabled - using new DecorationImageController instead
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Image Management", description = "APIs for managing wedding stage images")
public class ImageController {
    
    private final ImageService imageService;
    
    @GetMapping("/stage/{stageId}")
    @Operation(summary = "Get images by stage ID", description = "Retrieve all images for a specific stage")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved images"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Stage not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<List<ImageDto>>> getImagesByStageId(
            @Parameter(description = "Stage ID", required = true) @PathVariable Long stageId) {
        log.info("Received request to get images for stage id: {}", stageId);
        List<ImageDto> images = imageService.getAllImagesByStageId(stageId);
        return ResponseEntity.ok(ApiResponse.success("Images retrieved successfully", images));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get image by ID", description = "Retrieve a specific image by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved image"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Image not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<ImageDto>> getImageById(
            @Parameter(description = "Image ID", required = true) @PathVariable Long id) {
        log.info("Received request to get image by id: {}", id);
        ImageDto image = imageService.getImageById(id);
        return ResponseEntity.ok(ApiResponse.success("Image retrieved successfully", image));
    }
    
    @PostMapping
    @Operation(summary = "Create new image", description = "Create a new image for a stage")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Image created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Stage not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Image already exists for this stage"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<ImageDto>> createImage(
            @Valid @RequestBody ImageDto imageDto) {
        log.info("Received request to create image: {} for stage id: {}", imageDto.getName(), imageDto.getStageId());
        ImageDto createdImage = imageService.createImage(imageDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Image created successfully", createdImage));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update image", description = "Update an existing image")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Image updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Image or Stage not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Image name already exists for this stage"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<ImageDto>> updateImage(
            @Parameter(description = "Image ID", required = true) @PathVariable Long id,
            @Valid @RequestBody ImageDto imageDto) {
        log.info("Received request to update image with id: {}", id);
        ImageDto updatedImage = imageService.updateImage(id, imageDto);
        return ResponseEntity.ok(ApiResponse.success("Image updated successfully", updatedImage));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete image", description = "Soft delete an image")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Image deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Image not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<Void>> deleteImage(
            @Parameter(description = "Image ID", required = true) @PathVariable Long id) {
        log.info("Received request to delete image with id: {}", id);
        imageService.deleteImage(id);
        return ResponseEntity.ok(ApiResponse.success("Image deleted successfully", null));
    }
    
    @GetMapping("/stage/{stageId}/search")
    @Operation(summary = "Search images by stage", description = "Search images for a specific stage by name")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Search completed successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid search parameters"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Stage not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<List<ImageDto>>> searchImagesByStageId(
            @Parameter(description = "Stage ID", required = true) @PathVariable Long stageId,
            @Parameter(description = "Image name to search for") @RequestParam String name) {
        log.info("Received request to search images for stage id: {} with name: {}", stageId, name);
        List<ImageDto> images = imageService.searchImagesByStageId(stageId, name);
        return ResponseEntity.ok(ApiResponse.success("Search completed successfully", images));
    }
}
