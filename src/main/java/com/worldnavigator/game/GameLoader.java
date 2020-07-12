package com.worldnavigator.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worldnavigator.game.maze.Maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class GameLoader {

    private static final GameLoader loader = new GameLoader();
    private GameLoader(){}

    private Path path;

    public void setPath(Path path) {
        this.path = Objects.requireNonNull(path);
    }

    public Maze load() throws IOException {

        try(BufferedReader reader = Files.newBufferedReader(path)) {

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(reader, Maze.class);
        }
    }

    public static GameLoader getLoader() {
        return loader;
    }
}
