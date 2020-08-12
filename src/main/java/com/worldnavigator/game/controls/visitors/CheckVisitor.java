package com.worldnavigator.game.controls.visitors;

import com.worldnavigator.game.Player;
import com.worldnavigator.game.maze.items.Item;
import com.worldnavigator.game.maze.roomsides.*;

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

        Optional<Lock> optional = chest.getLock();

        if(optional.isPresent()) {
            Lock lock = optional.get();

            if(lock.isOpen())
                return collect(chest);
            else
                return execute((Lockable) chest);
        } else {
            return collect(chest);
        }
    }

    private String collect(Chest chest) {

        if(chest.isCollected())
            return "The chest contents are already collected!";

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

    @Override
    public String execute(Door door) {
        return execute((Lockable) door);
    }

    private String execute(Lockable lockable) {

        Optional<Lock> optional = lockable.getLock();

        if(optional.isPresent()) {

            Lock lock = optional.get();

            if (lock.isLocked()) {
                return String.format("The %s is locked, you need a %s to unlock it!", lockable, lock.getKey());

            } else {
                if (lock.isOpen())
                    return String.format("The %s is open!", lockable);
                else
                    return String.format("The %s is not open!", lockable);
            }
        } else {
            return String.format("There is no lock on the %s!", lockable);
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