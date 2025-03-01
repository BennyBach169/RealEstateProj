package com.example.NLuxuryBack.Controllers;

import com.example.NLuxuryBack.beans.Property;
import com.example.NLuxuryBack.beans.PropertyImage;
import com.example.NLuxuryBack.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin("*")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;


    @GetMapping
    public List<Property> getAllProperties(){
        return propertyService.getAllProperties();
    }

    @GetMapping("/getone/{id}")
    public Property getOnProperty(@PathVariable int id) throws SQLException {
        return propertyService.getOneProperty(id);
    }

    @GetMapping("/getimages/{id}")
    public List<PropertyImage> getAllImages(@PathVariable int id){
        return propertyService.getAllPropertyImages(id);
    }



    @GetMapping("/primaryimage/{propertyId}")
    public PropertyImage getPrimaryPropertyImage(@PathVariable int propertyId) throws SQLException {
        return propertyService.getPrimaryImage(propertyId);
    }

}
