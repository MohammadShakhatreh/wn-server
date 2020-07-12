package com.worldnavigator.game.commands;

import com.worldnavigator.game.GameLoader;
import com.worldnavigator.game.commands.global.GlobalShell;
import com.worldnavigator.game.maze.Maze;
import com.worldnavigator.game.maze.Player;

import java.io.IOException;
import java.util.LinkedHashMap;

public final class GameShell extends Shell {

    public GameShell(Input input, Output output) {
        super(input, output, "menu", new LinkedHashMap<>());

        addCommands(
                new Start()
        );
    }

    @Override
    public void execute(String... args) {

        output.println("Welcome to World Navigator!");
        output.println("To list the available commands and see how to use them");
        output.println("Type \"list-commands\" or \"help <command-name>\"");
        output.println("");

        super.execute(args);
    }

    @Override
    public String name() {
        return "game";
    }

    @Override
    public String description() {
        return "Controls the game like starting a new game or continue the current game.";
    }

    private class Start implements Command {

        @Override
        public void execute(String... args) {

            try {

                Maze maze = GameLoader.getLoader().load();
                Player player = maze.player();

                new GlobalShell(
                        player,
                        input,
                        output
                ).execute();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String name() {
            return "start";
        }

        @Override
        public String description() {
            return "Start a new game.";
        }
    }
}
