package com.worldnavigator.game.maze.items;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonDeserialize(using = ItemDeserializer.class)
public abstract class Item {

    public abstract String accept(ItemVisitor visitor);

    /**
     * This method made is abstract to make all items
     * have a description and every item must have a format
     * for the description that is enough to reconstruct the item from it
     *
     * @return The description of the item
     */
    @Override
    public abstract String toString();
}
