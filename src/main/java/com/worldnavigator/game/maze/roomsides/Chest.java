package com.worldnavigator.game.maze.roomsides;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.worldnavigator.game.maze.items.Item;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Chest extends RoomSide implements Lockable {

    private final Lock lock;

    private final int gold;
    private boolean isCollected;
    private final List<Item> items;

    @JsonCreator
    public Chest(
            @JsonProperty("gold") int gold,
            @JsonProperty("lock") Lock lock,
            @JsonProperty("items") List<Item> items
    ) {

        this.lock = lock;

        if(gold < 0)
            throw new IllegalArgumentException("Gold must be non-negative number");

        this.gold = gold;
        this.isCollected = false;
        this.items = Objects.requireNonNull(items);
    }

    @Override
    public String accept(RoomSideVisitor visitor) {
        return visitor.execute(this);
    }

    @Override
    public Optional<Lock> getLock() {
        return Optional.ofNullable(lock);
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public int getGold() {
        return gold;
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean isCollected() {
        return isCollected;
    }

    @Override
    public String toString() {
        return "Chest";
    }
}
