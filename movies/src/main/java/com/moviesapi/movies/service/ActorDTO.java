// Used for validating actors DTO = Data transfer objects, these basically help me transfer data and validate it

package com.moviesapi.movies.service;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public class ActorDTO {
    
    @NotNull(message = "Name is required.")
    private String name;

    @NotNull(message = "Birth date is required.")
    @Past(message = "Birth date must be a date in the past.")
    private LocalDate birthDate;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
