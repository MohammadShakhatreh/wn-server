package com.worldnavigator.game.controls.commands;

import com.worldnavigator.game.controls.visitors.CheckVisitor;
import com.worldnavigator.game.controls.Command;
import com.worldnavigator.game.controls.PlayerContext;
import com.worldnavigator.game.maze.room.RoomSide;
import org.springframework.stereotype.Component;

@Component
public class Check implements Command {

    @Override
    public String execute(PlayerContext context, String... args) {

        RoomSide side = context.getCurrentRoomSide();
        return side.accept(new CheckVisitor(context.getPlayer()));
    }

    @Override
    public String name() {
        return "check";
    }

    @Override
    public String description() {
        return "Checks the thing in front of the player.";
    }
}
