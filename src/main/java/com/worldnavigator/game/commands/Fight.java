package com.worldnavigator.game.commands;

import com.worldnavigator.game.Player;

import java.util.HashMap;
import java.util.Map;

public class Fight {
    private final Map<Player, Hand> hands;

    public Fight(Player firstPlayer, Player secondPlayer) {

        hands = new HashMap<>();
        hands.put(firstPlayer, Hand.NONE);
        hands.put(secondPlayer, Hand.NONE);
    }

    public boolean play(Player player, Hand hand) {
        if(hands.get(player) == Hand.NONE) {
            hands.put(player, hand);
            return true;
        }

        return false;
    }

    public void reset() {
        hands.replaceAll((player, hand) -> Hand.NONE);
    }

    public FightStatus getPlayerStatus(Player player) {
        Player opponent = getOpponent(player);

        Hand playerHand = hands.get(player);
        Hand opponentHand = hands.get(opponent);

        if(playerHand == Hand.NONE || opponentHand == Hand.NONE)
            return FightStatus.WAIT;

        if(playerHand == opponentHand)
            return FightStatus.TIE;

        if(playerHand.beats(opponentHand))
            return FightStatus.WON;

        return FightStatus.LOST;
    }

    public Player getOpponent(Player player) {
        return hands.keySet().stream()
                .filter(p -> !player.equals(p))
                .findFirst()
                .orElseThrow();
    }
}
