package com.example.NLuxuryBack.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name="property_images")
public class PropertyImage {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String image;
    @JsonProperty("isPrimary")
    private boolean isPrimary;
    @ManyToOne
    private Property property;

    public PropertyImage(int id, String image, boolean isPrimary, Property property) {
        this.id = id;
        this.image = image;
        this.isPrimary = isPrimary;
        this.property = property;
    }

    public PropertyImage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "PropertyImages{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", isPrimary=" + isPrimary +
                ", property=" + property +
                '}';
    }
}
