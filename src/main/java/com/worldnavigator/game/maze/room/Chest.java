package com.worldnavigator.game.maze.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.worldnavigator.game.maze.items.Item;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

        this.gold = gold;
        this.isCollected = false;
        this.items = Objects.requireNonNull(items);
    }

    @Override
    public String accept(RoomSideVisitor visitor) {
        return visitor.execute(this);
    }

    @Override
    public Lock getLock() {
        return lock;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public int getGold() {
        return gold;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean isCollected() {
        return isCollected;
    }

    @Override
    public String toString() {
        return "Chest";
    }
}
