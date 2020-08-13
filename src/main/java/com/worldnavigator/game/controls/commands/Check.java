package com.worldnavigator.game.controls.commands;

import com.worldnavigator.game.Player;
import com.worldnavigator.game.PlayerMode;
import com.worldnavigator.game.controls.Command;
import com.worldnavigator.game.controls.PlayerContext;
import com.worldnavigator.game.controls.visitors.CheckVisitor;
import com.worldnavigator.game.maze.roomsides.RoomSide;
import org.springframework.stereotype.Component;

@Component
public final class Check implements Command {

    @Override
    public String execute(PlayerContext context, String... args) {

        RoomSide side = context.getCurrentRoomSide();
        return side.accept(new CheckVisitor(context.getPlayer()));
    }

    @Override
    public boolean available(PlayerContext context) {
        Player player = context.getPlayer();
        return player.getMode() == PlayerMode.WALKING;
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
