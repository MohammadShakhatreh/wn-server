package com.worldnavigator.game;

import com.worldnavigator.game.maze.Direction;
import com.worldnavigator.game.maze.items.Item;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Player {

    @EqualsAndHashCode.Include
    private String username;

    private int gold;

    private int location;

    private Direction direction;

    private PlayerMode mode = PlayerMode.WALKING;

    private final Map<String, Item> items = new HashMap<>();

    public Player(String username, int gold, int location, Direction direction) {
        this.username = Objects.requireNonNull(username);

        if(gold < 0)
            throw new IllegalArgumentException("Gold must be non-negative number.");

        this.gold = gold;

        if(location < 0)
            throw new IllegalArgumentException("Location must be non-negative number.");

        this.location = location;

        this.direction = Objects.requireNonNull(direction);
    }

    public boolean addItem(Item item) {
        return items.putIfAbsent(item.toString(), item) == null;
    }

    public void addItems(Map<String, Item> items) {
        this.items.putAll(items);
    }
}
