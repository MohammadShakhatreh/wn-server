package com.worldnavigator.game.maze;

public enum Direction {
    NORTH {
        @Override
        public Direction right() {
            return EAST;
        }

        @Override
        public Direction left() {
            return WEST;
        }

        @Override
        public Direction reverse() {
            return SOUTH;
        }
    },
    EAST {
        @Override
        public Direction right() {
            return SOUTH;
        }

        @Override
        public Direction left() {
            return NORTH;
        }

        @Override
        public Direction reverse() {
            return WEST;
        }
    },
    SOUTH {
        @Override
        public Direction right() {
            return WEST;
        }

        @Override
        public Direction left() {
            return EAST;
        }

        @Override
        public Direction reverse() {
            return NORTH;
        }
    },
    WEST {
        @Override
        public Direction right() {
            return NORTH;
        }

        @Override
        public Direction left() {
            return SOUTH;
        }

        @Override
        public Direction reverse() {
            return EAST;
        }
    };

    /**
     * Returns the direction in the right.
     *
     * For example the right of the NORTH is EAST
     */
    public abstract Direction right();

    /**
     * Returns the direction in the left.
     *
     * For example the left of NORTH is WEST.
     *
     */
    public abstract Direction left();


    /**
     * Returns the opposite direction.
     *
     * For example the reverse of NORTH is SOUTH.
     *
     */
    public abstract Direction reverse();
}
