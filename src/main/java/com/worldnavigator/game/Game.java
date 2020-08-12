package com.worldnavigator.game;

import com.worldnavigator.game.fight.Fight;
import com.worldnavigator.game.maze.Maze;
import com.worldnavigator.game.maze.Room;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Game {

    private final UUID uuid;

    private final String name;

    private final String owner;


    private String winner;

    private final Maze maze;

    private final Map<Player, Fight> fights;

    private final Map<String, Player> players;


    private final int gold;

    private final int timeout;

    private final LocalDateTime startedAt;

    public Game(UUID uuid, String owner, String name, Maze maze, int gold, int timeout, LocalDateTime startedAt) {
        this.uuid = uuid;
        this.maze = maze;
        this.name = name;
        this.owner = owner;
        this.winner = null;
        this.fights = new HashMap<>();
        this.players = new HashMap<>();

        this.gold = gold;
        this.timeout = timeout;
        this.startedAt = startedAt;
    }

    public Fight getFightByPlayer(Player player) {
        return fights.get(player);
    }

    public void addFight(Fight fight) {
        fight.getPlayers().forEach(player -> fights.put(player, fight));
    }

    public void distributePlayerGold(Player player) {

        List<Player> notLostPlayers = this.players.values().stream()
                .filter(p -> p.getMode() != PlayerMode.LOST)
                .collect(Collectors.toList());

        int goldForEachPlayer = player.getGold() / notLostPlayers.size();
        notLostPlayers.forEach(p -> p.setGold(p.getGold() + goldForEachPlayer));
    }

    public void dropPlayerItems(Player player) {
        Room room = maze.getRoom(player.getLocation());
        room.addItems(player.getItems());
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

    public boolean isFinished() {
        return winner != null || isTimeout();
    }

    public boolean isStarted() {
        return LocalDateTime.now().isAfter(startedAt);
    }

    public boolean isTimeout() {
        return startedAt.plusMinutes(timeout).isBefore(LocalDateTime.now());
    }

    public void setWinner(Player player) {

        this.players.forEach((s, p) -> p.setMode(PlayerMode.LOST));
        player.setMode(PlayerMode.WON);

        this.winner = player.getUsername();
    }
}
