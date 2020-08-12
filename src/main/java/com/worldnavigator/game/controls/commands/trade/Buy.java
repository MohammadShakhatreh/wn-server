package com.worldnavigator.game.controls.commands.trade;

import com.worldnavigator.game.Player;
import com.worldnavigator.game.controls.Command;
import com.worldnavigator.game.controls.PlayerContext;
import com.worldnavigator.game.maze.items.Item;
import com.worldnavigator.game.maze.items.ItemFactory;
import com.worldnavigator.game.maze.items.NoSuchItemException;
import com.worldnavigator.game.maze.roomsides.RoomSide;
import com.worldnavigator.game.maze.roomsides.Seller;
import org.springframework.stereotype.Component;

@Component
public final class Buy implements Command {

    @Override
    public boolean validate(PlayerContext context, String... args) {
        return args.length >= 1;
    }

    @Override
    public String execute(PlayerContext context, String... args) {
        String description = String.join(" ", args);

        Player player = context.getPlayer();
        Seller seller = (Seller) context.getCurrentRoomSide();

        if(seller.getPrices().containsKey(description)) {

            int price = seller.getItemPrice(description);

            if(player.getItems().containsKey(description)) {
                return "You already have this item!";

            } else if(player.getGold() >= price) {

                addItemToPlayer(description, player);
                player.setGold(player.getGold() - price);
                return "You got it!";

            } else {
                return String.format("You don't have enough gold to buy the %s!%n", description);
            }

        } else {
            return "The seller doesn't have this item! try (trade:list) command to see whats available.";
        }
    }

    private void addItemToPlayer(String name, Player player) {
        try {
            Item item = ItemFactory.getFactory().valueOf(name);
            player.addItem(item);

        } catch (NoSuchItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean available(PlayerContext context) {
        RoomSide side = context.getCurrentRoomSide();
        return side instanceof Seller;
    }

    @Override
    public String name() {
        return "trade:buy";
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
