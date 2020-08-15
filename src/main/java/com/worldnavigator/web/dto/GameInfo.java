package com.worldnavigator.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class GameInfo {

    private final UUID uuid;

    private final String name;

    private final String owner;

    private final String winner;

    private final int playerCount;

    private final int timeout;

    private final LocalDateTime startedAt;

    private final boolean isStarted;

    private final boolean isFinished;
}
