package com.moviesapi.movies.service;

import com.moviesapi.movies.Entity.Actor;
import com.moviesapi.movies.repository.ActorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    public Actor createActor(Actor actor) {
        return actorRepository.save(actor);
    }

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    public Optional<Actor> getActorById(Long id) {
        return actorRepository.findById(id);
    }

    public List<Actor> searchActorsByName(String name) {
        return actorRepository.findByNameIgnoreCase(name);
    }

    public void deleteActor(Long id) {
        actorRepository.deleteById(id);
    }

    public Actor updateActor(Long id, Actor newActorData) {
        return actorRepository.findById(id).map(actor -> {
            if (newActorData.getName() != null) actor.setName(newActorData.getName());
            if (newActorData.getBirthDate() != null) actor.setBirthDate(newActorData.getBirthDate());
            return actorRepository.save(actor);
        }).orElse(null);
    }
}

