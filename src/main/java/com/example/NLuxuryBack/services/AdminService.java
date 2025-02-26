package com.example.NLuxuryBack.services;

import com.example.NLuxuryBack.Repositories.PropertyImagesRepo;
import com.example.NLuxuryBack.Repositories.PropertyRepository;
import com.example.NLuxuryBack.Repositories.PropertyTypeRepo;
import com.example.NLuxuryBack.beans.Property;
import com.example.NLuxuryBack.beans.PropertyImage;
import com.example.NLuxuryBack.beans.PropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class AdminService {

    @Autowired
    private PropertyRepository propertyRepository; // Assuming the same repository for Property management
    @Autowired
    private PropertyTypeRepo propertyTypeRepo;
    @Autowired
    private PropertyImagesRepo propertyImagesRepo;
    @Autowired
    private S3Service s3Service;

    // Get all properties (for admin view)
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    // Get a single property by ID
    public Property getOneProperty(int id) throws SQLException {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new SQLException("Property not found by id: " + id));
    }

    // Update an existing property
    public Property updateProperty(Property property) throws SQLException {
        if (property.getId() == 0) {
            throw new SQLException("Property ID must be provided for update");
        }
        if (!propertyRepository.existsById(property.getId())) {
            throw new SQLException("Property not found with ID: " + property.getId());
        }
        return propertyRepository.save(property);
    }

    // Delete a property by ID
    public void deleteProperty(int id) throws SQLException {
        if (!propertyRepository.existsById(id)) {
            throw new SQLException("Property not found by id: " + id);
        }
        propertyRepository.deleteById(id);
    }

    // Add a new property
    public Property addProperty(Property property) {
        // Ensure the ID is not set (let the database auto-generate it)
        property.setId(0); // Or null, depending on your entity setup
        return propertyRepository.save(property);
    }

    public List<PropertyType> getALLPropertyTypes(){
        return propertyTypeRepo.findAll();
    }

    public void addNewPropertyType(PropertyType propertyType) throws SQLException {
        for(PropertyType p :propertyTypeRepo.findAll()){
            if(p.getType().equals(propertyType.getType())){
                throw new SQLException("Sorry this type already exist");
            }
        }
        propertyTypeRepo.save(propertyType);
    }

    public PropertyImage uploadPropertyImage(int propertyId, MultipartFile file) throws IOException, SQLException {
        // Validate property exists
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new SQLException("Property not found by id: " + propertyId));

        // Convert MultipartFile to temporary File for S3 upload
        File tempFile = convertMultipartFileToFile(file);
        try {
            // Generate a unique key for the S3 object (e.g., "properties/{propertyId}/images/{uuid}.jpg")
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String keyName = "properties/" + propertyId + "/images/" + fileName;

            // Upload to S3 and get the URL
            String imageUrl = s3Service.uploadFile(tempFile.toPath(), keyName);

            // Create or update PropertyImage entity
            PropertyImage propertyImage = new PropertyImage();
            propertyImage.setProperty(property);
            propertyImage.setImage(imageUrl);
            propertyImage.setIsPrimary(false);

            // Save to database
            return propertyImagesRepo.save(propertyImage);
        } finally {
            // Clean up temporary file
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    // Helper method to convert MultipartFile to File
    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("upload-", file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(file.getBytes());
        }
        return tempFile;
    }

    public void deletePropertyImage(int imageId) throws SQLException {
        PropertyImage propertyImage = propertyImagesRepo.findById(imageId)
                .orElseThrow(() -> new SQLException("Property image not found by id: " + imageId));

        // Delete the file from S3 using the key extracted from the image URL
        String imageUrl = propertyImage.getImage();
        if (imageUrl != null) {
            String s3Key = extractS3KeyFromUrl(imageUrl);
            if (s3Key != null) {
                try {
                    s3Service.deleteFile(s3Key);
                } catch (Exception e) {
                    System.err.println("Failed to delete image from S3: " + e.getMessage());
                    // Optionally rethrow or log more details, but continue with DB deletion
                }
            }
        }

        // Delete the PropertyImage from the database
        propertyImagesRepo.deleteById(imageId);
    }

    // Helper method to extract S3 key from URL (already defined, just referenced)
    private String extractS3KeyFromUrl(String imageUrl) {
        if (imageUrl == null || !imageUrl.contains("/properties/")) return null;
        String[] parts = imageUrl.split("/properties/");
        if (parts.length > 1) {
            return "properties/" + parts[1];
        }
        return null;
    }

    public void setPrimaryImage(PropertyImage propertyImage) throws SQLException {
        if (propertyImage == null || propertyImage.getProperty().getId() == 0) {
            throw new SQLException("Invalid PropertyImage or propertyId provided");
        }

        int propertyId = propertyImage.getProperty().getId();

        PropertyImage currentPrimaryImage = propertyImagesRepo.findByPropertyIdAndIsPrimaryTrue(propertyId);
        if (currentPrimaryImage != null) {
            if (currentPrimaryImage.getId()!=propertyImage.getId()) {
                currentPrimaryImage.setIsPrimary(false);
                propertyImagesRepo.save(currentPrimaryImage);
            }
        }

        propertyImage.setIsPrimary(true);
        propertyImagesRepo.save(propertyImage);
    }


}