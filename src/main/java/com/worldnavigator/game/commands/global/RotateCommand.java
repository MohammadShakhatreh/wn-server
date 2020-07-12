package com.worldnavigator.game.commands.global;

import com.worldnavigator.game.commands.Command;
import com.worldnavigator.game.commands.Output;
import com.worldnavigator.game.maze.Direction;
import com.worldnavigator.game.maze.Player;

public final class RotateCommand implements Command {

    private final Player player;
    private final Output output;

    public RotateCommand(Player player, Output output) {
        this.player = player;
        this.output = output;
    }

    @Override
    public void execute(String...args) {
        if(!validate(args)) {
            output.println("Invalid argument to the rotate command!");
            output.println("Argument is either <left> or <right>");
            return;
        }

        String argument = args[0];
        if(argument.equalsIgnoreCase("left")) {

            Direction direction = player.getDirection().left();
            player.setDirection(direction);

        } else if(argument.equalsIgnoreCase("right")) {
            Direction direction = player.getDirection().right();
            player.setDirection(direction);
        }
    }

    private boolean validate(String... args) {
        return args.length == 1
                && args[0].equals("left")
                || args[0].equals("right");
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
