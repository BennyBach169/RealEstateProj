package com.example.NLuxuryBack.Repositories;

import com.example.NLuxuryBack.beans.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyImagesRepo extends JpaRepository<PropertyImage,Integer> {
    List<PropertyImage> findByPropertyId(int propertyId);
    PropertyImage findByPropertyIdAndIsPrimaryTrue(int propertyId);

}
