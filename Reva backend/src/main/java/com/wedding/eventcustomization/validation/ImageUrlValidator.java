package com.wedding.eventcustomization.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class ImageUrlValidator implements ConstraintValidator<ValidImageUrl, String> {
    
    private static final Pattern IMAGE_EXTENSION_PATTERN = Pattern.compile(".*\\.(jpg|jpeg|png|gif|bmp|webp)$", Pattern.CASE_INSENSITIVE);
    private static final Pattern URL_PATTERN = Pattern.compile("^https?://.*");
    
    @Override
    public boolean isValid(String imageUrl, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(imageUrl)) {
            return false;
        }
        
        // Check if it's a valid URL
        try {
            new URL(imageUrl);
        } catch (MalformedURLException e) {
            return false;
        }
        
        // Check if it starts with http or https
        if (!URL_PATTERN.matcher(imageUrl).matches()) {
            return false;
        }
        
        // For Unsplash URLs, we'll be more lenient - just check if it's a valid URL
        // For other URLs, check if it has a valid image extension
        if (imageUrl.contains("unsplash.com") || imageUrl.contains("images.unsplash.com")) {
            return true; // Allow Unsplash URLs without extension check
        }
        
        // Check if it has a valid image extension for other URLs
        return IMAGE_EXTENSION_PATTERN.matcher(imageUrl).matches();
    }
}
