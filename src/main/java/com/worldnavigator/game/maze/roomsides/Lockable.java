package com.worldnavigator.game.maze.roomsides;

import java.util.Optional;

public interface Lockable {

    Optional<Lock> getLock();
}
