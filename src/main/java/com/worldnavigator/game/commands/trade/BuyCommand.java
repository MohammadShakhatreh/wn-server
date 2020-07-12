package com.worldnavigator.game.commands.trade;

import com.worldnavigator.game.commands.Command;
import com.worldnavigator.game.commands.Output;
import com.worldnavigator.game.maze.Player;
import com.worldnavigator.game.maze.items.Item;
import com.worldnavigator.game.maze.items.ItemFactory;
import com.worldnavigator.game.maze.items.NoSuchItemException;
import com.worldnavigator.game.maze.room.Seller;

public final class BuyCommand implements Command {

    private final Player player;
    private final Output output;

    public BuyCommand(Player player, Output output) {
        this.player = player;
        this.output = output;
    }

    @Override
    public void execute(String... args) {
        Seller seller = (Seller) player
                .current()
                .getSide(player.getDirection());

        String item = String.join(" ", args);

        if(seller.getPrices().containsKey(item)) {

            int price = seller.getItemPrice(item);

            if(player.getItems().containsKey(item)) {
                output.println("You already have this item!");

            } else if(player.getGold() >= price) {
                addItemToPlayer(item, player);
                player.setGold(player.getGold() - price);

            } else {
                output.println(String.format("You don't have enough gold to buy the %s!", item));
            }

        } else {
            output.println("The seller doesn't have this item!");
            output.println("Try \"list\" command to see whats available.");
        }
    }

    private void addItemToPlayer(String name, Player player) {
        try {
            Item item = ItemFactory.getFactory().valueOf(name);
            player.addItem(item);

        } catch (NoSuchItemException e) {
            output.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String name() {
        return "buy";
    }

    @Override
    public String args() {
        return "<item>";
    }

    @Override
    public String description() {
        return "Buy an item from the seller";
    }
}