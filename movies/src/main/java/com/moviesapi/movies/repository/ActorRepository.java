package com.moviesapi.movies.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moviesapi.movies.Entity.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    

    List<Actor> findAllById(Iterable<Long> ids);
    
    // Used to find name, works partially as well
    @Query("SELECT a FROM Actor a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Actor> findByNameIgnoreCase(@Param("name") String name);
}