package com.worldnavigator.game.controls;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface Command {

    /**
     * Used to see if the arguments are valid and correct.
     *
     * This method should be called before the execute method.
     *
     * @param context context of the player in the game.
     * @param args arguments for the giving command.
     * @return whether the arguments are valid or not.
     */
    default boolean validate(PlayerContext context, String ...args) {
        return true;
    }

    /**
     * execute the command and return a string describing what happened.
     * @param args arguments the command takes
     */
    String execute(PlayerContext context, String...args);

    /**
     * Used to see if the command is available on the giving context.
     *
     * For example if the player is in front of a painting the command open
     * is not available.
     *
     * @param context context of the player in the game.
     * @return whether the command is applicable on the giving context or not.
     */
    default boolean available(PlayerContext context) {
        return true;
    }

    /**
     * Command name used to execute it.
     *
     * @return the name of the command
     */
    @JsonProperty("name")
    String name();

    /**
     *
     * @return A description of the command arguments or empty
     * if it doesn't require any.
     */
    @JsonProperty("arguments")
    default String args() {
        return "";
    }

    /**
     *
     * @return What the command will do if executed
     */
    @JsonProperty("description")
    String description();
}
