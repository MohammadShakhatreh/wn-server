package com.worldnavigator.game.maze.items;

public interface ItemVisitor {

    void execute(Key key);

    void execute(Flashlight flashlight);
}
