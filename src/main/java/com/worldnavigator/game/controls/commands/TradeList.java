package com.worldnavigator.game.controls.commands;

import com.worldnavigator.game.controls.Command;
import com.worldnavigator.game.controls.PlayerContext;
import com.worldnavigator.game.maze.room.Seller;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public final class TradeList implements Command {

    @Override
    public boolean validate(PlayerContext context, String... args) {
        return true;
    }

    @Override
    public String execute(PlayerContext context, String... args) {

        Seller seller = (Seller) context.getCurrentRoomSide();

        StringBuilder sb = new StringBuilder();
        Map<String, Integer> prices = seller.getPrices();

        sb.append("Available items:\n");
        prices.forEach(
                (item, price) ->
                        sb.append(item).append(": ").append(price).append("\n")
        );

        return sb.toString();
    }

    @Override
    public boolean available(PlayerContext context) {
        return context.getCurrentRoomSide() instanceof Seller;
    }

    @Override
    public String name() {
        return "trade:list";
    }

    @Override
    public String description() {
        return "List all prices of the seller";
    }
}
