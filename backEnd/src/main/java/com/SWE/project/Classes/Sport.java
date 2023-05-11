package com.SWE.project.Classes;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Sport {
    @Id
    private String name;

    public Sport(String name) {
        this.name = name;
    }

    public Sport() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // public Long getId() {
    // return this.id;
    // }

    // public void setId(Long id) {
    // this.id = id;
    // }

    @Override
    public String toString() {
        return getName();
    }
}