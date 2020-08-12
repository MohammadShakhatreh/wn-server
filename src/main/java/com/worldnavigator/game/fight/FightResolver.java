package com.worldnavigator.game.fight;

import com.worldnavigator.game.Game;
import com.worldnavigator.game.Player;
import com.worldnavigator.game.PlayerMode;
import com.worldnavigator.game.controls.PlayerContext;
import org.springframework.stereotype.Component;

@Component
public class FightResolver {

    public synchronized String resolve(PlayerContext context, Fight fight) {
        Player player = context.getPlayer();

        switch (fight.status(player)) {
            case WON:
                resolve(context, fight.getOpponent(player));
                return "You won the fight!";

            case LOST:
                return "You lost the fight!";

            case TIE:
                fight.reset();
                return "There is a tie play again!";

            case WAIT_SELF:
                return "Waiting for you to play.";

            case WAIT_OTHER:
                return "Waiting for your opponent to play";

            default:
                return "Something went wrong!";
        }
    }

    private void resolve(PlayerContext context, Player loser) {
        Game game = context.getGame();
        Player player = context.getPlayer();

        player.addItems(loser.getItems());
        game.distributePlayerGold(loser);
        loser.setMode(PlayerMode.LOST);
    }
}
