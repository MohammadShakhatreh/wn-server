package com.worldnavigator.game.maze.room;

public final class Wall extends RoomSide {

    @Override
    public String accept(RoomSideVisitor visitor) {
        return visitor.execute(this);
    }

    @Override
    public String toString() {
        return "Wall";
    }
}
