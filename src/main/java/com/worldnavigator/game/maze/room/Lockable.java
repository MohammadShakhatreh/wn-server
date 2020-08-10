package com.worldnavigator.game.maze.room;

import java.util.Optional;

public interface Lockable {

    Optional<Lock> getLock();
}
