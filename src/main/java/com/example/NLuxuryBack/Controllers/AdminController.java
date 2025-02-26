package com.example.NLuxuryBack.Controllers;

import com.example.NLuxuryBack.beans.Property;
import com.example.NLuxuryBack.beans.PropertyImage;
import com.example.NLuxuryBack.beans.PropertyType;
import com.example.NLuxuryBack.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/properties")
    public List<Property> getAllProperties() {
        return adminService.getAllProperties();
    }

    @GetMapping("/properties/{id}")
    public Property getOneProperty(@PathVariable int id) throws SQLException {
        return adminService.getOneProperty(id);
    }

    @PutMapping("/properties")
    public Property updateProperty(@RequestBody Property property) throws SQLException {
        return adminService.updateProperty(property);
    }

    @DeleteMapping("/properties/{id}")
    public void deleteProperty(@PathVariable int id) throws SQLException {
        adminService.deleteProperty(id);
    }

    @PostMapping("/properties")
    public Property addProperty(@RequestBody Property property) {
        return adminService.addProperty(property);
    }

    @GetMapping("/getalltypes")
    public List<PropertyType> getAllTypes(){
        return adminService.getALLPropertyTypes();
    }

    @PostMapping("/properties/primaryimage/{propertyId}")
    public PropertyImage uploadPropertyImage(
            @PathVariable int propertyId,
            @RequestParam("file") MultipartFile file) throws IOException, SQLException {
        return adminService.uploadPropertyImage(propertyId, file);
    }
}