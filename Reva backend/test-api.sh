#!/bin/bash

# Wedding Event Customization API Test Script
# Make sure the application is running on localhost:8080

BASE_URL="http://localhost:8080/api/v1"

echo "🎉 Testing Wedding Event Customization API"
echo "=========================================="

# Test 1: Get all events
echo -e "\n1. Getting all events..."
curl -s -X GET "$BASE_URL/events" | jq '.'

# Test 2: Get specific event (assuming Wedding is ID 1)
echo -e "\n2. Getting Wedding event details..."
curl -s -X GET "$BASE_URL/events/1" | jq '.'

# Test 3: Get stages for Wedding event
echo -e "\n3. Getting stages for Wedding event..."
curl -s -X GET "$BASE_URL/stages/event/1" | jq '.'

# Test 4: Get images for Entrance Gate stage (assuming ID 1)
echo -e "\n4. Getting images for Entrance Gate stage..."
curl -s -X GET "$BASE_URL/images/stage/1" | jq '.'

# Test 5: Search events
echo -e "\n5. Searching for 'Wedding' events..."
curl -s -X GET "$BASE_URL/events/search?name=Wedding" | jq '.'

# Test 6: Search stages
echo -e "\n6. Searching for 'Gate' stages in Wedding event..."
curl -s -X GET "$BASE_URL/stages/event/1/search?name=Gate" | jq '.'

# Test 7: Search images
echo -e "\n7. Searching for 'Gate' images in Entrance Gate stage..."
curl -s -X GET "$BASE_URL/images/stage/1/search?name=Gate" | jq '.'

# Test 8: Create a new event
echo -e "\n8. Creating a new event..."
curl -s -X POST "$BASE_URL/events" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Event",
    "description": "This is a test event for API testing"
  }' | jq '.'

# Test 9: Create a new stage for the test event
echo -e "\n9. Creating a new stage for Test Event..."
curl -s -X POST "$BASE_URL/stages" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Stage",
    "description": "This is a test stage",
    "eventId": 6
  }' | jq '.'

# Test 10: Create a new image for the test stage
echo -e "\n10. Creating a new image for Test Stage..."
curl -s -X POST "$BASE_URL/images" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Image",
    "description": "This is a test image",
    "imageUrl": "https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center",
    "stageId": 16
  }' | jq '.'

# Test 11: Generate PDF with selected images
echo -e "\n11. Generating PDF with selected wedding images..."
curl -s -X POST "$BASE_URL/pdf/generate" \
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
        "description": "Classic floral entrance gate design with roses and marigolds"
      },
      {
        "stageId": 2,
        "stageName": "Mandap",
        "imageId": 4,
        "imageName": "Mandap 1",
        "imageUrl": "https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center&auto=format&q=80",
        "description": "Traditional four-pillar mandap design with floral decorations"
      },
      {
        "stageId": 3,
        "stageName": "Food Stage",
        "imageId": 7,
        "imageName": "Food Stage 1",
        "imageUrl": "https://images.unsplash.com/photo-1511795409834-ef04bbd61622?w=800&h=600&fit=crop&crop=center&auto=format&q=80",
        "description": "Elegant buffet setup design with premium presentation"
      }
    ]
  }' --output "wedding_customization.pdf"

echo -e "\nPDF generated and saved as 'wedding_customization.pdf'"

# Test 12: Generate PDF as Base64
echo -e "\n12. Generating PDF as Base64..."
curl -s -X POST "$BASE_URL/pdf/generate-base64" \
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
  }' | jq '.data | length'

echo -e "\n✅ API testing completed!"
echo "Check the responses above to verify the API is working correctly."
echo "PDF file 'wedding_customization.pdf' has been generated in the current directory."
