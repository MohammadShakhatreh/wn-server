package com.worldnavigator.game.commands;

import com.worldnavigator.game.Game;
import com.worldnavigator.game.Player;
import com.worldnavigator.game.maze.items.Flashlight;
import com.worldnavigator.game.maze.items.ItemVisitor;
import com.worldnavigator.game.maze.items.Key;
import com.worldnavigator.game.maze.room.Lock;
import com.worldnavigator.game.maze.room.Lockable;
import com.worldnavigator.game.maze.room.RoomSide;

public final class UseVisitor implements ItemVisitor {

    private final Game game;

    private final Player player;

    public UseVisitor(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    @Override
    public String execute(Key key) {

        RoomSide side = game
                .getMaze()
                .getRoom(player.getLocation())
                .getSide(player.getDirection());


        if(side instanceof Lockable) {

            Lockable lockable = (Lockable) side;
            Lock lock = lockable.getLock();


            if(lock.isLocked()) {

                if(lock.unlock(key)) {
                    return String.format("The %s is now unlocked!", lockable);
                } else {
                    return String.format("The key can't be used on this %s!", lockable);
                }

            } else {

                if(lock.lock(key)) {
                    return String.format("The %s is now locked!", lockable);
                } else {
                    return String.format("The key can't be used on this %s!", lockable);
                }
            }
        }

        return "This is not something you can open!";
    }

    @Override
    public String execute(Flashlight flashlight) {
        flashlight.setOn(!flashlight.isOn());

        if(flashlight.isOn())
            return "The flashlight is on now!";
        else
            return "The flashlight is off now!";
    }
}