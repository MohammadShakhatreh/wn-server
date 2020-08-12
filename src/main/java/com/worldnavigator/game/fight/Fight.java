package com.worldnavigator.game.fight;

import com.worldnavigator.game.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.worldnavigator.game.fight.FightStatus.*;

public class Fight {
    private final Map<Player, Hand> hands;

    public Fight(Player firstPlayer, Player secondPlayer) {

        hands = new HashMap<>();
        hands.put(firstPlayer, Hand.NONE);
        hands.put(secondPlayer, Hand.NONE);
    }

    /**
     *
     * @param player that want to play a hand
     * @param hand that the player want to play
     * @return true if the player hadn't played before
     */
    public boolean play(Player player, Hand hand) {
        if(hands.get(player) == Hand.NONE) {
            hands.put(player, hand);
            return true;
        }

        return false;
    }

    public FightStatus status(Player player) {
        Player opponent = getOpponent(player);

        Hand playerHand = hands.get(player);
        Hand opponentHand = hands.get(opponent);

        if(playerHand == Hand.NONE)
            return WAIT_SELF;

        else if(opponentHand == Hand.NONE)
            return WAIT_OTHER;

        else if(playerHand == opponentHand)
            return TIE;

        else if(playerHand.beats(opponentHand))
            return WON;

        return LOST;
    }

    public void reset() {
        hands.replaceAll((player, hand) -> Hand.NONE);
    }

    public Player getOpponent(Player player) {
        return hands.keySet().stream()
                .filter(p -> !player.equals(p))
                .findFirst()
                .orElseThrow();
    }

    public Set<Player> getPlayers() {
        return hands.keySet();
    }
}
