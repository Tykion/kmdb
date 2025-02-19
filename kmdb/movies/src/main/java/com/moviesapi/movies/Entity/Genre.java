package com.moviesapi.movies.Entity;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Genre {
    // Automatically generated ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    //Many-to-Many relationship with Movie (inverse side)
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Movie> movies = new HashSet<>();
    
    // Default constructor
    public Genre() {}

    // Constructor
    public Genre(String name) {
        this.name = name;
    }
  
    // Getters and setters
    public Long getId() {
        return id;
    }
  
    public void setId(Long id) {
        this.id = id;
    }
  
    public String getName() {
        return name;
    }
  
    public void setName(String name) {
        this.name = name;
    }
  
    public Set<Movie> getMovies() {
        return movies;
    }
  
    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}