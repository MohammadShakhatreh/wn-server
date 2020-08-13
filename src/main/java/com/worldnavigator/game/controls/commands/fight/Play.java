package com.worldnavigator.game.controls.commands.fight;

import com.worldnavigator.game.Game;
import com.worldnavigator.game.Player;
import com.worldnavigator.game.PlayerMode;
import com.worldnavigator.game.controls.Command;
import com.worldnavigator.game.controls.PlayerContext;
import com.worldnavigator.game.fight.Fight;
import com.worldnavigator.game.fight.FightResolver;
import com.worldnavigator.game.fight.Hand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class Play implements Command {

    private final FightResolver resolver;

    @Autowired
    public Play(FightResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public boolean validate(PlayerContext context, String... args) {
        return args.length == 1
                && ("rock".startsWith(args[0])
                || "paper".startsWith(args[0])
                || "scissor".startsWith(args[0]));
    }

    @Override
    public String execute(PlayerContext context, String... args) {

        Hand hand = getHand(args[0]);

        Game game = context.getGame();
        Player player = context.getPlayer();

        Fight fight = game.getFightByPlayer(player);

        if (fight.play(player, hand))
            return resolver.status(context, fight);

        return "You already played your turn, you can't change your hand now!";
    }

    private Hand getHand(String name) {
        if("rock".startsWith(name))
            return Hand.ROCK;

        else if("paper".startsWith(name))
            return Hand.PAPER;

        else if("scissor".startsWith(name))
            return Hand.SCISSOR;

        return Hand.NONE;
    }

    @Override
    public boolean available(PlayerContext context) {
        Player player = context.getPlayer();
        return player.getMode() == PlayerMode.FIGHTING;
    }

    @Override
    public String name() {
        return "fight:play";
    }

    @Override
    public String args() {
        return "<rock|paper|scissor>";
    }

    @Override
    public String description() {
        return "Plays a hand in a rock paper scissor fight.";
    }
}
