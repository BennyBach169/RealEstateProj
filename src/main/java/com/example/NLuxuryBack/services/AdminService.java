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
    private PropertyRepository propertyRepository;
    @Autowired
    private PropertyTypeRepo propertyTypeRepo;
    @Autowired
    private PropertyImagesRepo propertyImagesRepo;
    @Autowired
    private S3Service s3Service;


    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }


    public Property getOneProperty(int id) throws SQLException {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new SQLException("Property not found by id: " + id));
    }

    public Property updateProperty(Property property) throws SQLException {
        if (property.getId() == 0) {
            throw new SQLException("Property ID must be provided for update");
        }
        if (!propertyRepository.existsById(property.getId())) {
            throw new SQLException("Property not found with ID: " + property.getId());
        }
        return propertyRepository.save(property);
    }





    public Property addProperty(Property property) {

        property.setId(0);
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

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new SQLException("Property not found by id: " + propertyId));


        File tempFile = convertMultipartFileToFile(file);
        try {

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String keyName = "properties/" + propertyId + "/images/" + fileName;


            String imageUrl = s3Service.uploadFile(tempFile.toPath(), keyName);


            PropertyImage propertyImage = new PropertyImage();
            propertyImage.setProperty(property);
            propertyImage.setImage(imageUrl);
            propertyImage.setIsPrimary(false);


            return propertyImagesRepo.save(propertyImage);
        } finally {

            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }


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


        String imageUrl = propertyImage.getImage();
        if (imageUrl != null) {
            String s3Key = extractS3KeyFromUrl(imageUrl);
            if (s3Key != null) {
                try {
                    s3Service.deleteFile(s3Key);
                } catch (Exception e) {
                    System.err.println("Failed to delete image from S3: " + e.getMessage());

                }
            }
        }


        propertyImagesRepo.deleteById(imageId);
    }


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

    public void deletePropertyWithImages(int propertyId) throws SQLException {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new SQLException("Property not found by id: " + propertyId));


        List<PropertyImage> images = propertyImagesRepo.findByPropertyId(propertyId);


        for (PropertyImage image : images) {
            String imageUrl = image.getImage();
            if (imageUrl != null) {
                String s3Key = extractS3KeyFromUrl(imageUrl);
                if (s3Key != null) {
                    try {
                        s3Service.deleteFile(s3Key);
                    } catch (Exception e) {
                        System.err.println("Failed to delete image from S3: " + e.getMessage());

                    }
                }
            }
            propertyImagesRepo.deleteById(image.getId());
        }


        propertyRepository.deleteById(propertyId);
    }


}