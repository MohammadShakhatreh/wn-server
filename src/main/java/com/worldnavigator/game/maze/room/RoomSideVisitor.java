package com.worldnavigator.game.maze.room;

public interface RoomSideVisitor {

    String execute(Mirror mirror);

    String execute(Painting painting);

    String execute(Chest chest);

    String execute(Door door);

    String execute(Seller seller);

    String execute(Wall wall);
}
