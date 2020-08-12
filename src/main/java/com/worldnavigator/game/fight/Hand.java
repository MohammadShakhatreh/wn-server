package com.worldnavigator.game.fight;

public enum Hand {
    ROCK {
        @Override
        public boolean beats(Hand hand) {
            return hand == SCISSOR;
        }
    },

    PAPER {
        @Override
        public boolean beats(Hand hand) {
            return hand == ROCK;
        }
    },

    SCISSOR {
        @Override
        public boolean beats(Hand hand) {
            return hand == PAPER;
        }
    },

    NONE {
        @Override
        public boolean beats(Hand hand) {
            return false;
        }
    };

    public abstract boolean beats(Hand hand);
}