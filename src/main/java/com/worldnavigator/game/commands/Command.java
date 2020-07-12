package com.worldnavigator.game.commands;

public interface Command {

    /**
     *
     * @param args The arguments the command takes
     */
    void execute(String...args);

    /**
     * Command name used to execute it.
     *
     * @return the name of the command
     */
    String name();

    /**
     *
     * @return A description of the command arguments or empty
     * if it doesn't require any.
     */
    default String args() {
        return "";
    }

    /**
     * The format of the usage string is
     * the name of the command and it's arguments
     *
     * For example: "move &lt;forward|backward&gt;"
     * @return how to call the command
     */
    default String usage() {
        String arguments = args();

        if(arguments == null || arguments.isEmpty())
            return name();

        return name() + " " + arguments;
    }

    /**
     *
     * @return What the command will do if executed
     */
    String description();
}
