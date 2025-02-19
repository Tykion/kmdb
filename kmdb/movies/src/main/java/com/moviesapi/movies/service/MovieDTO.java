// Helps with validation and transfering data

package com.moviesapi.movies.service;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class MovieDTO {

    // Some bean validations
    @NotNull(message = "Title cannot be empty")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    private String title;

    @Min(value = 1888, message = "Release year must be no earlier than 1888")
    @Max(value = 2024, message = "Release year must be no later than 2024")
    private int releaseYear;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int duration;

    @JsonProperty("actorIds")
    private Set<@NotNull(message = "Actor IDs cannot be null") Long> actors; // Set of actor IDs to avoid duplicates

    @JsonProperty("genreIds")
    private Set<@NotNull(message = "Genre IDs cannot be null") Long> genres; // Set of genre IDs to avoid duplicates

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public Set<Long> getActors() { return actors; }
    public void setActors(Set<Long> actors) { this.actors = actors; }

    public Set<Long> getGenres() { return genres; }
    public void setGenres(Set<Long> genres) { this.genres = genres; }
}
