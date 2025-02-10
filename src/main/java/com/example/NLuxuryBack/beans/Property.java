package com.example.NLuxuryBack.beans;

import jakarta.persistence.*;

@Entity
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private int bedrooms;
    private int rooms;
    private int bathrooms;
    private int sqm;
    private int builtSqm;
    @Enumerated(EnumType.STRING)
    private PropertyStatus propertyStatus;
    @ManyToOne
    @JoinColumn(name = "property_type_id") // Foreign key to PropertyType
    private PropertyType propertyType;

    public Property() {
    }


    public Property(int id, String title, String description, int bedrooms, int rooms, int bathrooms, int sqm, int builtSqm, PropertyStatus propertyStatus, PropertyType propertyType) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.bedrooms = bedrooms;
        this.rooms = rooms;
        this.bathrooms = bathrooms;
        this.sqm = sqm;
        this.builtSqm = builtSqm;
        this.propertyStatus = propertyStatus;
        this.propertyType = propertyType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public int getSqm() {
        return sqm;
    }

    public void setSqm(int sqm) {
        this.sqm = sqm;
    }

    public int getBuiltSqm() {
        return builtSqm;
    }

    public void setBuiltSqm(int builtSqm) {
        this.builtSqm = builtSqm;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PropertyStatus getPropertyStatus() {
        return propertyStatus;
    }

    public void setPropertyStatus(PropertyStatus propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", bedrooms=" + bedrooms +
                ", rooms=" + rooms +
                ", bathrooms=" + bathrooms +
                ", sqm=" + sqm +
                ", builtSqm=" + builtSqm +
                ", propertyType=" + propertyType +
                '}';
    }
}
