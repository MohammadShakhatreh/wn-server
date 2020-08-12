package com.worldnavigator.game.maze;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.worldnavigator.game.Player;
import com.worldnavigator.game.maze.items.Item;
import com.worldnavigator.game.maze.roomsides.RoomSide;

import java.util.*;

public final class Room {

    private final int index;
    
    private boolean isLit;

    private final boolean hasLights;

    @JsonDeserialize(as = EnumMap.class)
    private final Map<Direction, RoomSide> sides;

    private final Map<String, Item> items = new HashMap<>();

    private final Deque<Player> players = new LinkedList<>();

    @JsonCreator
    public Room(
            @JsonProperty("index") int index,
            @JsonProperty("lit") boolean isLit,
            @JsonProperty("hasLights") boolean hasLights,
            @JsonProperty("sides") Map<Direction, RoomSide> sides
    ) {
        this.index = index;
        this.isLit = isLit;
        this.hasLights = hasLights;
        this.sides = Objects.requireNonNull(sides);
    }

    public void switchLights() {
        if(hasLights)
            isLit = !isLit;
    }

    public void addItems(Map<String, Item> items) {
        this.items.putAll(items);
    }

    public RoomSide getSide(Direction dir) {
        return this.sides.get(dir);
    }

    public int getIndex() {
        return index;
    }

    public boolean isLit() {
        return isLit;
    }

    public boolean hasLights() {
        return hasLights;
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

    public boolean isFull() {
        return players.size() == 2;
    }

    public void removePlayer(Player player) {

        if(player.equals(players.getFirst()))
            players.removeFirst();

        else if(player.equals(players.getLast()))
            players.removeLast();
    }

    public void addPlayer(Player player) {
        players.addLast(player);
    }

    public Player getFirstPlayer() {
        return players.getFirst();
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public Deque<Player> getPlayers() {
        return players;
    }

    public Map<Direction, RoomSide> getSides() {
        return sides;
    }
}
