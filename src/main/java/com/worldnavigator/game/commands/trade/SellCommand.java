package com.worldnavigator.game.commands.trade;

import com.worldnavigator.game.commands.Command;
import com.worldnavigator.game.commands.Output;
import com.worldnavigator.game.maze.Player;
import com.worldnavigator.game.maze.room.Seller;

public final class SellCommand implements Command {

    private final Player player;
    private final Output output;

    public SellCommand(Player player, Output output) {
        this.player = player;
        this.output = output;
    }

    @Override
    public void execute(String... args) {
        Seller seller = (Seller) player.current()
                .getSide(player.getDirection());

        String item = String.join(" ", args);

        if(player.getItems().containsKey(item)) {

            if(seller.getPrices().containsKey(item)) {

                player.setGold(player.getGold() + seller.getItemPrice(item));
                player.getItems().remove(item);

            } else {
                output.println(String.format("The seller doesn't have a %s on it's prices list.", item));
            }

        } else {
            output.println("You don't have this item.");
        }
    }

    @Override
    public String name() {
        return "sell";
    }

    @Override
    public String args() {
        return "<item>";
    }

    @Override
    public String description() {
        return "Sells an item that you have";
    }
}
