package com.moviesapi.movies.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moviesapi.movies.Entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findAllById(Iterable<Long> ids);
    boolean existsByNameIgnoreCase(String name); // To check for duplicate genres
}
