package com.worldnavigator.game.controls.commands;

import com.worldnavigator.game.controls.Command;
import com.worldnavigator.game.controls.PlayerContext;
import org.springframework.stereotype.Component;

@Component
public class Play implements Command {

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
        return "fight:play";
    }

    @Override
    public String args() {
        return "<rock|paper|scissor>";
    }

    @Override
    public String description() {
        return "Plays a hand in a rock paper scissor fight.";
    }
}
