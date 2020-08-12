package com.worldnavigator.game.controls.commands;

import com.worldnavigator.game.controls.Command;
import com.worldnavigator.game.controls.PlayerContext;

public class Quit implements Command {

    @Override
    public String execute(PlayerContext context, String... args) {
        return null;
    }

    @Override
    public String name() {
        return "quit";
    }

    @Override
    public String description() {
        return "Quit the game.";
    }
}
