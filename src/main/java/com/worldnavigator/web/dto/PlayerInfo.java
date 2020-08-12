package com.worldnavigator.web.dto;

import com.worldnavigator.game.PlayerMode;
import com.worldnavigator.game.maze.Direction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class PlayerInfo {

    private final PlayerMode mode;

    private final String username;

    private final int gold;

    private final int location;

    private final Direction direction;

    private final Set<String> items;
}
