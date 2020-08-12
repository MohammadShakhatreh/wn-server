package com.worldnavigator.game.controls.commands;

import com.worldnavigator.game.Player;
import com.worldnavigator.game.PlayerMode;
import com.worldnavigator.game.controls.Command;
import com.worldnavigator.game.controls.PlayerContext;
import com.worldnavigator.game.maze.roomsides.Lock;
import com.worldnavigator.game.maze.roomsides.Lockable;
import com.worldnavigator.game.maze.roomsides.RoomSide;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Open implements Command {

    @Override
    public String execute(PlayerContext context, String... args) {

        Lockable lockable = (Lockable) context.getCurrentRoomSide();

        Optional<Lock> optional = lockable.getLock();

        if (optional.isPresent()) {

            Lock lock = optional.get();

            if (lock.isLocked()) {
                return String.format("The %s is locked, you need a %s to unlock it!", lockable, lock.getKey());

            } else {

                if (lock.isOpen()) {
                    return String.format("The %s is already open!", lockable);
                } else {
                    lock.open();
                    return String.format("The %s is now open!", lockable);
                }
            }

        } else {
            return String.format("The %s doesn't have a lock!", lockable);
        }
    }

    @Override
    public boolean available(PlayerContext context) {
        Player player = context.getPlayer();
        RoomSide side = context.getCurrentRoomSide();

        return player.getMode() == PlayerMode.WALKING
                && side instanceof Lockable;
    }

    @Override
    public String name() {
        return "open";
    }

    @Override
    public String description() {
        return "If you are in front of an unlocked door/chest it will open it";
    }
}
