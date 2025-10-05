package com.wedding.eventcustomization.controller;

import com.wedding.eventcustomization.dto.ApiResponse;
import com.wedding.eventcustomization.dto.StageDto;
import com.wedding.eventcustomization.service.StageService;
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

// @RestController  // Disabled - using new DecorationTypeController instead
@RequestMapping("/api/v1/stages")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Stage Management", description = "APIs for managing wedding event stages")
public class StageController {
    
    private final StageService stageService;
    
    @GetMapping("/event/{eventId}")
    @Operation(summary = "Get stages by event ID", description = "Retrieve all stages for a specific event")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved stages"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Event not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<List<StageDto>>> getStagesByEventId(
            @Parameter(description = "Event ID", required = true) @PathVariable Long eventId) {
        log.info("Received request to get stages for event id: {}", eventId);
        List<StageDto> stages = stageService.getAllStagesByEventId(eventId);
        return ResponseEntity.ok(ApiResponse.success("Stages retrieved successfully", stages));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get stage by ID", description = "Retrieve a specific stage by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved stage"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Stage not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<StageDto>> getStageById(
            @Parameter(description = "Stage ID", required = true) @PathVariable Long id) {
        log.info("Received request to get stage by id: {}", id);
        StageDto stage = stageService.getStageById(id);
        return ResponseEntity.ok(ApiResponse.success("Stage retrieved successfully", stage));
    }
    
    @PostMapping
    @Operation(summary = "Create new stage", description = "Create a new stage for an event")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Stage created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Event not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Stage already exists for this event"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<StageDto>> createStage(
            @Valid @RequestBody StageDto stageDto) {
        log.info("Received request to create stage: {} for event id: {}", stageDto.getName(), stageDto.getEventId());
        StageDto createdStage = stageService.createStage(stageDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Stage created successfully", createdStage));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update stage", description = "Update an existing stage")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Stage updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Stage or Event not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Stage name already exists for this event"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<StageDto>> updateStage(
            @Parameter(description = "Stage ID", required = true) @PathVariable Long id,
            @Valid @RequestBody StageDto stageDto) {
        log.info("Received request to update stage with id: {}", id);
        StageDto updatedStage = stageService.updateStage(id, stageDto);
        return ResponseEntity.ok(ApiResponse.success("Stage updated successfully", updatedStage));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete stage", description = "Soft delete a stage")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Stage deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Stage not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<Void>> deleteStage(
            @Parameter(description = "Stage ID", required = true) @PathVariable Long id) {
        log.info("Received request to delete stage with id: {}", id);
        stageService.deleteStage(id);
        return ResponseEntity.ok(ApiResponse.success("Stage deleted successfully", null));
    }
    
    @GetMapping("/event/{eventId}/search")
    @Operation(summary = "Search stages by event", description = "Search stages for a specific event by name")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Search completed successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid search parameters"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Event not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<List<StageDto>>> searchStagesByEventId(
            @Parameter(description = "Event ID", required = true) @PathVariable Long eventId,
            @Parameter(description = "Stage name to search for") @RequestParam String name) {
        log.info("Received request to search stages for event id: {} with name: {}", eventId, name);
        List<StageDto> stages = stageService.searchStagesByEventId(eventId, name);
        return ResponseEntity.ok(ApiResponse.success("Search completed successfully", stages));
    }
}
