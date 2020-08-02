package com.worldnavigator.game.maze.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Door extends RoomSide implements Lockable {

    private final int room;

    private final Lock lock;

    @JsonCreator
    public Door(
            @JsonProperty("room") int room,
            @JsonProperty("lock") Lock lock
    ) {

        this.room = room;
        this.lock = lock;
    }

    @Override
    public String accept(RoomSideVisitor visitor) {
        return visitor.execute(this);
    }

    @Override
    public Lock getLock() {
        return lock;
    }

    public int getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "Door";
    }
}
