package com.worldnavigator.game.maze.items;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;


public final class Flashlight extends Item {

    @JsonIgnore
    private boolean isOn;

    @JsonCreator
    public Flashlight() {
        this.isOn = false;
    }

    @Override
    public String accept(ItemVisitor visitor) {
        return visitor.execute(this);
    }

    public void flip() {
        this.isOn = !isOn;
    }

    public boolean isOn() {
        return isOn;
    }

    /**
     * Returns the string representation of the flashlight
     * which is the string "flashlight" as all flashlight are the same.
     *
     */
    @Override
    public String toString() {
        return "flashlight";
    }
}
