package com.worldnavigator.game.commands;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Invoker {

    protected final Map<String, Command> commands;

    public Invoker() {
        this.commands = new LinkedHashMap<>();
    }

    public final String execute(String command, String... args) {
        if(commands.containsKey(command)) {
            return commands.get(command).execute(args);
        }

        return String.format("Unknown command (%s)!", command);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }
}
