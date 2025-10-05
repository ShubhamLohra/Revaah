package com.wedding.eventcustomization.controller;

import com.wedding.eventcustomization.dto.ApiResponse;
import com.wedding.eventcustomization.dto.PdfGenerationRequest;
import com.wedding.eventcustomization.service.PdfGenerationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/pdf")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "PDF Generation", description = "APIs for generating wedding customization PDFs")
public class PdfController {
    
    private final PdfGenerationService pdfGenerationService;
    
    @PostMapping("/generate")
    @Operation(summary = "Generate wedding customization PDF", 
               description = "Generate a PDF with selected wedding decorations and images")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "PDF generated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "PDF generation failed")
    })
    public ResponseEntity<byte[]> generatePdf(@Valid @RequestBody PdfGenerationRequest request) {
        try {
            log.info("Received PDF generation request for event: {} with {} images", 
                    request.getEventName(), request.getSelectedImages().size());
            
            byte[] pdfBytes = pdfGenerationService.generateWeddingCustomizationPdf(request);
            
            String fileName = generateFileName(request.getEventName());
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentLength(pdfBytes.length);
            
            log.info("Successfully generated PDF: {} ({} bytes)", fileName, pdfBytes.length);
            
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
            
        } catch (IOException e) {
            log.error("Error generating PDF: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Unexpected error during PDF generation: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/generate-base64")
    @Operation(summary = "Generate wedding customization PDF as Base64", 
               description = "Generate a PDF and return it as Base64 encoded string")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "PDF generated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "PDF generation failed")
    })
    public ResponseEntity<ApiResponse<String>> generatePdfBase64(@Valid @RequestBody PdfGenerationRequest request) {
        try {
            log.info("Received PDF generation request (Base64) for event: {} with {} images", 
                    request.getEventName(), request.getSelectedImages().size());
            
            byte[] pdfBytes = pdfGenerationService.generateWeddingCustomizationPdf(request);
            String base64Pdf = java.util.Base64.getEncoder().encodeToString(pdfBytes);
            
            log.info("Successfully generated PDF as Base64 ({} bytes)", pdfBytes.length);
            
            return ResponseEntity.ok(ApiResponse.success("PDF generated successfully", base64Pdf));
            
        } catch (IOException e) {
            log.error("Error generating PDF: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("PDF generation failed: " + e.getMessage()));
        } catch (Exception e) {
            log.error("Unexpected error during PDF generation: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("PDF generation failed: " + e.getMessage()));
        }
    }
    
    private String generateFileName(String eventName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String sanitizedEventName = eventName.replaceAll("[^a-zA-Z0-9]", "_");
        return String.format("wedding_customization_%s_%s.pdf", sanitizedEventName, timestamp);
    }
}
