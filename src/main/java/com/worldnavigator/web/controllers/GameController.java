package com.worldnavigator.web.controllers;

import com.worldnavigator.game.Game;
import com.worldnavigator.game.Player;
import com.worldnavigator.game.controls.Command;
import com.worldnavigator.web.dto.*;
import com.worldnavigator.web.entities.User;
import com.worldnavigator.web.mappers.GameMapper;
import com.worldnavigator.web.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("games")
public class GameController {

    private final GameMapper gameMapper;

    private final GameService gameService;

    @Autowired
    public GameController(GameMapper gameMapper, GameService gameService) {
        this.gameMapper = gameMapper;
        this.gameService = gameService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameInfo create(User user, @Valid @RequestBody NewGameRequest request) {
        Game game = gameService.create(user, request);
        return gameMapper.toGameInfo(game);
    }

    @GetMapping("{uuid}")
    public GameInfo retrieve(@PathVariable String uuid) {
        Game game = gameService.getGame(uuid);
        return gameMapper.toGameInfo(game);
    }

    @GetMapping
    public List<GameInfo> list() {
        return gameService.getGames().values()
                .stream()
                .map(gameMapper::toGameInfo)
                .collect(toList());
    }

    @PostMapping("{uuid}/join")
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerInfo join(@PathVariable String uuid, User user) {
        Player player = gameService.createPlayer(uuid, user);
        return gameMapper.toPlayerInfo(player);
    }

    @PostMapping("{uuid}/execute")
    public ExecutionResponse execute(
            User user, @PathVariable String uuid, @Valid @RequestBody ExecutionRequest request
    ) {
        return new ExecutionResponse(gameService.execute(uuid, user, request));
    }

    @GetMapping("{uuid}/status")
    public PlayerInfo status(@PathVariable String uuid, User user) {
        Player player = gameService.getPlayer(uuid, user);
        return gameMapper.toPlayerInfo(player);
    }

    @GetMapping("{uuid}/commands")
    public List<Command> commands(@PathVariable String uuid, User user) {
        return gameService.getAvailableCommands(uuid, user);
    }

    @GetMapping("{uuid}/players")
    public List<PlayerInfo> players(@PathVariable String uuid) {
        Game game = gameService.getGame(uuid);
        return game.getPlayers().values()
                .stream()
                .map(gameMapper::toPlayerInfo)
                .collect(toList());
    }
}
