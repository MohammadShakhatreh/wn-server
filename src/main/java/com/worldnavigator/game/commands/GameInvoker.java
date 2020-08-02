package com.worldnavigator.game.commands;

import com.worldnavigator.game.Game;
import com.worldnavigator.game.Player;
import com.worldnavigator.game.maze.Direction;
import com.worldnavigator.game.maze.items.Flashlight;
import com.worldnavigator.game.maze.items.Item;
import com.worldnavigator.game.maze.room.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class GameInvoker extends Invoker {

    private final Game game;
    private final Player player;

    public GameInvoker(Game game, Player player) {
        this.game = game;
        this.player = player;

        this.commands.putAll(
                Stream.of(
                        new Open(),
                        new Move(),
                        new Use(),
                        new Look(),
                        new Check(),
                        new Trade(),
                        new Rotate(),
                        new SwitchLights()
                )
                .collect(toMap(Command::name, identity()))
        );
    }

    private class Open implements Command {

        @Override
        public String execute(String... args) {

            RoomSide side = game
                    .getMaze()
                    .getRoom(player.getLocation())
                    .getSide(player.getDirection());

            if(side instanceof Lockable) {

                Lockable lockable = (Lockable) side;
                Lock lock = lockable.getLock();

                if(lock.isLocked()) {

                    if(lock.isOpen()) {
                        return String.format("The %s is already open!", lockable);
                    } else {
                        lock.open();
                        return String.format("The %s is now open!", lockable);
                    }

                } else {
                    return String.format("The %s is locked, you need a %s to unlock it!", lockable, lock.getKey().orElseThrow());
                }
            }

            return "This is not something you can open!";
        }

        @Override
        public String name() {
            return "open";
        }

        @Override
        public String description() {
            return "If you are in front of a door and it's unlocked it will open it";
        }
    }

    private class Use implements Command {

        @Override
        public String execute(String... args) {
            if(args.length == 0)
                return name() + " command needs an argument " + args() + ".";

            String item = String.join(" ", args);
            Map<String, Item> items = player.getItems();

            if(items.containsKey(item)) {
                return items.get(item).accept(new UseVisitor(game, player));
            }

            return "You don't have an item with that name!";
        }

        @Override
        public String name() {
            return "use";
        }

        @Override
        public String args() {
            return "<item>";
        }

        @Override
        public String description() {
            return "Uses the item, for example using a flashlight would it turn on or off.";
        }
    }

    private class Look implements Command {

        @Override
        public String execute(String... args) {
            Room room = game.getMaze().getRoom(player.getLocation());
            RoomSide side = room.getSide(player.getDirection());
            Map<String, Item> items = player.getItems();

            if(room.isLit()
                    || (items.containsKey("flashlight")
                    && ((Flashlight) items.get("flashlight")).isOn())
            ) {
                return side.accept(new LookVisitor());
            }

            return "The room is dark!";
        }

        @Override
        public String name() {
            return "look";
        }

        @Override
        public String description() {
            return "Gives a description of whats in front of the player";
        }
    }

    private class Move implements Command {

        @Override
        public String execute(String... args) {
            if(!validate(args)) {
                return "Invalid argument to the move command!\n"
                        + "Argument is either <forward> or <backward>";
            }

            Room room = game.getMaze().getRoom(player.getLocation());
            Direction direction = player.getDirection();

            if(args[0].equals("backward"))
                direction = direction.reverse();

            RoomSide side = room.getSide(direction);

            if(side instanceof Door) {
                Door door = (Door) side;
                Lock lock = door.getLock();

                if(lock.isLocked()) {

                    return String.format("The door is locked, you need a %s to unlock it!", lock.getKey());
                } else {

                    if(lock.isOpen())
                        return move(player, room, door);
                    else
                        return "The door is not open, you need to open it first!";
                }
            }

            return "You can't move there is no door!";
        }

        private String move(Player player, Room currentRoom, Door door){

            if(door.getRoom() >= 0) {
                Room newRoom = game.getMaze().getRoom(door.getRoom());
                List<Player> players = newRoom.getPlayers();

                if(players.size() == 2) {
                    return "You can't enter the room there is a fight in progress.";

                } else if(players.size() == 1) {
                    Player otherPlayer = players.get(0);

                    // Initiate a fight
                    Fight fight = new Fight(player, otherPlayer);
                    player.setInvoker(new FightInvoker(game, player, fight));
                    otherPlayer.setInvoker(new FightInvoker(game, otherPlayer, fight));
                }

                currentRoom
                        .getPlayers()
                        .remove(player);

                player.setLocation(door.getRoom());
                players.add(player);

                return "You entered the door to the next room.";
            }

            synchronized (game) {
                if(game.getWinner() == null)
                    game.setWinner(player.getUsername());
            }

            return "You won!";
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

    private class SwitchLights implements Command {

        @Override
        public String execute(String... args) {
            Room room = game
                    .getMaze()
                    .getRoom(player.getLocation());

            if(room.hasLights()) {
                room.switchLights();

                return room.isLit() ? "The room is lit now!\n" : "The room is dark now!\n";
            }

            return "The room doesn't have lights!\n"
                    + "You should use a flashlight to see.\n";
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

    private class Rotate implements Command {
        @Override
        public String execute(String... args) {

            if(validate(args)) {
                String direction = args[0];

                if(direction.equals("left"))
                    player.setDirection(player.getDirection().left());
                else
                    player.setDirection(player.getDirection().right());

                return "Your direction now is" + player.getDirection();
            }

            return name() + " command needs one of these arguments " + args() + ".";
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

    private class Check implements Command {
        @Override
        public String execute(String... args) {

            RoomSide side = game
                    .getMaze()
                    .getRoom(player.getLocation())
                    .getSide(player.getDirection());

            return side.accept(new CheckVisitor(player));
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

    private class Trade implements Command {

        @Override
        public String execute(String... args) {

            RoomSide side = game
                    .getMaze()
                    .getRoom(player.getLocation())
                    .getSide(player.getDirection());

            if(side instanceof Seller) {
                player.setInvoker(new TradeInvoker(game, player));
                return "You are now in trade mode.";
            }

            return "You must be in front of a seller to enter trade mode.";
        }

        @Override
        public String name() {
            return "trade";
        }

        @Override
        public String description() {
            return "Enter trade mode.";
        }
    }
}
