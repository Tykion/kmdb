package com.moviesapi.movies.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.moviesapi.movies.Entity.Actor;
import com.moviesapi.movies.Entity.Genre;
import com.moviesapi.movies.Entity.Movie;
import com.moviesapi.movies.repository.ActorRepository;
import com.moviesapi.movies.repository.GenreRepository;
import com.moviesapi.movies.repository.MovieRepository;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private GenreRepository genreRepository;

    public Movie createMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setReleaseYear(movieDTO.getReleaseYear());
        movie.setDuration(movieDTO.getDuration());

        // Fetch actors by IDs and set them to the movie
        if (movieDTO.getActors() != null && !movieDTO.getActors().isEmpty()) {
            Set<Actor> actors = new HashSet<>(actorRepository.findAllById(movieDTO.getActors()));
            movie.setActors(actors);
        }

        // Fetch genres by IDs and set them to the movie
        if (movieDTO.getGenres() != null && !movieDTO.getGenres().isEmpty()) {
            Set<Genre> genres = new HashSet<>(genreRepository.findAllById(movieDTO.getGenres()));
            movie.setGenres(genres);
        }

        // Save the movie with associated actors and genres
        return movieRepository.save(movie);
    }

    public Movie updateMovieActors(Long movieId, MovieActorUpdateDTO updateDTO) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie with ID " + movieId + " not found."));

        // Fetch actors by IDs provided in the DTO
        Set<Actor> actors = new HashSet<>(actorRepository.findAllById(updateDTO.getActorIds()));

        // Set the new actors to the movie and save
        movie.setActors(actors);
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> getMoviebyTitle(String title) {
        return movieRepository.findByMovieTitle(title);
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Page<Movie> getMoviesByGenre(Long genreId, Pageable pageable) {
        return movieRepository.findByGenres_Id(genreId, pageable);
    }

    public Page<Movie> getMoviesByActor(Long actorId, Pageable pageable) {
        return movieRepository.findMoviesByActorId(actorId, pageable);
    }
    // pagination
    public Page<Movie> getMoviesByCriteria(Integer year, Long genre, Long actor, Pageable pageable) {
        if (year != null) {
            return movieRepository.findByReleaseYear(year, pageable);
        } else if (genre != null) {
            return movieRepository.findByGenres_Id(genre, pageable);
        } else if (actor != null) {
            return movieRepository.findMoviesByActorId(actor, pageable);
        } else {
            return movieRepository.findAll(pageable);  // Return all movies if no filters are written
        }
    }

    public Page<Movie> getMoviesByReleaseYear(int releaseYear, Pageable pageable) {
        return movieRepository.findByReleaseYear(releaseYear, pageable);
    }

    public Set<Actor> getActorsByMovieId(Long movieId) {
        return movieRepository.findById(movieId)
                .map(Movie::getActors)
                .orElse(null);
    }

    public Movie updateMovie(Long id, Movie updatedMovieData) {
        return movieRepository.findById(id).map(movie -> {
            if (updatedMovieData.getTitle() != null) {
                movie.setTitle(updatedMovieData.getTitle());
            }
            if (updatedMovieData.getReleaseYear() != 0) {
                movie.setReleaseYear(updatedMovieData.getReleaseYear());
            }
            if (updatedMovieData.getDuration() != 0) {
                movie.setDuration(updatedMovieData.getDuration());
            }
            if (updatedMovieData.getGenres() != null) {
                movie.setGenres(updatedMovieData.getGenres());
            }
            if (updatedMovieData.getActors() != null) {
                // Update actors, ensuring they are managed
                Set<Actor> managedActors = new HashSet<>();
                for (Actor actor : updatedMovieData.getActors()) {
                    if (actor.getId() != null) {
                        Actor existingActor = actorRepository.findById(actor.getId())
                                .orElseThrow(() -> new RuntimeException("Actor with ID " + actor.getId() + " not found."));
                        managedActors.add(existingActor);
                    } else {
                        // Save new actor if no ID is present
                        Actor newActor = actorRepository.save(actor);
                        managedActors.add(newActor);
                    }
                }
                movie.setActors(managedActors);
            }
            return movieRepository.save(movie);
        }).orElse(null);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
