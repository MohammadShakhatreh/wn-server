package com.worldnavigator.game.maze.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Objects;

public final class Seller extends RoomSide {

    private final Map<String, Integer> prices;

    @JsonCreator
    public Seller(
            @JsonProperty("prices") Map<String, Integer> prices
    ) {

        this.prices = Objects.requireNonNull(prices);
    }

    public Integer getItemPrice(String name) {
        return prices.get(name);
    }

    @Override
    public void accept(RoomSideVisitor visitor) {
        visitor.execute(this);
    }

    public Map<String, Integer> getPrices() {
        return prices;
    }

    @Override
    public String toString() {
        return "Seller";
    }
}
