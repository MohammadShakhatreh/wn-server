package com.worldnavigator.game.maze.room;

import com.worldnavigator.game.maze.items.Item;

import java.util.Optional;

public interface HiddenItem {

    Optional<Item> getItem();

    void setCollected(boolean collected);

    boolean isCollected();
}
