package com.example.NLuxuryBack.beans;

import jakarta.persistence.*;

@Entity
@Table(name = "property_types")
public class PropertyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String type;

    public PropertyType() {
    }

    public PropertyType(int id, String type) {
        this.id = id;
        this.type = type;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PropertyType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
