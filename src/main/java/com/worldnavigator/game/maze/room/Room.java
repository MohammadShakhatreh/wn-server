package com.worldnavigator.game.maze.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.worldnavigator.game.maze.Direction;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public final class Room {

    private boolean isLit;
    private boolean hasLights;

    @JsonDeserialize(as = EnumMap.class)
    private Map<Direction, RoomSide> sides;

    @JsonCreator
    public Room(
            @JsonProperty("lit") boolean isLit,
            @JsonProperty("hasLights") boolean hasLights,
            @JsonProperty("sides") Map<Direction, RoomSide> sides
    ) {
        this.isLit = isLit;
        this.hasLights = hasLights;
        this.sides = Objects.requireNonNull(sides);
    }

    public void switchLights() {
        if(hasLights)
            isLit = !isLit;
    }

    public RoomSide getSide(Direction dir) {
        return this.sides.get(dir);
    }

    public boolean isLit() {
        return isLit;
    }

    public boolean hasLights() {
        return hasLights;
    }
}
