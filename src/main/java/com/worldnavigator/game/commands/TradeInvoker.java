package com.worldnavigator.game.commands;

import com.worldnavigator.game.Game;
import com.worldnavigator.game.Player;
import com.worldnavigator.game.maze.items.Item;
import com.worldnavigator.game.maze.items.ItemFactory;
import com.worldnavigator.game.maze.items.NoSuchItemException;
import com.worldnavigator.game.maze.room.Seller;

import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public final class TradeInvoker extends Invoker {

    private final Game game;
    private final Player player;

    private final Seller seller;

    public TradeInvoker(Game game, Player player) {
        this.game = game;
        this.player = player;
        this.commands.putAll(
                Stream.of(
                        new BuyCommand(),
                        new SellCommand(),
                        new ListCommand(),
                        new Finish()
                )
                .collect(toMap(Command::name, identity()))
        );

        this.seller = (Seller) game
                .getMaze()
                .getRoom(player.getLocation())
                .getSide(player.getDirection());
    }

    public final class BuyCommand implements Command {

        @Override
        public String execute(String... args) {
            String item = String.join(" ", args);

            StringBuilder sb = new StringBuilder();
            if(seller.getPrices().containsKey(item)) {

                int price = seller.getItemPrice(item);

                if(player.getItems().containsKey(item)) {
                    sb.append("You already have this item!\n");

                } else if(player.getGold() >= price) {
                    addItemToPlayer(item);
                    player.setGold(player.getGold() - price);
                    sb.append("You got it!");

                } else {
                    sb.append(String.format("You don't have enough gold to buy the %s!%n", item));
                }

            } else {
                sb.append("The seller doesn't have this item!\n")
                        .append("Try \"list\" command to see whats available.\n");
            }

            return sb.toString();
        }

        private void addItemToPlayer(String name) {
            try {
                Item item = ItemFactory.getFactory().valueOf(name);
                player.addItem(item);

            } catch (NoSuchItemException e) {
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

    public final class ListCommand implements Command {

        @Override
        public String execute(String... args) {
            StringBuilder result = new StringBuilder();
            result.append("Available Items: \n");

            for(var entry : seller.getPrices().entrySet())
                result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");

            return result.toString();
        }

        @Override
        public String name() {
            return "list";
        }

        @Override
        public String description() {
            return "Lists the items that are available to sell or buy.";
        }
    }

    public final class SellCommand implements Command {

        @Override
        public String execute(String... args) {

            var items = player.getItems();
            String item = String.join(" ", args);

            if(items.containsKey(item)) {

                if(seller.getPrices().containsKey(item)) {

                    player.setGold(player.getGold() + seller.getItemPrice(item));
                    items.remove(item);

                    return String.format("The (%s) is sold!", item);
                }

                return String.format("The seller doesn't have a %s in it's prices list.%n", item);
            }

            return "You don't have this item.\n";
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

    private final class Finish implements Command {

        @Override
        public String execute(String... args) {
            player.setInvoker(new GameInvoker(game, player));
            return "You are out of trade mode.";
        }

        @Override
        public String name() {
            return "finish";
        }

        @Override
        public String description() {
            return "Exit trade mode.";
        }
    }
}
