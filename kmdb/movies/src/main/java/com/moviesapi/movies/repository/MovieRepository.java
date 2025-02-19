package com.moviesapi.movies.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moviesapi.movies.Entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    // Self explanatory :)
    Page<Movie> findByGenres_Id(Long genreId, Pageable pageable);
    Integer countByGenres_Id(Long genreId);
    
    @Query("SELECT m FROM Movie m JOIN m.actors a WHERE a.id = :actorId")
    Page<Movie> findMoviesByActorId(@Param("actorId") Long actorId, Pageable pageable);

    Page<Movie> findByReleaseYear(int releaseYear, Pageable pageable);
    Optional<Movie> findByTitle(String title);

    @Query("SELECT a FROM Movie a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Movie> findByMovieTitle(@Param("title") String title);
    

}
