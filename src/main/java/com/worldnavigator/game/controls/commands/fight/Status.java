package com.worldnavigator.game.controls.commands.fight;

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
public class Status implements Command {

    private final FightResolver resolver;

    @Autowired
    public Status(FightResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public String execute(PlayerContext context, String... args) {

        Game game = context.getGame();
        Fight fight = game.getFightByPlayer(context.getPlayer());

        return resolver.resolve(context, fight);
    }

    @Override
    public boolean available(PlayerContext context) {
        Player player = context.getPlayer();
        return player.getMode() == PlayerMode.FIGHTING;
    }

    @Override
    public String name() {
        return "fight:status";
    }

    @Override
    public String description() {
        return "Gives fight status.";
    }
}
