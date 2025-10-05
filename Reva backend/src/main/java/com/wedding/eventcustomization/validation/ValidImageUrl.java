package com.wedding.eventcustomization.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageUrlValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImageUrl {
    
    String message() default "Invalid image URL. Must be a valid HTTP/HTTPS URL with image extension (jpg, jpeg, png, gif, bmp, webp)";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
