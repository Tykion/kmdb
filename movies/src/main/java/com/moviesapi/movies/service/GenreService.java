package com.moviesapi.movies.service;

import com.moviesapi.movies.Entity.Genre;
import com.moviesapi.movies.Entity.Movie;
import com.moviesapi.movies.repository.GenreRepository;
import com.moviesapi.movies.repository.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MovieRepository movieRepository;
    // Create genre and check if duplicate
    public Genre createGenre(Genre genre) {
        if (genreRepository.existsByNameIgnoreCase(genre.getName())) {
            throw new IllegalArgumentException("Genre with this name already exists.");
        }
        return genreRepository.save(genre);
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Optional<Genre> getGenreById(Long id) {
        return genreRepository.findById(id);
    }

    public Genre updateGenre(Long id, Genre newGenreData) {
        return genreRepository.findById(id).map(genre -> {
            if (newGenreData.getName() != null) {
                genre.setName(newGenreData.getName());
            }
            return genreRepository.save(genre);
        }).orElse(null);
    }

    public boolean deleteGenre(Long genreId, boolean force) {
        Optional<Genre> genreOptional = genreRepository.findById(genreId);

        if (genreOptional.isEmpty()) {
            return false; // Genre not found
        }
        int associatedMoviesCount = movieRepository.countByGenres_Id(genreId);
        Genre genre = genreOptional.get();

        if (force) {
            // If force is true, remove relationships with movies
            for (Movie movie : genre.getMovies()) {
                movie.getGenres().remove(genre);
                movieRepository.save(movie); // Update the movie to reflect genre removal
            }
            genreRepository.delete(genre); // Delete the genre after clearing relationships
        } else {
            // If force is false, only delete if no movies are associated
            if (genre.getMovies().isEmpty()) {
                genreRepository.delete(genre);
            } else {
                throw new RuntimeException("Genre '" + genre.getName() + "' cannot be deleted because it is associated with " + associatedMoviesCount + " movies");
            }
        }
        return true; // Deletion successful
    }

    public List<Movie> getMoviesByGenreId(Long genreId) {
        return genreRepository.findById(genreId)
            .map(genre -> new ArrayList<>(genre.getMovies())) // Convert Set<Movie> to List<Movie>
            .orElse(null);
    }
}
