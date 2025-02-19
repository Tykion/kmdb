package com.moviesapi.movies.controller;

import com.moviesapi.movies.Entity.Genre;
import com.moviesapi.movies.Entity.Movie;
import com.moviesapi.movies.service.GenreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }
    // POST new genre
    @PostMapping
    public ResponseEntity<Genre> addGenre(@RequestBody Genre genre) {
        Genre createdGenre = genreService.createGenre(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGenre);
    }
    // GET genre by id
    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
        Optional<Genre> genre = genreService.getGenreById(id);
        return genre.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    // PATCH genre
    @PatchMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable Long id, @RequestBody Genre genre) {
        Genre updatedGenre = genreService.updateGenre(id, genre);
        return updatedGenre != null ? ResponseEntity.ok(updatedGenre) : ResponseEntity.notFound().build();
    }
    // DELETE genre also uses force parameter to delete associations
    @DeleteMapping("/{genreId}")
    public ResponseEntity<Void> deleteGenre(
            @PathVariable Long genreId,
            @RequestParam(value = "force", defaultValue = "false") boolean force) {

        boolean deleted = genreService.deleteGenre(genreId, force);

        if (deleted) {
            return ResponseEntity.noContent().build(); // Successful deletion
        } else {
            return ResponseEntity.notFound().build(); // Genre not found
        }
    }
    // GET movies by genre
    @GetMapping("/{id}/movies")
    public ResponseEntity<List<Movie>> getMoviesByGenreId(@PathVariable Long id) {
        List<Movie> movies = genreService.getMoviesByGenreId(id);
        return movies != null ? ResponseEntity.ok(movies) : ResponseEntity.notFound().build();
    }
}