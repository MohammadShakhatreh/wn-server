package com.worldnavigator.game.controls.commands;

import com.worldnavigator.game.controls.Command;
import com.worldnavigator.game.controls.PlayerContext;
import org.springframework.stereotype.Component;

@Component
public class Move implements Command {

    @Override
    public boolean validate(PlayerContext context, String... args) {
        return args.length == 1
                && (args[0].equals("forward") || args[0].equals("backward"));
    }

    @Override
    public boolean available(PlayerContext context) {
        return true;
    }

    @Override
    public String execute(PlayerContext context, String... args) {
        return "To Be Implemented";
    }

    @Override
    public String name() {
        return "move";
    }

    @Override
    public String args() {
        return "<forward|backward>";
    }

    @Override
    public String description() {
        return "Moves the player between rooms";
    }
}
