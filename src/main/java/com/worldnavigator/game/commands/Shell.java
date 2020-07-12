package com.worldnavigator.game.commands;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * This class acts as a shell like unix shells
 * it has a prompt and can accept commands
 * and execute them.
 */
public abstract class Shell implements Command {

    private final String prompt;
    private static final String PROMPT_SUFFIX = "> ";

    protected final Input input;
    protected final Output output;

    private final Map<String, Command> commands;

    private boolean done;

    public Shell(Input input,
                 Output output,
                 String prompt,
                 Map<String, Command> commands
    ) {

        this.input = Objects.requireNonNull(input);
        this.output = Objects.requireNonNull(output);
        this.prompt = Objects.requireNonNull(prompt);

        this.done = false;
        this.commands = Objects.requireNonNull(commands);

        addCommands(
                new Help(),
                new ListCommands(),
                new Exit()
        );
    }

    /**
     *
     * @param commands to be added to the shell.
     */
    public final void addCommands(Command... commands) {
        this.commands.putAll(
            Arrays
                .stream(commands)
                .collect(toMap(Command::name, identity()))
        );
    }

    /**
     * This is the shell's main loop
     * here it will get the command from the user
     * and determine which command to execute
     *
     */
    @Override
    public void execute(String... args) {

        while(!done()) {
            output.print(prompt + PROMPT_SUFFIX);

            String[] parts = read();
            String command = parts[0];
            String arguments = parts[1];

            if(command.isEmpty())
                continue;

            execute(command, arguments);
        }
    }

    private void execute(String command, String args) {

        if(commands.containsKey(command)) {

            commands
                    .get(command)
                    .execute(args.split("\\s+"));

        } else {
            output.println("There is no command with that name!");
            output.println("Type \"list-commands\" to list the commands that are available.");
        }
    }

    /**
     * Used by the while loop in the execute method of the shell.
     *
     * @return true if the shell must stop.
     */
    public boolean done() {
        return done;
    }

    /**
     * @returns a tuple of two elements
     * the first is the command name
     * and the second is it's arguments
     */
    private String[] read() {
        String[] splits = input.read()
                .trim()
                .toLowerCase()
                .split("\\s+", 2);

        if(splits.length == 1)
            return new String[] { splits[0], "" };

        return splits;
    }


    private class Help implements Command {

        @Override
        public void execute(String... args) {

            if(args.length == 1) {

                String commandName = args[0];
                if(commands.containsKey(commandName)) {
                    Command command = commands.get(commandName);

                    output.println("Usage: " + command.usage());
                    output.println(command.description());

                } else {
                    output.println("There is no command with that name!");
                }
            } else {
                output.println("Missing required argument <command-name>");
            }
        }

        @Override
        public String name() {
            return "help";
        }

        @Override
        public String args() {
            return "<command-name>";
        }

        @Override
        public String description() {
            return "Give a help message for the given command";
        }
    }

    private class ListCommands implements Command {
        @Override
        public void execute(String... args) {
            output.println("Available commands are:");
            for(Command command : commands.values())
                output.println(command.usage());
        }

        @Override
        public String name() {
            return "list-commands";
        }

        @Override
        public String description() {
            return "List all commands that can be called";
        }
    }

    private class Exit implements Command {
        @Override
        public void execute(String... args) {
            done = true;
        }

        @Override
        public String name() {
            return "exit";
        }

        @Override
        public String description() {
            return "Exits the current shell.";
        }
    }
}
