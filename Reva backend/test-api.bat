@echo off
echo 🎉 Testing Wedding Event Customization API
echo ==========================================

set BASE_URL=http://localhost:8080/api/v1

echo.
echo 1. Getting all events...
curl -s -X GET "%BASE_URL%/events"

echo.
echo 2. Getting Wedding event details...
curl -s -X GET "%BASE_URL%/events/1"

echo.
echo 3. Getting stages for Wedding event...
curl -s -X GET "%BASE_URL%/stages/event/1"

echo.
echo 4. Getting images for Entrance Gate stage...
curl -s -X GET "%BASE_URL%/images/stage/1"

echo.
echo 5. Searching for 'Wedding' events...
curl -s -X GET "%BASE_URL%/events/search?name=Wedding"

echo.
echo 6. Searching for 'Gate' stages in Wedding event...
curl -s -X GET "%BASE_URL%/stages/event/1/search?name=Gate"

echo.
echo 7. Searching for 'Gate' images in Entrance Gate stage...
curl -s -X GET "%BASE_URL%/images/stage/1/search?name=Gate"

echo.
echo 8. Creating a new event...
curl -s -X POST "%BASE_URL%/events" -H "Content-Type: application/json" -d "{\"name\":\"Test Event\",\"description\":\"This is a test event for API testing\"}"

echo.
echo 9. Creating a new stage for Test Event...
curl -s -X POST "%BASE_URL%/stages" -H "Content-Type: application/json" -d "{\"name\":\"Test Stage\",\"description\":\"This is a test stage\",\"eventId\":6}"

echo.
echo 10. Creating a new image for Test Stage...
curl -s -X POST "%BASE_URL%/images" -H "Content-Type: application/json" -d "{\"name\":\"Test Image\",\"description\":\"This is a test image\",\"imageUrl\":\"https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center\",\"stageId\":16}"

echo.
echo 11. Generating PDF with selected wedding images...
curl -s -X POST "%BASE_URL%/pdf/generate" -H "Content-Type: application/json" -d "{\"eventId\":1,\"eventName\":\"Wedding\",\"selectedImages\":[{\"stageId\":1,\"stageName\":\"Entrance Gate\",\"imageId\":1,\"imageName\":\"Entrance Gate 1\",\"imageUrl\":\"https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center\",\"description\":\"Classic floral entrance gate design\"},{\"stageId\":2,\"stageName\":\"Mandap\",\"imageId\":4,\"imageName\":\"Mandap 1\",\"imageUrl\":\"https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center&auto=format&q=80\",\"description\":\"Traditional four-pillar mandap design\"},{\"stageId\":3,\"stageName\":\"Food Stage\",\"imageId\":7,\"imageName\":\"Food Stage 1\",\"imageUrl\":\"https://images.unsplash.com/photo-1511795409834-ef04bbd61622?w=800&h=600&fit=crop&crop=center&auto=format&q=80\",\"description\":\"Elegant buffet setup design\"}]}" --output "wedding_customization.pdf"

echo.
echo PDF generated and saved as 'wedding_customization.pdf'

echo.
echo 12. Generating PDF as Base64...
curl -s -X POST "%BASE_URL%/pdf/generate-base64" -H "Content-Type: application/json" -d "{\"eventId\":1,\"eventName\":\"Wedding\",\"selectedImages\":[{\"stageId\":1,\"stageName\":\"Entrance Gate\",\"imageId\":1,\"imageName\":\"Entrance Gate 1\",\"imageUrl\":\"https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=800&h=600&fit=crop&crop=center\",\"description\":\"Classic floral entrance gate design\"}]}"

echo.
echo ✅ API testing completed!
echo Check the responses above to verify the API is working correctly.
echo PDF file 'wedding_customization.pdf' has been generated in the current directory.
pause
