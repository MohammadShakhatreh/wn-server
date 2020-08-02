package com.worldnavigator.game.maze.items;

import java.util.Objects;

public final class Key extends Item {
    private final String color;

    public Key(String color) {
        this.color = Objects.requireNonNull(color);
    }

    @Override
    public String accept(ItemVisitor visitor) {
        return visitor.execute(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return color.equals(key.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }

    /**
     * The string format that this method returns is
     * "color key" where color is substituted by the key's color.
     *
     * @return string representation of the key.
     */
    @Override
    public String toString() {
        return color + " key";
    }
}
