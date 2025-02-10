package com.example.NLuxuryBack.beans;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="property_images")
public class PropertyImages {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String image;
    private boolean isPrimary;
    @ManyToOne
    private Property property;


}
