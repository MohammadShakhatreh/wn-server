package com.worldnavigator.game.maze.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.worldnavigator.game.maze.items.Item;
import com.worldnavigator.game.maze.items.Key;

import java.util.Objects;

public final class Door extends RoomSide implements Openable {
    private final Key key;
    private boolean isOpen;
    private boolean isUnlocked;

    private final int nextRoom;

    @JsonCreator
    public Door(
            @JsonProperty("key") Key key,
            @JsonProperty("nextRoom") int nextRoom,
            @JsonProperty("open") boolean isOpen,
            @JsonProperty("unlocked") boolean isUnlocked
    ) {
        this.key = key;

        if(isOpen && !isUnlocked)
            throw new IllegalArgumentException("if isOpen is true so should the isUnlocked");

        this.isOpen = isOpen;
        this.isUnlocked = isUnlocked;

        this.nextRoom = nextRoom;
    }

    @Override
    public void accept(RoomSideVisitor visitor) {
        visitor.execute(this);
    }

    @Override
    public void open() {
        if(isUnlocked())
            isOpen = true;
    }

    @Override
    public boolean lock(Item key) {
        if(Objects.equals(this.key, key)) {
            isOpen = false;
            isUnlocked = false;
        }

        return !isUnlocked;
    }

    @Override
    public boolean unlock(Item key) {
        if(Objects.equals(this.key, key))
            isUnlocked = true;

        return isUnlocked;
    }


    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public boolean isUnlocked() {
        return isUnlocked;
    }

    public int getNextRoom() {
        return nextRoom;
    }

    @Override
    public Key getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "Door";
    }
}
