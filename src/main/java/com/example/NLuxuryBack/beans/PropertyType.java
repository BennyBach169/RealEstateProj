package com.example.NLuxuryBack.beans;

import jakarta.persistence.*;

@Entity
@Table(name = "property_types")
public class PropertyType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String type;
}
