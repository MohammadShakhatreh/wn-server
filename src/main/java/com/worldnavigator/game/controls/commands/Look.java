package com.worldnavigator.game.controls.commands;

import com.worldnavigator.game.controls.Command;
import com.worldnavigator.game.controls.visitors.LookVisitor;
import com.worldnavigator.game.controls.PlayerContext;
import com.worldnavigator.game.maze.items.Flashlight;
import com.worldnavigator.game.maze.items.Item;
import com.worldnavigator.game.maze.room.Room;
import com.worldnavigator.game.maze.room.RoomSide;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Look implements Command {

    @Override
    public boolean validate(PlayerContext context, String... args) {
        return true;
    }

    @Override
    public String execute(PlayerContext context, String... args) {

        Room room = context.getCurrentRoom();
        RoomSide side = context.getCurrentRoomSide();

        Map<String, Item> items = context.getPlayer().getItems();

        if(room.isLit()
            || (items.containsKey("flashlight")
                && ((Flashlight) items.get("flashlight")).isOn())
        ) {
            return side.accept(new LookVisitor());
        }

        return "The room is dark!";
    }

    @Override
    public boolean available(PlayerContext context) {
        return true;
    }

    @Override
    public String name() {
        return "look";
    }

    @Override
    public String description() {
        return "Gives a description of whats in front of you";
    }
}
