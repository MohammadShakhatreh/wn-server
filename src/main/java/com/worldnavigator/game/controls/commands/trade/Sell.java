package com.worldnavigator.game.controls.commands.trade;

import com.worldnavigator.game.Player;
import com.worldnavigator.game.controls.Command;
import com.worldnavigator.game.controls.PlayerContext;
import com.worldnavigator.game.maze.items.Item;
import com.worldnavigator.game.maze.roomsides.RoomSide;
import com.worldnavigator.game.maze.roomsides.Seller;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public final class Sell implements Command {

    @Override
    public boolean validate(PlayerContext context, String... args) {
        return args.length >= 1;
    }

    @Override
    public String execute(PlayerContext context, String... args) {
        String description = String.join(" ", args);

        Player player = context.getPlayer();
        Seller seller = (Seller) context.getCurrentRoomSide();

        Map<String, Item> items = player.getItems();
        Map<String, Integer> prices = seller.getPrices();

        if(items.containsKey(description)) {

            if(prices.containsKey(description)) {

                player.setGold(player.getGold() + seller.getItemPrice(description));
                items.remove(description);

                return String.format("The (%s) is sold!", description);
            }

            return String.format("The seller doesn't have a %s in it's prices list.", description);
        }

        return "You don't have this item to sell.";
    }

    @Override
    public boolean available(PlayerContext context) {
        RoomSide side = context.getCurrentRoomSide();
        return side instanceof Seller;
    }

    @Override
    public String name() {
        return "trade:sell";
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
