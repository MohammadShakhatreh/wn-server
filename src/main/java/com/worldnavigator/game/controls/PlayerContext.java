package com.worldnavigator.game.controls;

import com.worldnavigator.game.Game;
import com.worldnavigator.game.Player;
import com.worldnavigator.game.maze.room.Room;
import com.worldnavigator.game.maze.room.RoomSide;

public final class PlayerContext {

    private final Game game;

    private final Player player;

    public PlayerContext(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public Room getCurrentRoom() {
        return game.getMaze().getRoom(player.getLocation());
    }

    public RoomSide getCurrentRoomSide() {
        return getCurrentRoom().getSide(player.getDirection());
    }
}
