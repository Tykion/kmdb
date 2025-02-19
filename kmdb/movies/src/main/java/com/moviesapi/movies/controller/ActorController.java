package com.moviesapi.movies.controller;

import com.moviesapi.movies.Entity.Actor;
import com.moviesapi.movies.service.ActorDTO;
import com.moviesapi.movies.service.ActorService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    @Autowired
    private ActorService actorService;

    // Get actors by name
    @GetMapping
    public ResponseEntity<List<Actor>> getActorsByName(@Valid @RequestParam(required = false) String name) {
        List<Actor> actors;
        if (name != null) {
            actors = actorService.searchActorsByName(name);
        } else {
            actors = actorService.getAllActors();
        }
        return ResponseEntity.ok(actors);
    }
    // Creating actor POST
    @PostMapping
    public ResponseEntity<String> createActor(@Valid @RequestBody ActorDTO actorDTO) {
        return ResponseEntity.ok("Actor created successfully!");
    }

    // GET actors by id
    @GetMapping("/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable Long id) {
        Optional<Actor> actor = actorService.getActorById(id);
        return actor.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    // PATCH operation to update an actor
    @PatchMapping("/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable Long id, @RequestBody Actor actor) {
        Actor updatedActor = actorService.updateActor(id, actor);
        return updatedActor != null ? ResponseEntity.ok(updatedActor) : ResponseEntity.notFound().build();
    }
    // DELETE operation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Long id) {
        actorService.deleteActor(id);
        return ResponseEntity.noContent().build();
    }
}