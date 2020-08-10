package com.worldnavigator.game.controls.commands;

import com.worldnavigator.game.controls.Command;
import com.worldnavigator.game.controls.PlayerContext;
import org.springframework.stereotype.Component;

@Component
public class Status implements Command {

    @Override
    public boolean validate(PlayerContext context, String... args) {
        return false;
    }

    @Override
    public String execute(PlayerContext context, String... args) {
        return null;
    }

    @Override
    public boolean available(PlayerContext context) {
        return false;
    }

    @Override
    public String name() {
        return "fight:status";
    }

    @Override
    public String description() {
        return "Gives fight status.";
    }
}
