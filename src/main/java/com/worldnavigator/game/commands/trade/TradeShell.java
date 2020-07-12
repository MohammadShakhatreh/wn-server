package com.worldnavigator.game.commands.trade;

import com.worldnavigator.game.commands.Input;
import com.worldnavigator.game.commands.Output;
import com.worldnavigator.game.commands.Shell;
import com.worldnavigator.game.maze.Player;
import com.worldnavigator.game.maze.room.RoomSide;
import com.worldnavigator.game.maze.room.Seller;

import java.util.LinkedHashMap;


public final class TradeShell extends Shell {

    private final Player player;

    public TradeShell(Player player, Input input, Output output) {
        super(input, output, "trade", new LinkedHashMap<>());

        this.player = player;

        addCommands(
            new BuyCommand(player, output),
            new SellCommand(player, output),
            new ListCommand(player, output)
        );
    }

    @Override
    public void execute(String... args) {

        RoomSide side = player
                .current()
                .getSide(player.getDirection());

        if(side instanceof Seller) {
            super.execute(args);
        } else {
            output.println("You must be facing a seller to enter trade mode.");
        }
    }

    @Override
    public String name() {
        return "trade";
    }

    @Override
    public String description() {
        return "Interact with the seller";
    }
}
