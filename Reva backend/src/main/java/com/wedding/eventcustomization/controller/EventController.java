package com.wedding.eventcustomization.controller;

import com.wedding.eventcustomization.dto.ApiResponse;
import com.wedding.eventcustomization.dto.EventDto;
import com.wedding.eventcustomization.service.EventService;
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

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Event Management", description = "APIs for managing wedding events")
public class EventController {
    
    private final EventService eventService;
    
    @GetMapping
    @Operation(summary = "Get all events", description = "Retrieve all active wedding events")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved events"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<List<EventDto>>> getAllEvents() {
        log.info("Received request to get all events");
        List<EventDto> events = eventService.getAllEvents();
        return ResponseEntity.ok(ApiResponse.success("Events retrieved successfully", events));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get event by ID", description = "Retrieve a specific event by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved event"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Event not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<EventDto>> getEventById(
            @Parameter(description = "Event ID", required = true) @PathVariable Long id) {
        log.info("Received request to get event by id: {}", id);
        EventDto event = eventService.getEventById(id);
        return ResponseEntity.ok(ApiResponse.success("Event retrieved successfully", event));
    }
    
    @PostMapping
    @Operation(summary = "Create new event", description = "Create a new wedding event")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Event created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Event already exists"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<EventDto>> createEvent(
            @Valid @RequestBody EventDto eventDto) {
        log.info("Received request to create event: {}", eventDto.getName());
        EventDto createdEvent = eventService.createEvent(eventDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Event created successfully", createdEvent));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update event", description = "Update an existing event")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Event updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Event not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Event name already exists"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<EventDto>> updateEvent(
            @Parameter(description = "Event ID", required = true) @PathVariable Long id,
            @Valid @RequestBody EventDto eventDto) {
        log.info("Received request to update event with id: {}", id);
        EventDto updatedEvent = eventService.updateEvent(id, eventDto);
        return ResponseEntity.ok(ApiResponse.success("Event updated successfully", updatedEvent));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete event", description = "Soft delete an event")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Event deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Event not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<Void>> deleteEvent(
            @Parameter(description = "Event ID", required = true) @PathVariable Long id) {
        log.info("Received request to delete event with id: {}", id);
        eventService.deleteEvent(id);
        return ResponseEntity.ok(ApiResponse.success("Event deleted successfully", null));
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search events", description = "Search events by name")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Search completed successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid search parameter"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<List<EventDto>>> searchEvents(
            @Parameter(description = "Event name to search for") @RequestParam String name) {
        log.info("Received request to search events with name: {}", name);
        List<EventDto> events = eventService.searchEvents(name);
        return ResponseEntity.ok(ApiResponse.success("Search completed successfully", events));
    }
}
