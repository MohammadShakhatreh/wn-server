package com.worldnavigator.game.maze.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.worldnavigator.game.maze.items.Key;

import java.util.Objects;
import java.util.Optional;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class
)
public final class Lock {

    private final Key key;

    private boolean open;

    private boolean locked;

    @JsonCreator
    public Lock(
            @JsonProperty("key") Key key,
            @JsonProperty("open") boolean open,
            @JsonProperty("locked") boolean locked
    ) {
        this.key = key;

        if(open && locked)
            throw new IllegalArgumentException("The lock can't be open and locked at the same time.");

        if(key == null && locked)
            throw new IllegalArgumentException("You have to provide a key if you want to lock things.");

        this.open = open;
        this.locked = locked;
    }

    public void open() {
        if(!locked)
            open = true;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean lock(Key key) {
        if(Objects.equals(this.key, key)) {
            open = false;
            locked = true;
        }

        return locked;
    }

    public boolean unlock(Key key) {
        if(Objects.equals(this.key, key)) {
            open = false;
            locked = false;
        }

        return !locked;
    }

    public boolean isLocked() {
        return locked;
    }

    public Optional<Key> getKey() {
        return Optional.ofNullable(key);
    }
}
