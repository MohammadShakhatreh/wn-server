package com.worldnavigator.game.fight;

import com.worldnavigator.game.Game;
import com.worldnavigator.game.Player;
import com.worldnavigator.game.PlayerMode;
import com.worldnavigator.game.controls.PlayerContext;
import com.worldnavigator.game.maze.Room;
import org.springframework.stereotype.Component;

@Component
public class FightResolver {

    public synchronized String status(PlayerContext context, Fight fight) {
        Player player = context.getPlayer();

        switch (fight.status(player)) {
            case WON:
                resolve(context, fight);
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

    /**
     * This method must be called with the winner context
     * to resolve the fight.
     *
     * @param context winner player context
     */
    public synchronized void resolve(PlayerContext context, Fight fight) {
        Game game = context.getGame();
        Player player = context.getPlayer();
        Player opponent = fight.getOpponent(player);

        player.setMode(PlayerMode.WALKING);
        opponent.setMode(PlayerMode.LOST);

        player.addItems(opponent.getItems());
        game.distributePlayerGold(opponent);

        Room room = context.getCurrentRoom();
        room.removePlayer(opponent);
    }
}
