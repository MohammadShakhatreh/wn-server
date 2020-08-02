package com.worldnavigator.game.commands;

import com.worldnavigator.game.Player;
import com.worldnavigator.game.maze.items.Item;
import com.worldnavigator.game.maze.items.Key;
import com.worldnavigator.game.maze.room.*;

import java.util.Optional;

public final class CheckVisitor implements RoomSideVisitor {

    private final Player player;

    public CheckVisitor(Player player) {
        this.player = player;
    }

    @Override
    public String execute(Mirror mirror) {
        return execute((HiddenItem) mirror);
    }

    @Override
    public String execute(Painting painting) {
        return execute((HiddenItem) painting);
    }

    private String execute(HiddenItem hiddenItem) {
        Optional<Item> item = hiddenItem.getItem();

        if(item.isPresent()) {

            if(hiddenItem.isCollected()) {
                return "The hidden item is gone!";

            } else {
                player.addItem(item.get());
                hiddenItem.setCollected(true);
                return String.format("You got a %s.", item.get());
            }

        } else {
            return "There is nothing hidden here!";
        }
    }

    @Override
    public String execute(Chest chest) {

        Lock lock = chest.getLock();

        if(lock.isOpen()) {

            if(chest.isCollected()) {
                return "The chest contents are already collected!";

            } else {

                StringBuilder sb = new StringBuilder();
                sb.append(String.format("You got %d gold from this chest!", chest.getGold()));

                player.setGold(player.getGold() + chest.getGold());

                sb.append("The items you acquired from this chest are:\n");
                for(Item item : chest.getItems()) {
                    if(player.addItem(item))
                        sb.append(String.format("\t%s", item));
                }

                chest.setCollected(true);
                return sb.toString();
            }

        } else {
            return execute((Lockable) chest);
        }
    }

    @Override
    public String execute(Door door) {
        return execute((Lockable) door);
    }

    private String execute(Lockable lockable) {

        Lock lock = lockable.getLock();

        if(lock.isLocked()) {
            Key key = lock.getKey().orElseThrow();
            return String.format("The %s is locked, you need a %s to unlock it!", lockable, key);

        } else {
            if(lock.isOpen())
                return String.format("The %s is open!", lockable);
            else
                return String.format("The %s is not open!", lockable);
        }
    }

    @Override
    public String execute(Seller seller) {
        return "You are in front of a seller!";
    }

    @Override
    public String execute(Wall wall) {
        return "You are in front of an empty wall!";
    }
}