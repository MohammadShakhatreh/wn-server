package com.worldnavigator.web.controllers;

import com.worldnavigator.game.Game;
import com.worldnavigator.game.Player;
import com.worldnavigator.web.dto.ExecutionRequest;
import com.worldnavigator.web.dto.ExecutionResponse;
import com.worldnavigator.web.dto.NewGameRequest;
import com.worldnavigator.web.entities.User;
import com.worldnavigator.web.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Game create(User user, @Valid @RequestBody NewGameRequest request) {
        return gameService.create(user, request);
    }

    @GetMapping("{uuid}")
    public Game retrieve(@PathVariable String uuid) {
        return gameService.getGame(uuid);
    }

    @GetMapping
    public Collection<Game> list() {
        return gameService.getGames().values();
    }

    @PostMapping("{uuid}/join")
    @ResponseStatus(HttpStatus.CREATED)
    public Player join(@PathVariable String uuid, User user) {
        return gameService.addPlayer(uuid, user);
    }

    @PostMapping("{uuid}/execute")
    public ExecutionResponse execute(
            User user,
            @PathVariable String uuid,
            @Valid @RequestBody ExecutionRequest request
    ) {
        return new ExecutionResponse(gameService.execute(uuid, user, request));
    }

    @GetMapping("{uuid}/status")
    public Player status(@PathVariable String uuid, User user) {
        Game game = gameService.getGame(uuid);
        Map<String, Player> players = game.getPlayers();

        return players.get(user.getUsername());
    }

    @PostMapping("{uuid}/quit")
    public void quit(@PathVariable String uuid, User user) {
        Game game = gameService.getGame(uuid);
        Map<String, Player> players = game.getPlayers();

        Player player = players.get(user.getUsername());
        players.remove(user.getUsername());

        game.distributePlayerGold(player);
        game.dropPlayerItems(player);
    }
}
