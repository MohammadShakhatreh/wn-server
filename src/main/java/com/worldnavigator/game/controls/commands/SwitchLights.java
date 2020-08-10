package com.worldnavigator.game.controls.commands;

import com.worldnavigator.game.controls.Command;
import com.worldnavigator.game.controls.PlayerContext;
import com.worldnavigator.game.maze.room.Room;
import org.springframework.stereotype.Component;

@Component
public class SwitchLights implements Command {

    @Override
    public String execute(PlayerContext context, String... args) {
        Room room = context.getCurrentRoom();

        if(room.hasLights()) {
            room.switchLights();

            return room.isLit() ? "The room is lit now!" : "The room is dark now!";
        }

        return "The room doesn't have lights!\n"
                + "You should use a flashlight to see.";
    }

    @Override
    public String name() {
        return "switch-lights";
    }

    @Override
    public String description() {
        return "Will turn the room lights on or off if the room have lights";
    }
}