package com.worldnavigator.game.controls.commands;

import com.worldnavigator.game.Game;
import com.worldnavigator.game.Player;
import com.worldnavigator.game.PlayerMode;
import com.worldnavigator.game.controls.Command;
import com.worldnavigator.game.controls.PlayerContext;
import com.worldnavigator.game.fight.Fight;
import com.worldnavigator.game.fight.FightResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class Quit implements Command {

    private final FightResolver resolver;

    @Autowired
    public Quit(FightResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public String execute(PlayerContext context, String... args) {

        Game game = context.getGame();
        Player player = context.getPlayer();

        switch (player.getMode()) {
            case WALKING:
                player.setMode(PlayerMode.LOST);
                game.distributePlayerGold(player);
                game.dropPlayerItems(player);
                break;

            case FIGHTING:
                Fight fight = game.getFightByPlayer(player);
                Player opponent = fight.getOpponent(player);
                PlayerContext opponentContext = new PlayerContext(game, opponent);

                resolver.resolve(opponentContext, fight);
                break;
        }

        return "You are out of the game now.";
    }

    @Override
    public boolean available(PlayerContext context) {
        PlayerMode mode = context.getPlayer().getMode();
        return mode == PlayerMode.WALKING || mode == PlayerMode.FIGHTING;
    }

    @Override
    public String name() {
        return "quit";
    }

    @Override
    public String description() {
        return "Quit the game.";
    }
}
