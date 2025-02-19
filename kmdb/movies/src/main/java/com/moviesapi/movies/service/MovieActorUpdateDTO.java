//This handles operations for Patch

package com.moviesapi.movies.service;

import java.util.Set;

public class MovieActorUpdateDTO {
    private Set<Long> actorIds;

    public Set<Long> getActorIds() {
        return actorIds;
    }

    public void setActorIds(Set<Long> actorIds) {
        this.actorIds = actorIds;
    }
}