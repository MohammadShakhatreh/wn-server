package com.worldnavigator.game.commands.global;

import com.worldnavigator.game.commands.Command;
import com.worldnavigator.game.commands.Output;
import com.worldnavigator.game.maze.Player;
import com.worldnavigator.game.maze.items.Item;
import com.worldnavigator.game.maze.room.*;

import java.util.Optional;

public final class CheckCommand implements Command {

    private final Player player;
    private final Output output;

    public CheckCommand(Player player, Output output) {
        this.player = player;
        this.output = output;
    }

    @Override
    public void execute(String... args) {
        RoomSide side = player
                .current()
                .getSide(player.getDirection());

        side.accept(new CheckVisitor());
    }

    @Override
    public String name() {
        return "check";
    }

    @Override
    public String description() {
        return "Checks the thing in front of the player.";
    }

    private final class CheckVisitor implements RoomSideVisitor {

        @Override
        public void execute(Mirror mirror) {
            execute((HiddenItem) mirror);
        }

        @Override
        public void execute(Painting painting) {
            execute((HiddenItem) painting);
        }

        private void execute(HiddenItem hiddenItem) {
            Optional<Item> item = hiddenItem.getItem();

            if(item.isPresent()) {

                if(hiddenItem.isCollected()) {
                    output.println("You already got the hidden item!");

                } else {
                    player.addItem(item.get());
                    hiddenItem.setCollected(true);
                    output.println(String.format("You got a %s.", item.get()));
                }

            } else {
                output.println("There is nothing hidden here!");
            }
        }

        @Override
        public void execute(Chest chest) {

            if(chest.isOpen()) {

                if(chest.isCollected()) {
                    output.println("The chest contents are already collected!");

                } else {

                    output.println(String.format("You got %d gold from this chest!", chest.getGold()));
                    player.setGold(player.getGold() + chest.getGold());

                    output.println("The items you acquired from this chest are:");
                    for(Item item : chest.getItems()){
                        if(player.addItem(item))
                            output.println(String.format("\t%s", item));
                    }

                    chest.setCollected(true);
                }

            } else {
                execute((Openable) chest);
            }
        }

        @Override
        public void execute(Door door) {
            execute((Openable) door);
        }

        private void execute(Openable openable) {
            if(openable.isUnlocked()) {

                if(openable.isOpen())
                    output.println(String.format("The %s is open!", openable));
                else
                    output.println(String.format("The %s is not open!", openable));

            } else {
                output.println(String.format("The %s is locked, you need a %s to unlock it!", openable, openable.getKey()));
            }
        }

        @Override
        public void execute(Seller seller) {
            output.println("You are in front of a seller!");
        }

        @Override
        public void execute(Wall wall) {
            output.println("You are in front of an empty wall!");
        }
    }
}
