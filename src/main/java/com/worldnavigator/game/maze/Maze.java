package com.worldnavigator.game.maze;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.worldnavigator.game.maze.room.Room;

import java.util.LinkedHashMap;
import java.util.List;

public class Maze {

    private final List<Room> rooms;

    @JsonCreator
    public Maze(@JsonProperty("rooms") List<Room> rooms) {
        this.rooms = rooms;
    }

    public Room getRoom(int idx) {
        return rooms.get(idx);
    }

    public int numberOfRooms() {
        return rooms.size();
    }

    public Player player(){
        return new Player(
                this,
                0,
                0,
                Direction.NORTH,
                new LinkedHashMap<>()
        );
    }
}
