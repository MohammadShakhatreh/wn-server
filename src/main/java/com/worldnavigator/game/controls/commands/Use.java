package com.worldnavigator.game.controls.commands;

import com.worldnavigator.game.Player;
import com.worldnavigator.game.PlayerMode;
import com.worldnavigator.game.controls.Command;
import com.worldnavigator.game.controls.PlayerContext;
import com.worldnavigator.game.controls.visitors.UseVisitor;
import com.worldnavigator.game.maze.items.Item;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public final class Use implements Command {
    @Override
    public boolean validate(PlayerContext context, String... args) {
        return args.length >= 1;
    }

    @Override
    public String execute(PlayerContext context, String... args) {

        String description = String.join(" ", args);

        Player player = context.getPlayer();
        Map<String, Item> items = player.getItems();

        Item item = items.get(description);

        if(item != null)
            return item.accept(new UseVisitor(context.getCurrentRoomSide()));

        return "You don't have an item with that name!";
    }

    @Override
    public boolean available(PlayerContext context) {
        Player player = context.getPlayer();
        return player.getMode() == PlayerMode.WALKING;
    }

    @Override
    public String name() {
        return "use";
    }

    @Override
    public String args() {
        return "<item>";
    }

    @Override
    public String description() {
        return "Uses an item, for example using a flashlight would turn it on or off.";
    }
}
