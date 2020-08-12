package com.worldnavigator.web.mappers;

import com.worldnavigator.game.Game;
import com.worldnavigator.game.Player;
import com.worldnavigator.web.dto.PlayerInfo;
import com.worldnavigator.web.dto.GameInfo;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public GameInfo toGameInfo(Game game) {
        return new GameInfo(
                game.getUuid(),
                game.getName(),
                game.getOwner(),
                game.getWinner(),
                game.getPlayers().size(),
                game.getTimeout(),
                game.getStartedAt()
        );
    }

    public PlayerInfo toPlayerInfo(Player player) {

        return new PlayerInfo(
                player.getMode(),
                player.getUsername(),
                player.getGold(),
                player.getLocation(),
                player.getDirection(),
                player.getItems().keySet()
        );
    }
}
