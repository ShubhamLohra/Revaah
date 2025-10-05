package com.wedding.eventcustomization.service;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.wedding.eventcustomization.dto.PdfGenerationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfGenerationService {
    
    public byte[] generateWeddingCustomizationPdf(PdfGenerationRequest request) throws IOException {
        log.info("Generating PDF for event: {} with {} selected images", 
                request.getEventName(), request.getSelectedImages().size());
        
        String htmlContent = generateHtmlContent(request);
        
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            HtmlConverter.convertToPdf(htmlContent, outputStream);
            byte[] pdfBytes = outputStream.toByteArray();
            
            log.info("Successfully generated PDF with {} bytes", pdfBytes.length);
            return pdfBytes;
        }
    }
    
    private String generateHtmlContent(PdfGenerationRequest request) {
        StringBuilder html = new StringBuilder();
        
        // HTML Header
        html.append("<!DOCTYPE html>")
            .append("<html>")
            .append("<head>")
            .append("<meta charset='UTF-8'>")
            .append("<title>Wedding Customization - ").append(request.getEventName()).append("</title>")
            .append("<style>")
            .append("body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f9f9f9; }")
            .append(".header { text-align: center; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px; border-radius: 10px; margin-bottom: 30px; }")
            .append(".header h1 { margin: 0; font-size: 2.5em; }")
            .append(".header p { margin: 10px 0 0 0; font-size: 1.2em; opacity: 0.9; }")
            .append(".content { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }")
            .append(".section { margin-bottom: 40px; }")
            .append(".section h2 { color: #333; border-bottom: 3px solid #667eea; padding-bottom: 10px; margin-bottom: 20px; }")
            .append(".image-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 20px; }")
            .append(".image-item { text-align: center; padding: 20px; border: 2px solid #e0e0e0; border-radius: 10px; background: #fafafa; }")
            .append(".image-item img { max-width: 100%; height: 200px; object-fit: cover; border-radius: 8px; margin-bottom: 15px; }")
            .append(".image-item h3 { color: #667eea; margin: 10px 0; }")
            .append(".image-item p { color: #666; font-size: 0.9em; line-height: 1.4; }")
            .append(".footer { text-align: center; margin-top: 40px; padding: 20px; color: #666; border-top: 1px solid #e0e0e0; }")
            .append(".generated-date { font-style: italic; }")
            .append("</style>")
            .append("</head>")
            .append("<body>");
        
        // Header Section
        html.append("<div class='header'>")
            .append("<h1>🎉 ").append(request.getEventName()).append(" Customization</h1>")
            .append("<p>Your Perfect Wedding Event Configuration</p>")
            .append("</div>");
        
        // Content Section
        html.append("<div class='content'>")
            .append("<div class='section'>")
            .append("<h2>📋 Selected Decorations</h2>")
            .append("<div class='image-grid'>");
        
        // Add selected images
        for (PdfGenerationRequest.SelectedImage image : request.getSelectedImages()) {
            html.append("<div class='image-item'>")
                .append("<img src='").append(image.getImageUrl()).append("' alt='").append(image.getImageName()).append("'>")
                .append("<h3>").append(image.getStageName()).append("</h3>")
                .append("<p><strong>").append(image.getImageName()).append("</strong></p>");
            
            if (image.getDescription() != null && !image.getDescription().trim().isEmpty()) {
                html.append("<p>").append(image.getDescription()).append("</p>");
            }
            
            html.append("</div>");
        }
        
        html.append("</div>") // Close image-grid
            .append("</div>") // Close section
            .append("</div>"); // Close content
        
        // Footer
        html.append("<div class='footer'>")
            .append("<p>Generated on: <span class='generated-date'>")
            .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' h:mm a")))
            .append("</span></p>")
            .append("<p>Thank you for choosing our wedding customization service! 💕</p>")
            .append("</div>");
        
        html.append("</body></html>");
        
        return html.toString();
    }
}
