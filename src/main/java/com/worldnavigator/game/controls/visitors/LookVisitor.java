package com.worldnavigator.game.controls.visitors;

import com.worldnavigator.game.maze.room.*;

public class LookVisitor implements RoomSideVisitor {

    @Override
    public String execute(Mirror mirror) {
        return "You See a silhouette of you";
    }

    @Override
    public String execute(Painting painting) {
        return "Painting";
    }

    @Override
    public String execute(Chest chest) {
        return "Chest";
    }

    @Override
    public String execute(Door door) {
        return "Door";
    }

    @Override
    public String execute(Seller seller) {
        return "Seller";
    }

    @Override
    public String execute(Wall wall) {
        return "Wall";
    }
}
