package com.worldnavigator.game.commands.global;

import com.worldnavigator.game.commands.Command;
import com.worldnavigator.game.commands.Output;
import com.worldnavigator.game.maze.Direction;
import com.worldnavigator.game.maze.Player;
import com.worldnavigator.game.maze.room.Door;
import com.worldnavigator.game.maze.room.Room;
import com.worldnavigator.game.maze.room.RoomSide;

public final class MoveCommand implements Command {

    private final Player player;
    private final Output output;

    public MoveCommand(Player player, Output output) {
        this.player = player;
        this.output = output;
    }

    @Override
    public void execute(String... args) {
        if(!validate(args)) {
            output.println("Invalid argument to the move command!");
            output.println("Argument is either <forward> or <backward>");
            return;
        }

        Room room = player.current();
        Direction direction = player.getDirection();

        if(args[0].equals("backward"))
            direction = direction.reverse();

        RoomSide side = room.getSide(direction);

        if(side instanceof Door) {
            Door door = (Door) side;

            if(door.isUnlocked()) {

                if(door.isOpen())
                    player.move(direction);
                else
                    output.println("The door is not open, you need to open it first!");

            } else {
                output.println(String.format("The door is locked, you need a %s to unlock it!", door.getKey()));
            }

        } else {
            output.println("You can't move there is no door!");
        }
    }

    private boolean validate(String... args) {
        return args.length == 1
                && args[0].equals("forward")
                || args[0].equals("backward");
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
