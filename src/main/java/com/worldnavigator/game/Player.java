package com.worldnavigator.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.worldnavigator.game.commands.GameInvoker;
import com.worldnavigator.game.commands.Invoker;
import com.worldnavigator.game.maze.Direction;
import com.worldnavigator.game.maze.Maze;
import com.worldnavigator.game.maze.items.Item;
import com.worldnavigator.game.maze.room.Room;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Player {

    @EqualsAndHashCode.Include
    private String username;

    @JsonIgnore
    private Invoker invoker;

    private int gold;

    @JsonIgnore
    private int location;

    private Direction direction;

    private final Map<String, Item> items = new HashMap<>();

    public static Player of(Game game, String username) {
        Player player = new Player();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        // Pick a random direction for the player
        Direction[] directions = Direction.values();
        Direction direction =  directions[random.nextInt(directions.length)];
        player.setDirection(direction);

        // Pick a random room to spawn the player in
        Maze maze = game.getMaze();
        int randomRoomIndex = random.nextInt(maze.numberOfRooms());
        player.setLocation(randomRoomIndex);

        // Add player to the room list of players
        Room room = maze.getRoom(player.getLocation());
        room.getPlayers().add(player);

        //TODO: initiate fight mode if there is already a player in the room.
        //Or pick a room with no players.

        player.setInvoker(new GameInvoker(game, player));
        player.setGold(game.getGold());

        player.setUsername(username);
        return player;
    }

    public String execute(String line) {

        String[] parts = line
                .trim()
                .toLowerCase()
                .split("\\s+", 2);

        if(parts.length > 1)
            return invoker.execute(parts[0], parts[1].split("\\s+"));

        return invoker.execute(parts[0]);
    }

    public boolean addItem(Item item) {
        return items.putIfAbsent(item.toString(), item) == null;
    }

    public void addItems(Map<String, Item> items) {
        this.items.putAll(items);
    }
}
