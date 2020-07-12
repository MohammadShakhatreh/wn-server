package com.worldnavigator.game.maze;

import com.worldnavigator.game.maze.items.Item;
import com.worldnavigator.game.maze.room.Door;
import com.worldnavigator.game.maze.room.Room;
import com.worldnavigator.game.maze.room.RoomSide;
import java.util.*;

public final class Player {

    private int gold;

    private final Map<String, Item> items;

    private int location;

    private Direction direction;

    private boolean isDone;

    private final Maze maze;

    public Player(
            Maze maze,
            int gold,
            int location,
            Direction direction,
            Map<String, Item> items
    ) {

        if(gold < 0)
            throw new IllegalArgumentException("Gold should be non-negative integer.");

        this.gold = gold;

        if(location < 0 || location >= maze.numberOfRooms())
            throw new IndexOutOfBoundsException();

        this.location = location;
        this.direction = Objects.requireNonNull(direction);

        this.isDone = false;
        this.maze = Objects.requireNonNull(maze);
        this.items = Objects.requireNonNull(items);
    }

    /**
     * Moves the player to the room based on provided direction
     * if there is an open door in that direction
     *
     * @param direction The direction of the door you want to go through.
     */
    public void move(Direction direction) {
        if(isDone)
            throw new NoSuchElementException();

        Room room = maze.getRoom(location);
        RoomSide side = room.getSide(direction);

        if(side instanceof Door) {

            Door door = (Door) side;

            if(door.isOpen()) {

                if(door.getNextRoom() >= 0)
                    location = door.getNextRoom();
                else
                    isDone = true;

            } else {
                throw new IllegalStateException();
            }

        } else {
            throw new IllegalStateException();
        }
    }

    /**
     *
     * @return current room the player is in.
     */
    public Room current() {
        return maze.getRoom(location);
    }

    /**
     *
     * @return true if the player entered the exit door of the maze.
     */
    public boolean isDone() {
        return isDone;
    }

    public void setGold(int amount) {
        if(amount < 0)
            throw new IllegalArgumentException();

        gold = amount;
    }

    public void setDirection(Direction direction) {
        this.direction = Objects.requireNonNull(direction);
    }

    /**
     *
     * @param item To be added to the player items list.
     * @return true if the item wasn't with player originally.
     */
    public boolean addItem(Item item) {
        return items.putIfAbsent(item.toString(), item) == null;
    }

    public int getGold() {
        return gold;
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public Direction getDirection() {
        return direction;
    }
}
