package com.worldnavigator.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.worldnavigator.game.maze.Maze;
import com.worldnavigator.game.maze.room.Room;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
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
        int gold = player.getGold() / players.size();
        players.forEach((s, p) -> p.setGold(p.getGold() + gold));
    }

    public void dropPlayerItems(Player player) {
        Room room = maze.getRoom(player.getLocation());
        room.addItems(player.getItems());
    }

    public void removePlayer(Player player) {
        this.players.remove(player.getUsername());
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public boolean isFinished() {
        return this.getStartedAt()
                .plusMinutes(this.getTimeout())
                .isBefore(LocalDateTime.now());
    }
}
