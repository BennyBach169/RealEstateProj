package com.example.NLuxuryBack.services;


import com.example.NLuxuryBack.Repositories.PropertyRepository;
import com.example.NLuxuryBack.Repositories.PropertyTypeRepo;
import com.example.NLuxuryBack.beans.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private PropertyTypeRepo propertyTypeRepo;


    public List<Property> getAllProperties(){
        return propertyRepository.findAll();
    }

    public Property getOneProperty(int id) throws SQLException {
        return propertyRepository.findById(id).orElseThrow(()->new SQLException("Property not found by id :" + id));
    }
}
