package com.worldnavigator.game.commands.global;

import com.worldnavigator.game.commands.Command;
import com.worldnavigator.game.commands.Output;
import com.worldnavigator.game.maze.Player;
import com.worldnavigator.game.maze.room.Openable;
import com.worldnavigator.game.maze.room.RoomSide;

public final class OpenCommand implements Command {

    private final Player player;
    private final Output output;

    public OpenCommand(Player player, Output output) {
        this.player = player;
        this.output = output;
    }

    @Override
    public void execute(String... args) {

        RoomSide side = player
                .current()
                .getSide(player.getDirection());

        if(side instanceof Openable) {

            Openable openable = (Openable) side;

            if(openable.isUnlocked()) {

                if(openable.isOpen()) {
                    output.println(String.format("The %s is already open!", openable));
                } else {
                    openable.open();
                    output.println(String.format("The %s is now open!", openable));
                }

            } else {
                output.println(String.format("The %s is locked, you need a %s to unlock it!", openable, openable.getKey()));
            }

        } else {
            output.println("This is not something you can open!");
        }
    }

    @Override
    public String name() {
        return "open";
    }

    @Override
    public String description() {
        return "If you are in front of a door and it's unlocked it will open it";
    }
}
