package com.worldnavigator.game.maze.room;

import com.worldnavigator.game.maze.items.Item;
import com.worldnavigator.game.maze.items.Key;

public interface Openable {

    void open();

    boolean isOpen();

    boolean lock(Item key);

    boolean unlock(Item key);

    boolean isUnlocked();

    Key getKey();
}
