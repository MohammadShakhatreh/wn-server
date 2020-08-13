package com.worldnavigator.game.controls.commands;

import com.worldnavigator.game.Game;
import com.worldnavigator.game.Player;
import com.worldnavigator.game.PlayerMode;
import com.worldnavigator.game.controls.Command;
import com.worldnavigator.game.controls.PlayerContext;
import com.worldnavigator.game.fight.Fight;
import com.worldnavigator.game.maze.Direction;
import com.worldnavigator.game.maze.Room;
import com.worldnavigator.game.maze.items.Item;
import com.worldnavigator.game.maze.roomsides.Door;
import com.worldnavigator.game.maze.roomsides.Lock;
import com.worldnavigator.game.maze.roomsides.RoomSide;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public final class Move implements Command {

    @Override
    public boolean validate(PlayerContext context, String... args) {
        return args.length == 1
                && (args[0].equals("forward")
                || args[0].equals("backward"));
    }

    @Override
    public synchronized String execute(PlayerContext context, String... args) {

        Player player = context.getPlayer();
        Direction direction = player.getDirection();
        if(args[0].equals("backward"))
            direction = direction.reverse();


        RoomSide side = context.getCurrentRoom().getSide(direction);

        if(side instanceof Door) {

            Door door = (Door) side;
            Optional<Lock> optional = door.getLock();

            if(optional.isPresent()) {
                Lock lock = optional.get();

                if(lock.isOpen())
                    return move(context, door);
                else
                    return "The door is not open you can't go though.";

            } else {
                return move(context, door);
            }

        } else {
            return "You can't move " + args[0] + " there is not door.";
        }
    }

    private String move(PlayerContext context, Door door) {

        Player player = context.getPlayer();
        Game game = context.getGame();

        if(door.getRoom() < 0) {
            game.setWinner(player);
            return "You won! you entered a special door.";
        }

        Room source = context.getCurrentRoom();
        Room destination = context.getGame()
                .getMaze()
                .getRoom(door.getRoom());

        if(destination.isFull())
            return "You can't go to the room there is a fight.";

        boolean empty = destination.isEmpty();

        source.removePlayer(player);
        destination.addPlayer(player);
        player.setLocation(door.getRoom());

        if(empty) {
            Map<String, Item> items = destination.getItems();

            player.addItems(items);
            items.clear();

            return "You are in the next room.";
        }

        Player opponent = destination.getFirstPlayer();
        return fight(context.getGame(), player, opponent);
    }

    private String fight(Game game, Player player, Player opponent) {

        if(player.getGold() > opponent.getGold()) {

            player.addItems(opponent.getItems());
            opponent.setMode(PlayerMode.LOST);
            game.distributePlayerGold(opponent);

            return "You won the fight by gold.";

        } else if (player.getGold() < opponent.getGold()) {

            opponent.addItems(player.getItems());
            player.setMode(PlayerMode.LOST);
            game.distributePlayerGold(player);

            return "You lost the fight by gold.";
        }

        player.setMode(PlayerMode.FIGHTING);
        opponent.setMode(PlayerMode.FIGHTING);

        Fight fight = new Fight(player, opponent);
        game.addFight(fight);

        return "There is a player in the room you are in fight mode.";
    }

    @Override
    public boolean available(PlayerContext context) {

        Player player = context.getPlayer();
        Room room = context.getCurrentRoom();
        Direction direction = player.getDirection();

        return player.getMode() == PlayerMode.WALKING
                && (room.getSide(direction) instanceof Door
                || room.getSide(direction.reverse()) instanceof Door);
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
