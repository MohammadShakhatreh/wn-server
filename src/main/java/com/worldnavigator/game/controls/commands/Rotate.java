package com.worldnavigator.game.controls.commands;

import com.worldnavigator.game.Player;
import com.worldnavigator.game.controls.Command;
import com.worldnavigator.game.controls.PlayerContext;
import org.springframework.stereotype.Component;

@Component
public class Rotate implements Command {

    @Override
    public boolean validate(PlayerContext context, String... args) {
        return args.length == 1
                && args[0].equals("left")
                || args[0].equals("right");
    }

    @Override
    public String execute(PlayerContext context, String... args) {

        String direction = args[0];
        Player player = context.getPlayer();

        if(direction.equals("left"))
            player.setDirection(player.getDirection().left());
        else
            player.setDirection(player.getDirection().right());

        return "Your direction now is " + player.getDirection().name().toLowerCase();
    }

    @Override
    public boolean available(PlayerContext context) {
        return true;
    }

    @Override
    public String name() {
        return "rotate";
    }

    @Override
    public String args() {
        return "<left|right>";
    }

    @Override
    public String description() {
        return "Rotate the player to the left or right";
    }
}
