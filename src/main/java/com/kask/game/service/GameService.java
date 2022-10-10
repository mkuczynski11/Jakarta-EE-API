package com.kask.game.service;

import com.kask.game.entity.Game;
import com.kask.game.repository.GameRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class GameService {

    private GameRepository gameRepository;

    @Inject
    public GameService(GameRepository gameRepository) {this.gameRepository = gameRepository;}

    public void createGame(Game game) {
        gameRepository.create(game);
    }

    public Optional<Game> getGame(String gameName) {
        return gameRepository.get(gameName);
    }

    public List<Game> getAllGames() {return gameRepository.getAll();}

    public void updateGame(Game game) {
        gameRepository.create(game);
    }

    public void deleteGame(Game game) {
        gameRepository.delete(game);
    }
}
