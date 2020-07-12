package com.worldnavigator.game.maze.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.worldnavigator.game.maze.items.Item;
import com.worldnavigator.game.maze.items.Key;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Chest extends RoomSide implements Openable {

    private final Key key;
    private boolean isOpen;
    private boolean isUnlocked;

    private final int gold;
    private final List<Item> items;
    private boolean isCollected;

    @JsonCreator
    public Chest(
            @JsonProperty("key") Key key,
            @JsonProperty("open") boolean isOpen,
            @JsonProperty("unlocked") boolean isUnlocked,
            @JsonProperty("gold") int gold,
            @JsonProperty("items") List<Item> items
    ) {
        this.key = key;

        if(isOpen && !isUnlocked)
            throw new IllegalArgumentException("if isOpen is true so should the isUnlocked");

        this.isOpen = isOpen;
        this.isUnlocked = isUnlocked;

        this.gold = gold;
        this.items = Objects.requireNonNull(items);
        this.isCollected = false;
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

    @Override
    public Key getKey() {
        return key;
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
