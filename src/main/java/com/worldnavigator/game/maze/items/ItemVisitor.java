package com.worldnavigator.game.maze.items;

public interface ItemVisitor {

    String execute(Key key);

    String execute(Flashlight flashlight);
}
