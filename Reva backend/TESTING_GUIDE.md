# 🧪 API Testing Guide

This guide will help you test the Wedding Event Customization API with real dummy images.

## 🚀 Quick Start

### 1. Start the Application
```bash
mvn spring-boot:run
```

### 2. Access the API
- **Base URL**: `http://localhost:8080/api/v1`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`

## 📊 Sample Data Available

The application automatically initializes with the following data:

### Events
- **Wedding** (ID: 1) - Main wedding ceremony
- **Haldi** (ID: 2) - Haldi ceremony
- **Mehandi** (ID: 3) - Mehandi ceremony  
- **Sangeet** (ID: 4) - Sangeet ceremony
- **Cocktail Party** (ID: 5) - Cocktail party

### Wedding Event Stages
- **Entrance Gate** (ID: 1) - 3 image options
- **Mandap** (ID: 2) - 3 image options
- **Food Stage** (ID: 3) - 3 image options
- **Reception Stage** (ID: 4) - 3 image options
- **Photo Booth** (ID: 5) - 3 image options
- **Dance Floor** (ID: 6) - 3 image options

### Other Event Stages
- **Haldi Ceremony** + **Haldi Photo Area**
- **Mehandi Ceremony**
- **Sangeet Stage**
- **Cocktail Bar**

## 🧪 Testing Methods

### Method 1: Using Test Scripts

#### Windows:
```bash
test-api.bat
```

#### Linux/Mac:
```bash
./test-api.sh
```

### Method 2: Using Swagger UI
1. Open `http://localhost:8080/swagger-ui.html`
2. Click on any endpoint
3. Click "Try it out"
4. Fill in the parameters
5. Click "Execute"

### Method 3: Using curl Commands

#### Get All Events
```bash
curl -X GET "http://localhost:8080/api/v1/events"
```

#### Get Wedding Event Details
```bash
curl -X GET "http://localhost:8080/api/v1/events/1"
```

#### Get Stages for Wedding Event
```bash
curl -X GET "http://localhost:8080/api/v1/stages/event/1"
```

#### Get Images for Entrance Gate Stage
```bash
curl -X GET "http://localhost:8080/api/v1/images/stage/1"
```

#### Search Events
```bash
curl -X GET "http://localhost:8080/api/v1/events/search?name=Wedding"
```

#### Search Stages
```bash
curl -X GET "http://localhost:8080/api/v1/stages/event/1/search?name=Gate"
```

#### Search Images
```bash
curl -X GET "http://localhost:8080/api/v1/images/stage/1/search?name=Gate"
```

#### Generate PDF (Download)
```bash
curl -X POST "http://localhost:8080/api/v1/pdf/generate" \
  -H "Content-Type: application/json" \
  -d '{
    "eventId": 1,
    "eventName": "Wedding",
    "selectedImages": [
      {
        "stageId": 1,
        "stageName": "Entrance Gate",
        "imageId": 1,
        "imageName": "Entrance Gate 1",
        "imageUrl": "https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center",
        "description": "Classic floral entrance gate design"
      }
    ]
  }' --output "wedding_customization.pdf"
```

#### Generate PDF as Base64
```bash
curl -X POST "http://localhost:8080/api/v1/pdf/generate-base64" \
  -H "Content-Type: application/json" \
  -d '{
    "eventId": 1,
    "eventName": "Wedding",
    "selectedImages": [
      {
        "stageId": 1,
        "stageName": "Entrance Gate",
        "imageId": 1,
        "imageName": "Entrance Gate 1",
        "imageUrl": "https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center",
        "description": "Classic floral entrance gate design"
      }
    ]
  }'
```

### Method 4: Using Postman

1. Import the following collection:

```json
{
  "info": {
    "name": "Wedding Event API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Get All Events",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "{{baseUrl}}/events",
          "host": ["{{baseUrl}}"],
          "path": ["events"]
        }
      }
    },
    {
      "name": "Get Event by ID",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "{{baseUrl}}/events/1",
          "host": ["{{baseUrl}}"],
          "path": ["events", "1"]
        }
      }
    },
    {
      "name": "Get Stages by Event",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "{{baseUrl}}/stages/event/1",
          "host": ["{{baseUrl}}"],
          "path": ["stages", "event", "1"]
        }
      }
    },
    {
      "name": "Get Images by Stage",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "{{baseUrl}}/images/stage/1",
          "host": ["{{baseUrl}}"],
          "path": ["images", "stage", "1"]
        }
      }
    }
  ],
  "variable": [
    {
      "key": "baseUrl",
      "value": "http://localhost:8080/api/v1"
    }
  ]
}
```

## 🖼️ Image URLs Used

All images are sourced from Unsplash with proper sizing:
- **Format**: 800x600 pixels
- **Quality**: Optimized for web
- **Content**: Wedding and event-related imagery

### Sample Image URLs:
- `https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center`
- `https://images.unsplash.com/photo-1511795409834-ef04bbd61622?w=800&h=600&fit=crop&crop=center`

## 🔍 Testing Scenarios

### 1. Basic Flow Testing
1. Get all events
2. Select Wedding event
3. Get Wedding stages
4. Select Entrance Gate stage
5. Get Entrance Gate images
6. Select an image

### 2. Search Testing
1. Search for events containing "Wedding"
2. Search for stages containing "Gate"
3. Search for images containing "Gate"

### 3. CRUD Testing
1. Create a new event
2. Create a new stage for that event
3. Create a new image for that stage
4. Update the created resources
5. Delete the created resources

### 4. Error Handling Testing
1. Try to get non-existent event (404)
2. Try to create event with duplicate name (409)
3. Try to create image with invalid URL (400)
4. Try to create stage without event ID (400)

## 📝 Expected Responses

### Successful Response Format:
```json
{
  "success": true,
  "message": "Operation completed successfully",
  "data": { ... },
  "timestamp": "2024-01-01T12:00:00"
}
```

### Error Response Format:
```json
{
  "success": false,
  "message": "Error description",
  "errorCode": "ERROR_CODE",
  "timestamp": "2024-01-01T12:00:00"
}
```

## 🐛 Troubleshooting

### Common Issues:

1. **Application won't start**
   - Check if MySQL is running
   - Verify database credentials
   - Check if port 8080 is available

2. **Database connection error**
   - Create the database: `CREATE DATABASE wedding_events;`
   - Check MySQL service is running
   - Verify username/password

3. **Images not loading**
   - Check internet connection
   - Verify image URLs are accessible
   - Check if Unsplash is blocked

4. **API returns 404**
   - Verify the application is running
   - Check the correct endpoint URL
   - Ensure the resource exists

## 📊 Performance Testing

### Load Testing with Apache Bench:
```bash
# Test events endpoint
ab -n 100 -c 10 http://localhost:8080/api/v1/events

# Test stages endpoint
ab -n 100 -c 10 http://localhost:8080/api/v1/stages/event/1

# Test images endpoint
ab -n 100 -c 10 http://localhost:8080/api/v1/images/stage/1
```

## 🎯 Success Criteria

✅ **API Testing is successful when:**
- All GET endpoints return 200 with data
- All POST endpoints return 201 with created data
- All PUT endpoints return 200 with updated data
- All DELETE endpoints return 200 with success message
- Search endpoints return filtered results
- Error handling returns proper error codes
- Images load correctly in browser
- Swagger UI is accessible and functional

## 📞 Support

If you encounter any issues:
1. Check the application logs in `logs/` directory
2. Verify database connectivity
3. Check network connectivity for image loading
4. Review the error messages in API responses
