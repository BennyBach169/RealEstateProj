package com.example.NLuxuryBack.beans;

import jakarta.persistence.*;

@Entity
@Table(name = "properties")
public class Property {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
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

    public Property(String title, int bedrooms, int rooms, int bathrooms, int sqm, int builtSqm, PropertyType propertyType) {
        this.title = title;
        this.bedrooms = bedrooms;
        this.rooms = rooms;
        this.bathrooms = bathrooms;
        this.sqm = sqm;
        this.builtSqm = builtSqm;
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
