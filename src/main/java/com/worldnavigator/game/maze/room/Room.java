package com.worldnavigator.game.maze.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.worldnavigator.game.Player;
import com.worldnavigator.game.maze.Direction;
import com.worldnavigator.game.maze.items.Item;

import java.util.*;

public final class Room {

    private boolean isLit;
    private final boolean hasLights;

    @JsonDeserialize(as = EnumMap.class)
    private final Map<Direction, RoomSide> sides;

    private final List<Player> players = new ArrayList<>();

    private final Map<String, Item> items = new HashMap<>();

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

    public Map<Direction, RoomSide> getSides() {
        return sides;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addItems(Map<String, Item> items) {
        this.items.putAll(items);
    }
}
