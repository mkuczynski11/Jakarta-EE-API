package com.kask.game.view;

import com.kask.game.model.GamesModel;
import com.kask.game.service.GameService;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named
@Slf4j
public class GameList implements Serializable {

    private final GameService gameService;

    private GamesModel games;

    @Inject
    public GameList(GameService gameService) {
        this.gameService = gameService;
    }

    public GamesModel getGames() {
        if (games == null) {
            games = GamesModel.entityToModelMapper().apply(gameService.getAllGames());
        }
        return games;
    }

    public String deleteGame(GamesModel.Game game) {
        gameService.deleteGame(game.getName());
        return "games_list?faces-redirect=true";
    }
}
