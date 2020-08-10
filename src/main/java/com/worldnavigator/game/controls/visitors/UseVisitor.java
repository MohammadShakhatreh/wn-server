package com.worldnavigator.game.controls.visitors;

import com.worldnavigator.game.maze.items.Flashlight;
import com.worldnavigator.game.maze.items.ItemVisitor;
import com.worldnavigator.game.maze.items.Key;
import com.worldnavigator.game.maze.room.Lock;
import com.worldnavigator.game.maze.room.Lockable;
import com.worldnavigator.game.maze.room.RoomSide;

import java.util.Optional;

public final class UseVisitor implements ItemVisitor {

    private final RoomSide side;

    public UseVisitor(RoomSide side) {
        this.side = side;
    }

    @Override
    public String execute(Key key) {

        if (side instanceof Lockable) {

            Lockable lockable = (Lockable) side;
            Optional<Lock> lock = lockable.getLock();

            if (lock.isPresent())
                return useKeyOnLock(key, lock.get());
            else
                return String.format("There is no lock on the %s!", side);

        } else {
            return "This is not something you can open!";
        }
    }

    private String useKeyOnLock(Key key, Lock lock) {
        if (lock.isLocked()) {

            if (lock.unlock(key))
                return String.format("The %s is now unlocked!", side);
            else
                return String.format("The key can't be used on this %s!", side);

        } else {

            if (lock.lock(key))
                return String.format("The %s is now locked!", side);
            else
                return String.format("The key can't be used on this %s!", side);
        }
    }

    @Override
    public String execute(Flashlight flashlight) {

        flashlight.flip();

        if (flashlight.isOn())
            return "The flashlight is on now!";
        else
            return "The flashlight is off now!";
    }
}