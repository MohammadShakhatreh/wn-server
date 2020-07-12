package com.worldnavigator.game;

import com.worldnavigator.game.commands.GameShell;
import com.worldnavigator.game.commands.StandardInput;
import com.worldnavigator.game.commands.StandardOutput;

import java.nio.file.Paths;

public class MazeGame {

    public void start(String path) {

        GameLoader
                .getLoader()
                .setPath(Paths.get(path));

        new GameShell(
                new StandardInput(),
                new StandardOutput()
        ).execute();
    }
}
