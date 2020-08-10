package com.worldnavigator.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.worldnavigator.game.maze.Maze;
import com.worldnavigator.game.maze.room.Room;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@Getter
@Setter
public class Game {

    private final UUID uuid;

    private final String name;

    @JsonIgnore
    private final Maze maze;

    private final String owner;

    @JsonIgnore
    private final Map<String, Player> players;

    private final int gold;

    private final int timeout;

    private final LocalDateTime startedAt;

    private String winner;

    public Game(UUID uuid, String owner, String name, Maze maze, int gold, int timeout, LocalDateTime startedAt) {
        this.uuid = uuid;
        this.maze = maze;
        this.name = name;
        this.owner = owner;
        this.winner = null;
        this.players = new HashMap<>();

        this.gold = gold;
        this.timeout = timeout;
        this.startedAt = startedAt;
    }

    public void distributePlayerGold(Player player) {
        int goldForEachPlayer = player.getGold() / players.size();
        players.forEach((s, p) -> p.setGold(p.getGold() + goldForEachPlayer));
    }

    public void dropPlayerItems(Player player) {
        Room room = maze.getRoom(player.getLocation());
        room.addItems(player.getItems());
    }

    public void removePlayer(Player player) {
        this.players.remove(player.getUsername());
    }

    /**
     * Get a player using the player username
     * @param username player username
     * @return player
     * @throws NoSuchElementException if no player with this username exists.
     */
    public Player getPlayer(String username) {
        Player player = players.get(username);

        if(player == null)
            throw new NoSuchElementException(String.format("There is no player with username (%s)!", username));

        return player;
    }

    /**
     * See if the game has finished or not depending on if there is a winner or
     * the game has timed out.
     *
     */
    public boolean isFinished() {
        return winner != null
                || startedAt.plusMinutes(timeout).isBefore(LocalDateTime.now());
    }

    /**
     * See if The has started or not.
     *
     */
    public boolean isStarted() {
        return LocalDateTime.now().isAfter(startedAt);
    }
}
