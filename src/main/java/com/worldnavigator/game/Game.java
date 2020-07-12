package com.worldnavigator.game;

import com.worldnavigator.game.maze.Maze;

import com.worldnavigator.game.maze.Player;
import com.worldnavigator.web.entities.Account;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Game {

    private Maze maze;

    private List<Player> players;

    private int initialGold;

    /**
     * Timeout in minutes
     */
    private int timeout;

    private boolean started;

    private LocalDateTime startedAt;

    public Game(Maze maze, int initialGold, int timeout) {
        this.maze = maze;
        this.initialGold = initialGold;
        this.timeout = timeout;

        this.started = false;
        this.startedAt = LocalDateTime.now();
        this.players = new ArrayList<>();
    }
}
