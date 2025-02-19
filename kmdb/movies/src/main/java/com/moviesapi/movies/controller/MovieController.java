package com.moviesapi.movies.controller;

import com.moviesapi.movies.Entity.Movie;
import com.moviesapi.movies.service.MovieActorUpdateDTO;
import com.moviesapi.movies.service.MovieDTO;
import com.moviesapi.movies.service.MovieService;

import jakarta.validation.Valid;

import com.moviesapi.movies.Entity.Actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;
    // GET all movies, also checks if you want to get movies by year, genre, actor if not then returns all movies
    @GetMapping
    public ResponseEntity<Page<Movie>> getMovies(@RequestParam(required = false) Integer year,
                                                @RequestParam(required = false) Long genre, 
                                                @RequestParam(required = false) Long actor,
                                                @RequestParam(defaultValue = "0") int page, 
                                                @RequestParam(defaultValue = "20") int size) {

        // Create Pageable instance for pagination
        Pageable pageable = PageRequest.of(page, size);

        // Get the filtered movies with pagination
        Page<Movie> movies = movieService.getMoviesByCriteria(year, genre, actor, pageable);

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> FindMoviesByTitle (@Valid @RequestParam(required = false) String title) {
        List<Movie> movies;
        if (title != null) {
            movies = movieService.getMoviebyTitle(title);
        } else {
            movies = movieService.getAllMovies();
        }
        return ResponseEntity.ok(movies);
    }

    // POST with @Valid annotation to create a movie and validate inputs
    @PostMapping
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody MovieDTO movieDTO) {
        Movie createdMovie = movieService.createMovie(movieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }
    // PATCH to update movie actors
    @PatchMapping("/{id}/actors")
    public ResponseEntity<Movie> updateMovieActors(@PathVariable Long id, @RequestBody MovieActorUpdateDTO updateDTO) {
        Movie updatedMovie = movieService.updateMovieActors(id, updateDTO);
        return ResponseEntity.ok(updatedMovie);
    }
    // GET movie by id
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        System.out.println("Fetching movie with ID: " + id); // Debug statement
        Optional<Movie> movie = movieService.getMovieById(id);
        return movie.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // GET actors from a specific movie
    @GetMapping("/{id}/actors")
    public ResponseEntity<Set<Actor>> getActorsByMovieId(@PathVariable Long id) {
        Set<Actor> actors = movieService.getActorsByMovieId(id);
        return actors != null ? ResponseEntity.ok(actors) : ResponseEntity.notFound().build();
    }
    // PATCH to update movie using @Valid to validate inputs
    @PatchMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@Valid @PathVariable Long id, @RequestBody Movie movie) {
        Movie updatedMovie = movieService.updateMovie(id, movie);
        return updatedMovie != null ? ResponseEntity.ok(updatedMovie) : ResponseEntity.notFound().build();
    }
    // DELETE movie
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
