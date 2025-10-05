package com.wedding.eventcustomization.controller;

import com.wedding.eventcustomization.dto.ApiResponse;
import com.wedding.eventcustomization.dto.DecorationTypeDto;
import com.wedding.eventcustomization.service.DecorationTypeService;
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
@RequestMapping("/api/v1/decoration-types")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Decoration Type", description = "API for managing decoration types")
public class DecorationTypeController {
    
    private final DecorationTypeService decorationTypeService;
    
    @GetMapping
    @Operation(summary = "Get all decoration types", description = "Retrieve all active decoration types")
    public ResponseEntity<ApiResponse<List<DecorationTypeDto>>> getAllDecorationTypes() {
        try {
            log.info("Getting all decoration types");
            List<DecorationTypeDto> decorationTypes = decorationTypeService.getAllDecorationTypes();
            return ResponseEntity.ok(ApiResponse.success("Decoration types retrieved successfully", decorationTypes));
        } catch (Exception e) {
            log.error("Error getting all decoration types", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve decoration types: " + e.getMessage()));
        }
    }
    
    @GetMapping("/event/{eventId}")
    @Operation(summary = "Get decoration types by event", description = "Retrieve all decoration types for a specific event")
    public ResponseEntity<ApiResponse<List<DecorationTypeDto>>> getDecorationTypesByEventId(@PathVariable Long eventId) {
        try {
            log.info("Getting decoration types for event ID: {}", eventId);
            List<DecorationTypeDto> decorationTypes = decorationTypeService.getDecorationTypesByEventId(eventId);
            return ResponseEntity.ok(ApiResponse.success("Decoration types retrieved successfully", decorationTypes));
        } catch (Exception e) {
            log.error("Error getting decoration types for event ID: {}", eventId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve decoration types: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get decoration type by ID", description = "Retrieve a specific decoration type by its ID")
    public ResponseEntity<ApiResponse<DecorationTypeDto>> getDecorationTypeById(@PathVariable Long id) {
        try {
            log.info("Getting decoration type with ID: {}", id);
            DecorationTypeDto decorationType = decorationTypeService.getDecorationTypeById(id);
            return ResponseEntity.ok(ApiResponse.success("Decoration type retrieved successfully", decorationType));
        } catch (Exception e) {
            log.error("Error getting decoration type with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve decoration type: " + e.getMessage()));
        }
    }
    
    @PostMapping
    @Operation(summary = "Create decoration type", description = "Create a new decoration type")
    public ResponseEntity<ApiResponse<DecorationTypeDto>> createDecorationType(@Valid @RequestBody DecorationTypeDto decorationTypeDto) {
        try {
            log.info("Creating decoration type: {}", decorationTypeDto.getName());
            DecorationTypeDto createdDecorationType = decorationTypeService.createDecorationType(decorationTypeDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Decoration type created successfully", createdDecorationType));
        } catch (Exception e) {
            log.error("Error creating decoration type", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create decoration type: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update decoration type", description = "Update an existing decoration type")
    public ResponseEntity<ApiResponse<DecorationTypeDto>> updateDecorationType(@PathVariable Long id, @Valid @RequestBody DecorationTypeDto decorationTypeDto) {
        try {
            log.info("Updating decoration type with ID: {}", id);
            DecorationTypeDto updatedDecorationType = decorationTypeService.updateDecorationType(id, decorationTypeDto);
            return ResponseEntity.ok(ApiResponse.success("Decoration type updated successfully", updatedDecorationType));
        } catch (Exception e) {
            log.error("Error updating decoration type with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to update decoration type: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete decoration type", description = "Soft delete a decoration type")
    public ResponseEntity<ApiResponse<Void>> deleteDecorationType(@PathVariable Long id) {
        try {
            log.info("Deleting decoration type with ID: {}", id);
            decorationTypeService.deleteDecorationType(id);
            return ResponseEntity.ok(ApiResponse.success("Decoration type deleted successfully", null));
        } catch (Exception e) {
            log.error("Error deleting decoration type with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to delete decoration type: " + e.getMessage()));
        }
    }
}
