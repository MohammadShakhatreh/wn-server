package com.worldnavigator.game.maze.room;

public final class Wall extends RoomSide {

    @Override
    public void accept(RoomSideVisitor visitor) {
        visitor.execute(this);
    }

    @Override
    public String toString() {
        return "Wall";
    }
}
