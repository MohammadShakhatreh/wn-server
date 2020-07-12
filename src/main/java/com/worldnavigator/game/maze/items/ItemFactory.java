package com.worldnavigator.game.maze.items;

public class ItemFactory {

    private static final ItemFactory factory = new ItemFactory();
    private ItemFactory(){}

    /**
     *
     * @param s The description of the item.
     * @return item instance based on the description
     * @throws NoSuchItemException if the description can't be converted to an item.
     */
    public Item valueOf(String s) throws NoSuchItemException {
        s = s.trim().toLowerCase();

        if(s.equals("flashlight")) {
            return new Flashlight();

        } else if(s.endsWith(" key")) {
            return new Key(s.split("\\s+")[0]);
        }

        throw new NoSuchItemException("There are no items with such description!");
    }

    public static ItemFactory getFactory() {
        return factory;
    }
}
