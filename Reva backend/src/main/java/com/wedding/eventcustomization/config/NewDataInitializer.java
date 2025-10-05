package com.wedding.eventcustomization.config;

import com.wedding.eventcustomization.entity.DecorationImage;
import com.wedding.eventcustomization.entity.DecorationType;
import com.wedding.eventcustomization.entity.Event;
import com.wedding.eventcustomization.repository.DecorationImageRepository;
import com.wedding.eventcustomization.repository.DecorationTypeRepository;
import com.wedding.eventcustomization.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewDataInitializer implements CommandLineRunner {
    
    private final EventRepository eventRepository;
    private final DecorationTypeRepository decorationTypeRepository;
    private final DecorationImageRepository decorationImageRepository;
    
    @Override
    public void run(String... args) throws Exception {
        log.info("Starting new data initialization...");
        
        // Check if data already exists
        if (eventRepository.count() > 0) {
            log.info("Data already exists, skipping initialization");
            return;
        }
        
        initializeEvents();
        log.info("New data initialization completed successfully!");
    }
    
    private void initializeEvents() {
        log.info("Initializing events data...");
        
        // Create Wedding Event
        Event weddingEvent = Event.builder()
                .name("Wedding")
                .description("Traditional wedding ceremony and reception")
                .isActive(true)
                .build();
        weddingEvent = eventRepository.save(weddingEvent);
        log.info("Created event: {}", weddingEvent.getName());
        
        createWeddingDecorationTypes(weddingEvent);
        
        // Create Haldi Event
        Event haldiEvent = Event.builder()
                .name("Haldi")
                .description("Pre-wedding haldi ceremony")
                .isActive(true)
                .build();
        haldiEvent = eventRepository.save(haldiEvent);
        log.info("Created event: {}", haldiEvent.getName());
        
        createHaldiDecorationTypes(haldiEvent);
        
        // Create Mehandi Event
        Event mehandiEvent = Event.builder()
                .name("Mehandi")
                .description("Pre-wedding mehandi ceremony")
                .isActive(true)
                .build();
        mehandiEvent = eventRepository.save(mehandiEvent);
        log.info("Created event: {}", mehandiEvent.getName());
        
        createMehandiDecorationTypes(mehandiEvent);
        
        // Create Sangeet Event
        Event sangeetEvent = Event.builder()
                .name("Sangeet")
                .description("Pre-wedding sangeet ceremony")
                .isActive(true)
                .build();
        sangeetEvent = eventRepository.save(sangeetEvent);
        log.info("Created event: {}", sangeetEvent.getName());
        
        createSangeetDecorationTypes(sangeetEvent);
        
        // Create Cocktail Party Event
        Event cocktailEvent = Event.builder()
                .name("Cocktail Party")
                .description("Pre-wedding cocktail party")
                .isActive(true)
                .build();
        cocktailEvent = eventRepository.save(cocktailEvent);
        log.info("Created event: {}", cocktailEvent.getName());
        
        createCocktailDecorationTypes(cocktailEvent);
    }
    
    private void createWeddingDecorationTypes(Event weddingEvent) {
        // Mandap Decoration
        DecorationType mandapDecoration = DecorationType.builder()
                .name("Mandap Decoration")
                .description("Beautiful mandap decoration for wedding ceremony")
                .isActive(true)
                .displayOrder(1)
                .event(weddingEvent)
                .build();
        mandapDecoration = decorationTypeRepository.save(mandapDecoration);
        log.info("Created decoration type: {} for event: {}", mandapDecoration.getName(), weddingEvent.getName());
        
        createMandapImages(mandapDecoration);
        
        // Entrance Gate Decoration
        DecorationType entranceDecoration = DecorationType.builder()
                .name("Entrance Gate Decoration")
                .description("Grand entrance gate decoration")
                .isActive(true)
                .displayOrder(2)
                .event(weddingEvent)
                .build();
        entranceDecoration = decorationTypeRepository.save(entranceDecoration);
        log.info("Created decoration type: {} for event: {}", entranceDecoration.getName(), weddingEvent.getName());
        
        createEntranceImages(entranceDecoration);
        
        // Food Stage Decoration
        DecorationType foodStageDecoration = DecorationType.builder()
                .name("Food Stage Decoration")
                .description("Elegant food stage decoration")
                .isActive(true)
                .displayOrder(3)
                .event(weddingEvent)
                .build();
        foodStageDecoration = decorationTypeRepository.save(foodStageDecoration);
        log.info("Created decoration type: {} for event: {}", foodStageDecoration.getName(), weddingEvent.getName());
        
        createFoodStageImages(foodStageDecoration);
    }
    
    private void createHaldiDecorationTypes(Event haldiEvent) {
        DecorationType haldiDecoration = DecorationType.builder()
                .name("Haldi Stage Decoration")
                .description("Colorful haldi ceremony decoration")
                .isActive(true)
                .displayOrder(1)
                .event(haldiEvent)
                .build();
        haldiDecoration = decorationTypeRepository.save(haldiDecoration);
        log.info("Created decoration type: {} for event: {}", haldiDecoration.getName(), haldiEvent.getName());
        
        createHaldiImages(haldiDecoration);
    }
    
    private void createMehandiDecorationTypes(Event mehandiEvent) {
        DecorationType mehandiDecoration = DecorationType.builder()
                .name("Mehandi Stage Decoration")
                .description("Traditional mehandi ceremony decoration")
                .isActive(true)
                .displayOrder(1)
                .event(mehandiEvent)
                .build();
        mehandiDecoration = decorationTypeRepository.save(mehandiDecoration);
        log.info("Created decoration type: {} for event: {}", mehandiDecoration.getName(), mehandiEvent.getName());
        
        createMehandiImages(mehandiDecoration);
    }
    
    private void createSangeetDecorationTypes(Event sangeetEvent) {
        DecorationType sangeetDecoration = DecorationType.builder()
                .name("Sangeet Stage Decoration")
                .description("Vibrant sangeet ceremony decoration")
                .isActive(true)
                .displayOrder(1)
                .event(sangeetEvent)
                .build();
        sangeetDecoration = decorationTypeRepository.save(sangeetDecoration);
        log.info("Created decoration type: {} for event: {}", sangeetDecoration.getName(), sangeetEvent.getName());
        
        createSangeetImages(sangeetDecoration);
    }
    
    private void createCocktailDecorationTypes(Event cocktailEvent) {
        DecorationType cocktailDecoration = DecorationType.builder()
                .name("Cocktail Party Decoration")
                .description("Modern cocktail party decoration")
                .isActive(true)
                .displayOrder(1)
                .event(cocktailEvent)
                .build();
        cocktailDecoration = decorationTypeRepository.save(cocktailDecoration);
        log.info("Created decoration type: {} for event: {}", cocktailDecoration.getName(), cocktailEvent.getName());
        
        createCocktailImages(cocktailDecoration);
    }
    
    private void createMandapImages(DecorationType mandapDecoration) {
        List<DecorationImage> mandapImages = List.of(
                DecorationImage.builder()
                        .name("Traditional Mandap")
                        .imageUrl("https://images.unsplash.com/photo-1519741497674-611481863552?w=800")
                        .description("Beautiful traditional mandap with flowers")
                        .isActive(true)
                        .displayOrder(1)
                        .decorationType(mandapDecoration)
                        .build(),
                DecorationImage.builder()
                        .name("Modern Mandap")
                        .imageUrl("https://images.unsplash.com/photo-1606800052052-a08af7148866?w=800")
                        .description("Contemporary mandap design")
                        .isActive(true)
                        .displayOrder(2)
                        .decorationType(mandapDecoration)
                        .build(),
                DecorationImage.builder()
                        .name("Floral Mandap")
                        .imageUrl("https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800")
                        .description("Mandap decorated with fresh flowers")
                        .isActive(true)
                        .displayOrder(3)
                        .decorationType(mandapDecoration)
                        .build()
        );
        
        decorationImageRepository.saveAll(mandapImages);
        log.info("Created {} mandap images", mandapImages.size());
    }
    
    private void createEntranceImages(DecorationType entranceDecoration) {
        List<DecorationImage> entranceImages = List.of(
                DecorationImage.builder()
                        .name("Grand Entrance")
                        .imageUrl("https://images.unsplash.com/photo-1519741497674-611481863552?w=800")
                        .description("Magnificent entrance gate decoration")
                        .isActive(true)
                        .displayOrder(1)
                        .decorationType(entranceDecoration)
                        .build(),
                DecorationImage.builder()
                        .name("Flower Arch")
                        .imageUrl("https://images.unsplash.com/photo-1606800052052-a08af7148866?w=800")
                        .description("Beautiful flower arch entrance")
                        .isActive(true)
                        .displayOrder(2)
                        .decorationType(entranceDecoration)
                        .build()
        );
        
        decorationImageRepository.saveAll(entranceImages);
        log.info("Created {} entrance images", entranceImages.size());
    }
    
    private void createFoodStageImages(DecorationType foodStageDecoration) {
        List<DecorationImage> foodStageImages = List.of(
                DecorationImage.builder()
                        .name("Elegant Food Station")
                        .imageUrl("https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800")
                        .description("Beautifully decorated food station")
                        .isActive(true)
                        .displayOrder(1)
                        .decorationType(foodStageDecoration)
                        .build()
        );
        
        decorationImageRepository.saveAll(foodStageImages);
        log.info("Created {} food stage images", foodStageImages.size());
    }
    
    private void createHaldiImages(DecorationType haldiDecoration) {
        List<DecorationImage> haldiImages = List.of(
                DecorationImage.builder()
                        .name("Colorful Haldi Setup")
                        .imageUrl("https://images.unsplash.com/photo-1519741497674-611481863552?w=800")
                        .description("Vibrant haldi ceremony decoration")
                        .isActive(true)
                        .displayOrder(1)
                        .decorationType(haldiDecoration)
                        .build()
        );
        
        decorationImageRepository.saveAll(haldiImages);
        log.info("Created {} haldi images", haldiImages.size());
    }
    
    private void createMehandiImages(DecorationType mehandiDecoration) {
        List<DecorationImage> mehandiImages = List.of(
                DecorationImage.builder()
                        .name("Traditional Mehandi Setup")
                        .imageUrl("https://images.unsplash.com/photo-1606800052052-a08af7148866?w=800")
                        .description("Traditional mehandi ceremony decoration")
                        .isActive(true)
                        .displayOrder(1)
                        .decorationType(mehandiDecoration)
                        .build()
        );
        
        decorationImageRepository.saveAll(mehandiImages);
        log.info("Created {} mehandi images", mehandiImages.size());
    }
    
    private void createSangeetImages(DecorationType sangeetDecoration) {
        List<DecorationImage> sangeetImages = List.of(
                DecorationImage.builder()
                        .name("Vibrant Sangeet Stage")
                        .imageUrl("https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800")
                        .description("Colorful sangeet ceremony decoration")
                        .isActive(true)
                        .displayOrder(1)
                        .decorationType(sangeetDecoration)
                        .build()
        );
        
        decorationImageRepository.saveAll(sangeetImages);
        log.info("Created {} sangeet images", sangeetImages.size());
    }
    
    private void createCocktailImages(DecorationType cocktailDecoration) {
        List<DecorationImage> cocktailImages = List.of(
                DecorationImage.builder()
                        .name("Modern Cocktail Setup")
                        .imageUrl("https://images.unsplash.com/photo-1519741497674-611481863552?w=800")
                        .description("Elegant cocktail party decoration")
                        .isActive(true)
                        .displayOrder(1)
                        .decorationType(cocktailDecoration)
                        .build()
        );
        
        decorationImageRepository.saveAll(cocktailImages);
        log.info("Created {} cocktail images", cocktailImages.size());
    }
}
