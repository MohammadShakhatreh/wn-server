package com.worldnavigator.web.services;

import com.worldnavigator.game.Game;
import com.worldnavigator.game.Player;
import com.worldnavigator.web.dto.ExecutionRequest;
import com.worldnavigator.web.dto.NewGameRequest;
import com.worldnavigator.web.entities.User;
import com.worldnavigator.web.repositories.MazeTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GameService {

    private final Map<UUID, Game> games;
    private final MazeTemplateRepository mazeTemplateRepository;

    @Autowired
    public GameService(MazeTemplateRepository mazeTemplateRepository) {
        this.games = new HashMap<>();
        this.mazeTemplateRepository = mazeTemplateRepository;
    }

    public Player addPlayer(String uuid, User user) {
        Game game = games.get(UUID.fromString(uuid));

        if(game == null)
            throw new NoSuchElementException("There is no game with this uuid.");

        if(game.isStarted())
            throw new IllegalStateException("The game started you can't join.");

        Map<String, Player> players = game.getPlayers();
        if(players.containsKey(user.getUsername()))
            throw new IllegalArgumentException("There is already a player with this name.");

        Player player = Player.of(game, user.getUsername());
        players.put(user.getUsername(), player);

        return player;
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

        var players = game.getPlayers();
        players.put(user.getUsername(), Player.of(game, user.getUsername()));

        return games.putIfAbsent(uuid, game);
    }

    public Game getGame(String uuid) {
        return games.get(UUID.fromString(uuid));
    }

    public Map<UUID, Game> getGames() {
        return games;
    }

    public String execute(String uuid, User user, ExecutionRequest request) {
        Game game = games.get(UUID.fromString(uuid));

        if(game == null)
            throw new NoSuchElementException("There is no game with this uuid.");

        if(game.isFinished())
            return "The game is finished and " +
                    (game.getWinner() == null ? "there is no winner" : "the winner is " + game.getWinner());

        Player player = game.getPlayers().get(user.getUsername());

        if(player == null)
            throw new NoSuchElementException("You are not in this game.");



        return player.execute(request.getLine());
    }
}
