package com.wedding.eventcustomization.config;

import com.wedding.eventcustomization.entity.Event;
import com.wedding.eventcustomization.entity.Stage;
import com.wedding.eventcustomization.entity.Image;
import com.wedding.eventcustomization.repository.EventRepository;
import com.wedding.eventcustomization.repository.StageRepository;
import com.wedding.eventcustomization.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

// @Component  // Disabled - using NewDataInitializer instead
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final EventRepository eventRepository;
    private final StageRepository stageRepository;
    private final ImageRepository imageRepository;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("Starting data initialization...");
        
        // Initialize events
        initializeEvents();
        
        log.info("Data initialization completed successfully");
    }
    
    private void initializeEvents() {
        if (eventRepository.count() > 0) {
            log.info("Events already exist, skipping initialization");
            return;
        }
        
        log.info("Initializing events data...");
        
        // Create Wedding Event
        Event wedding = Event.builder()
                .name("Wedding")
                .description("Main wedding ceremony event")
                .isActive(true)
                .build();
        wedding = eventRepository.save(wedding);
        log.info("Created event: {}", wedding.getName());
        
        // Create Wedding Stages
        createWeddingStages(wedding);
        
        // Create other events
        createOtherEvents();
    }
    
    private void createWeddingStages(Event wedding) {
        // Entrance Gate Stage
        Stage entranceGate = Stage.builder()
                .name("Entrance Gate")
                .description("Wedding entrance gate decoration")
                .event(wedding)
                .isActive(true)
                .build();
        entranceGate = stageRepository.save(entranceGate);
        log.info("Created stage: {} for event: {}", entranceGate.getName(), wedding.getName());
        
        // Create Entrance Gate Images
        createEntranceGateImages(entranceGate);
        
        // Mandap Stage
        Stage mandap = Stage.builder()
                .name("Mandap")
                .description("Wedding mandap decoration")
                .event(wedding)
                .isActive(true)
                .build();
        mandap = stageRepository.save(mandap);
        log.info("Created stage: {} for event: {}", mandap.getName(), wedding.getName());
        
        // Create Mandap Images
        createMandapImages(mandap);
        
        // Food Stage
        Stage foodStage = Stage.builder()
                .name("Food Stage")
                .description("Wedding food stage decoration")
                .event(wedding)
                .isActive(true)
                .build();
        foodStage = stageRepository.save(foodStage);
        log.info("Created stage: {} for event: {}", foodStage.getName(), wedding.getName());
        
        // Create Food Stage Images
        createFoodStageImages(foodStage);
        
        // Create additional stages for more variety
        createAdditionalWeddingStages(wedding);
    }
    
    private void createEntranceGateImages(Stage entranceGate) {
        List<Image> entranceImages = Arrays.asList(
                Image.builder()
                        .name("Entrance Gate 1")
                        .description("Classic floral entrance gate design with roses and marigolds")
                        .imageUrl("https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center")
                        .stage(entranceGate)
                        .isActive(true)
                        .displayOrder(1)
                        .build(),
                Image.builder()
                        .name("Entrance Gate 2")
                        .description("Modern geometric entrance gate design with LED lights")
                        .imageUrl("https://images.unsplash.com/photo-1511795409834-ef04bbd61622?w=800&h=600&fit=crop&crop=center")
                        .stage(entranceGate)
                        .isActive(true)
                        .displayOrder(2)
                        .build(),
                Image.builder()
                        .name("Entrance Gate 3")
                        .description("Traditional marigold entrance gate design with golden theme")
                        .imageUrl("https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center&auto=format&q=80")
                        .stage(entranceGate)
                        .isActive(true)
                        .displayOrder(3)
                        .build()
        );
        
        imageRepository.saveAll(entranceImages);
        log.info("Created {} images for stage: {}", entranceImages.size(), entranceGate.getName());
    }
    
    private void createMandapImages(Stage mandap) {
        List<Image> mandapImages = Arrays.asList(
                Image.builder()
                        .name("Mandap 1")
                        .description("Traditional four-pillar mandap design with floral decorations")
                        .imageUrl("https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center&auto=format&q=80")
                        .stage(mandap)
                        .isActive(true)
                        .displayOrder(1)
                        .build(),
                Image.builder()
                        .name("Mandap 2")
                        .description("Modern minimalist mandap design with clean lines")
                        .imageUrl("https://images.unsplash.com/photo-1511795409834-ef04bbd61622?w=800&h=600&fit=crop&crop=center&auto=format&q=80")
                        .stage(mandap)
                        .isActive(true)
                        .displayOrder(2)
                        .build(),
                Image.builder()
                        .name("Mandap 3")
                        .description("Royal palace-style mandap design with ornate decorations")
                        .imageUrl("https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center&auto=format&q=80")
                        .stage(mandap)
                        .isActive(true)
                        .displayOrder(3)
                        .build()
        );
        
        imageRepository.saveAll(mandapImages);
        log.info("Created {} images for stage: {}", mandapImages.size(), mandap.getName());
    }
    
    private void createFoodStageImages(Stage foodStage) {
        List<Image> foodImages = Arrays.asList(
                Image.builder()
                        .name("Food Stage 1")
                        .description("Elegant buffet setup design with premium presentation")
                        .imageUrl("https://images.unsplash.com/photo-1511795409834-ef04bbd61622?w=800&h=600&fit=crop&crop=center&auto=format&q=80")
                        .stage(foodStage)
                        .isActive(true)
                        .displayOrder(1)
                        .build(),
                Image.builder()
                        .name("Food Stage 2")
                        .description("Live cooking station design with interactive setup")
                        .imageUrl("https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center&auto=format&q=80")
                        .stage(foodStage)
                        .isActive(true)
                        .displayOrder(2)
                        .build(),
                Image.builder()
                        .name("Food Stage 3")
                        .description("Traditional thali presentation design with authentic setup")
                        .imageUrl("https://images.unsplash.com/photo-1511795409834-ef04bbd61622?w=800&h=600&fit=crop&crop=center&auto=format&q=80")
                        .stage(foodStage)
                        .isActive(true)
                        .displayOrder(3)
                        .build()
        );
        
        imageRepository.saveAll(foodImages);
        log.info("Created {} images for stage: {}", foodImages.size(), foodStage.getName());
    }
    
    private void createOtherEvents() {
        List<Event> otherEvents = Arrays.asList(
                Event.builder()
                        .name("Haldi")
                        .description("Haldi ceremony event")
                        .isActive(true)
                        .build(),
                Event.builder()
                        .name("Mehandi")
                        .description("Mehandi ceremony event")
                        .isActive(true)
                        .build(),
                Event.builder()
                        .name("Sangeet")
                        .description("Sangeet ceremony event")
                        .isActive(true)
                        .build(),
                Event.builder()
                        .name("Cocktail Party")
                        .description("Cocktail party event")
                        .isActive(true)
                        .build()
        );
        
        List<Event> savedEvents = eventRepository.saveAll(otherEvents);
        log.info("Created {} other events", savedEvents.size());
        
        // Add stages and images for other events
        for (Event event : savedEvents) {
            createEventStages(event);
        }
    }
    
    private void createAdditionalWeddingStages(Event wedding) {
        // Create Reception Stage
        Stage reception = Stage.builder()
                .name("Reception Stage")
                .description("Wedding reception stage decoration")
                .event(wedding)
                .isActive(true)
                .build();
        reception = stageRepository.save(reception);
        log.info("Created stage: {} for event: {}", reception.getName(), wedding.getName());
        
        // Create Reception Images
        createReceptionImages(reception);
        
        // Create Photo Booth Stage
        Stage photoBooth = Stage.builder()
                .name("Photo Booth")
                .description("Wedding photo booth setup")
                .event(wedding)
                .isActive(true)
                .build();
        photoBooth = stageRepository.save(photoBooth);
        log.info("Created stage: {} for event: {}", photoBooth.getName(), wedding.getName());
        
        // Create Photo Booth Images
        createPhotoBoothImages(photoBooth);
        
        // Create Dance Floor Stage
        Stage danceFloor = Stage.builder()
                .name("Dance Floor")
                .description("Wedding dance floor decoration")
                .event(wedding)
                .isActive(true)
                .build();
        danceFloor = stageRepository.save(danceFloor);
        log.info("Created stage: {} for event: {}", danceFloor.getName(), wedding.getName());
        
        // Create Dance Floor Images
        createDanceFloorImages(danceFloor);
    }
    
    private void createReceptionImages(Stage reception) {
        List<Image> receptionImages = Arrays.asList(
                Image.builder()
                        .name("Reception Stage 1")
                        .description("Elegant reception stage with crystal chandeliers")
                        .imageUrl("https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center")
                        .stage(reception)
                        .isActive(true)
                        .displayOrder(1)
                        .build(),
                Image.builder()
                        .name("Reception Stage 2")
                        .description("Modern reception stage with LED backdrop")
                        .imageUrl("https://images.unsplash.com/photo-1511795409834-ef04bbd61622?w=800&h=600&fit=crop&crop=center")
                        .stage(reception)
                        .isActive(true)
                        .displayOrder(2)
                        .build(),
                Image.builder()
                        .name("Reception Stage 3")
                        .description("Traditional reception stage with floral arrangements")
                        .imageUrl("https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center&auto=format&q=80")
                        .stage(reception)
                        .isActive(true)
                        .displayOrder(3)
                        .build()
        );
        
        imageRepository.saveAll(receptionImages);
        log.info("Created {} images for stage: {}", receptionImages.size(), reception.getName());
    }
    
    private void createPhotoBoothImages(Stage photoBooth) {
        List<Image> photoBoothImages = Arrays.asList(
                Image.builder()
                        .name("Photo Booth 1")
                        .description("Vintage style photo booth with props")
                        .imageUrl("https://images.unsplash.com/photo-1511795409834-ef04bbd61622?w=800&h=600&fit=crop&crop=center")
                        .stage(photoBooth)
                        .isActive(true)
                        .displayOrder(1)
                        .build(),
                Image.builder()
                        .name("Photo Booth 2")
                        .description("Modern digital photo booth setup")
                        .imageUrl("https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center")
                        .stage(photoBooth)
                        .isActive(true)
                        .displayOrder(2)
                        .build(),
                Image.builder()
                        .name("Photo Booth 3")
                        .description("Outdoor photo booth with natural backdrop")
                        .imageUrl("https://images.unsplash.com/photo-1511795409834-ef04bbd61622?w=800&h=600&fit=crop&crop=center&auto=format&q=80")
                        .stage(photoBooth)
                        .isActive(true)
                        .displayOrder(3)
                        .build()
        );
        
        imageRepository.saveAll(photoBoothImages);
        log.info("Created {} images for stage: {}", photoBoothImages.size(), photoBooth.getName());
    }
    
    private void createDanceFloorImages(Stage danceFloor) {
        List<Image> danceFloorImages = Arrays.asList(
                Image.builder()
                        .name("Dance Floor 1")
                        .description("LED dance floor with colorful lighting")
                        .imageUrl("https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center")
                        .stage(danceFloor)
                        .isActive(true)
                        .displayOrder(1)
                        .build(),
                Image.builder()
                        .name("Dance Floor 2")
                        .description("Classic wooden dance floor with disco ball")
                        .imageUrl("https://images.unsplash.com/photo-1511795409834-ef04bbd61622?w=800&h=600&fit=crop&crop=center")
                        .stage(danceFloor)
                        .isActive(true)
                        .displayOrder(2)
                        .build(),
                Image.builder()
                        .name("Dance Floor 3")
                        .description("Outdoor dance floor with string lights")
                        .imageUrl("https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center&auto=format&q=80")
                        .stage(danceFloor)
                        .isActive(true)
                        .displayOrder(3)
                        .build()
        );
        
        imageRepository.saveAll(danceFloorImages);
        log.info("Created {} images for stage: {}", danceFloorImages.size(), danceFloor.getName());
    }
    
    private void createEventStages(Event event) {
        String eventName = event.getName().toLowerCase();
        
        switch (eventName) {
            case "haldi":
                createHaldiStages(event);
                break;
            case "mehandi":
                createMehandiStages(event);
                break;
            case "sangeet":
                createSangeetStages(event);
                break;
            case "cocktail party":
                createCocktailPartyStages(event);
                break;
        }
    }
    
    private void createHaldiStages(Event haldi) {
        // Haldi Ceremony Stage
        Stage haldiCeremony = Stage.builder()
                .name("Haldi Ceremony")
                .description("Haldi ceremony setup and decoration")
                .event(haldi)
                .isActive(true)
                .build();
        haldiCeremony = stageRepository.save(haldiCeremony);
        createHaldiImages(haldiCeremony);
        
        // Haldi Photo Area
        Stage haldiPhoto = Stage.builder()
                .name("Haldi Photo Area")
                .description("Photo area for haldi ceremony")
                .event(haldi)
                .isActive(true)
                .build();
        haldiPhoto = stageRepository.save(haldiPhoto);
        createHaldiPhotoImages(haldiPhoto);
    }
    
    private void createHaldiImages(Stage haldiCeremony) {
        List<Image> haldiImages = Arrays.asList(
                Image.builder()
                        .name("Haldi Setup 1")
                        .description("Traditional haldi ceremony setup with yellow decorations")
                        .imageUrl("https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center")
                        .stage(haldiCeremony)
                        .isActive(true)
                        .displayOrder(1)
                        .build(),
                Image.builder()
                        .name("Haldi Setup 2")
                        .description("Modern haldi ceremony with elegant yellow theme")
                        .imageUrl("https://images.unsplash.com/photo-1511795409834-ef04bbd61622?w=800&h=600&fit=crop&crop=center")
                        .stage(haldiCeremony)
                        .isActive(true)
                        .displayOrder(2)
                        .build()
        );
        imageRepository.saveAll(haldiImages);
    }
    
    private void createHaldiPhotoImages(Stage haldiPhoto) {
        List<Image> haldiPhotoImages = Arrays.asList(
                Image.builder()
                        .name("Haldi Photo 1")
                        .description("Yellow themed photo backdrop for haldi ceremony")
                        .imageUrl("https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center&auto=format&q=80")
                        .stage(haldiPhoto)
                        .isActive(true)
                        .displayOrder(1)
                        .build()
        );
        imageRepository.saveAll(haldiPhotoImages);
    }
    
    private void createMehandiStages(Event mehandi) {
        // Mehandi Ceremony Stage
        Stage mehandiCeremony = Stage.builder()
                .name("Mehandi Ceremony")
                .description("Mehandi ceremony setup and decoration")
                .event(mehandi)
                .isActive(true)
                .build();
        mehandiCeremony = stageRepository.save(mehandiCeremony);
        createMehandiImages(mehandiCeremony);
    }
    
    private void createMehandiImages(Stage mehandiCeremony) {
        List<Image> mehandiImages = Arrays.asList(
                Image.builder()
                        .name("Mehandi Setup 1")
                        .description("Traditional mehandi ceremony with intricate designs")
                        .imageUrl("https://images.unsplash.com/photo-1511795409834-ef04bbd61622?w=800&h=600&fit=crop&crop=center")
                        .stage(mehandiCeremony)
                        .isActive(true)
                        .displayOrder(1)
                        .build(),
                Image.builder()
                        .name("Mehandi Setup 2")
                        .description("Modern mehandi setup with elegant decorations")
                        .imageUrl("https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center&auto=format&q=80")
                        .stage(mehandiCeremony)
                        .isActive(true)
                        .displayOrder(2)
                        .build()
        );
        imageRepository.saveAll(mehandiImages);
    }
    
    private void createSangeetStages(Event sangeet) {
        // Sangeet Stage
        Stage sangeetStage = Stage.builder()
                .name("Sangeet Stage")
                .description("Sangeet ceremony stage and decoration")
                .event(sangeet)
                .isActive(true)
                .build();
        sangeetStage = stageRepository.save(sangeetStage);
        createSangeetImages(sangeetStage);
    }
    
    private void createSangeetImages(Stage sangeetStage) {
        List<Image> sangeetImages = Arrays.asList(
                Image.builder()
                        .name("Sangeet Stage 1")
                        .description("Vibrant sangeet stage with colorful decorations")
                        .imageUrl("https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center")
                        .stage(sangeetStage)
                        .isActive(true)
                        .displayOrder(1)
                        .build(),
                Image.builder()
                        .name("Sangeet Stage 2")
                        .description("Modern sangeet stage with LED lighting")
                        .imageUrl("https://images.unsplash.com/photo-1511795409834-ef04bbd61622?w=800&h=600&fit=crop&crop=center&auto=format&q=80")
                        .stage(sangeetStage)
                        .isActive(true)
                        .displayOrder(2)
                        .build()
        );
        imageRepository.saveAll(sangeetImages);
    }
    
    private void createCocktailPartyStages(Event cocktailParty) {
        // Cocktail Bar Stage
        Stage cocktailBar = Stage.builder()
                .name("Cocktail Bar")
                .description("Cocktail bar setup and decoration")
                .event(cocktailParty)
                .isActive(true)
                .build();
        cocktailBar = stageRepository.save(cocktailBar);
        createCocktailBarImages(cocktailBar);
    }
    
    private void createCocktailBarImages(Stage cocktailBar) {
        List<Image> cocktailImages = Arrays.asList(
                Image.builder()
                        .name("Cocktail Bar 1")
                        .description("Elegant cocktail bar with premium setup")
                        .imageUrl("https://images.unsplash.com/photo-1511795409834-ef04bbd61622?w=800&h=600&fit=crop&crop=center")
                        .stage(cocktailBar)
                        .isActive(true)
                        .displayOrder(1)
                        .build(),
                Image.builder()
                        .name("Cocktail Bar 2")
                        .description("Modern cocktail bar with LED lighting")
                        .imageUrl("https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center&auto=format&q=80")
                        .stage(cocktailBar)
                        .isActive(true)
                        .displayOrder(2)
                        .build()
        );
        imageRepository.saveAll(cocktailImages);
    }
}
