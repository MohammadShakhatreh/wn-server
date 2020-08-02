package com.worldnavigator.game.commands;

import com.worldnavigator.game.Game;
import com.worldnavigator.game.Player;

import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class FightInvoker extends Invoker {

    private final Game game;
    private final Fight fight;
    private final Player player;

    private final Invoker previousInvoker;

    public FightInvoker(Game game, Player player, Fight fight) {
        this.game = game;
        this.fight = fight;
        this.player = player;
        this.previousInvoker = player.getInvoker();

        this.commands.putAll(
                Stream.of(
                    new Play(),
                    new Status()
                )
                .collect(toMap(Command::name, identity()))
        );
    }

    private String fightStatus() {
        FightStatus playerStatus = fight.getPlayerStatus(player);

        switch (playerStatus) {
            case WON:
                player.setInvoker(previousInvoker);
                return "You won!";

            case LOST:
                game.removePlayer(player);
                game.distributePlayerGold(player);
                Player opponent = fight.getOpponent(player);
                opponent.addItems(player.getItems());
                return "You lost!";

            case TIE:
                fight.reset();
                return "There is a tie play again!";

            case WAIT:
                return "Waiting for your opponent to play";

            default:
                return "Something went wrong!";
        }
    }

    private class Play implements Command {

        @Override
        public String execute(String... args) {
            if(args.length == 0)
                return "This command needs an argument.";

            try {
                Hand hand = Hand.valueOf(args[0].toUpperCase());

                if(fight.play(player, hand)) {
                    return fightStatus();

                } else {
                    return "You already played your turn!";
                }

            } catch (IllegalArgumentException e) {
                return "The argument must be one of these " + args() + ".";
            }
        }

        @Override
        public String name() {
            return "play";
        }

        @Override
        public String args() {
            return "<rock|paper|scissor>";
        }

        @Override
        public String description() {
            return "Play your hand!";
        }
    }

    private class Status implements Command {
        @Override
        public String execute(String... args) {
            return fightStatus();
        }

        @Override
        public String name() {
            return "status";
        }

        @Override
        public String description() {
            return "Gives fight status!";
        }
    }
}
