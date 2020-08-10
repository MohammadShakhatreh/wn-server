package com.worldnavigator.web.services;

import com.worldnavigator.game.Game;
import com.worldnavigator.game.Player;
import com.worldnavigator.game.controls.Command;
import com.worldnavigator.game.controls.Invoker;
import com.worldnavigator.game.controls.PlayerContext;
import com.worldnavigator.web.dto.ExecutionRequest;
import com.worldnavigator.web.dto.NewGameRequest;
import com.worldnavigator.web.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GameService {

    private final Invoker invoker;

    private final Map<UUID, Game> games;

    @Autowired
    public GameService(Invoker invoker) {
        this.invoker = invoker;
        this.games = new HashMap<>();
    }

    public Game create(User user, NewGameRequest request) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime startedAt = LocalDateTime.now().plusMinutes(request.getStartsAfter());

        Game game = new Game(
                uuid,
                user.getUsername(),
                request.getName(),
                request.getMaze(),
                request.getGold(),
                request.getTimeout(),
                startedAt
        );

        Map<String, Player> players = game.getPlayers();
        players.put(user.getUsername(), Player.of(game, user.getUsername()));

        games.putIfAbsent(uuid, game);
        return game;
    }

    public Player addPlayer(String uuid, User user) {
        Game game = games.get(UUID.fromString(uuid));

        if(game == null)
            throw new NoSuchElementException("There is no game with this uuid.");

        if(game.isStarted())
            throw new IllegalStateException("The game started you can't join.");

        if(game.isFinished())
            throw new IllegalStateException("The game has finished you can't join");

        Map<String, Player> players = game.getPlayers();

        if(players.containsKey(user.getUsername()))
            throw new IllegalArgumentException("There is already a player with this name.");

        Player player = Player.of(game, user.getUsername());
        players.put(user.getUsername(), player);

        return player;
    }

    public String execute(String uuid, User user, ExecutionRequest request) {
        return invoker.execute(getPlayerContext(uuid, user), request.getLine());
    }

    public List<Command> getAvailableCommands(String uuid, User user) {
        return invoker.getAvailableCommands(getPlayerContext(uuid, user));
    }

    private PlayerContext getPlayerContext(String uuid, User user) {

        Game game = getGame(uuid);

        if(game.isFinished())
            throw new IllegalStateException(
                    "The game is finished and " +
                    (game.getWinner() == null ? "there is no winner" : "the winner is " + game.getWinner())
            );

        Player player = game.getPlayer(user.getUsername());
        return new PlayerContext(game, player);
    }

    public Game getGame(String uuid) {
        Game game = games.get(UUID.fromString(uuid));

        if(game == null)
            throw new NoSuchElementException("There is no game with that uuid.");

        return game;
    }

    public Map<UUID, Game> getGames() {
        return games;
    }
}
