package com.kask.game.view;

import com.kask.game.model.GamesModel;
import com.kask.game.service.GameService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named
@Slf4j
@NoArgsConstructor
public class GameList implements Serializable {

    private GameService gameService;

    private GamesModel games;

    @EJB
    public void setGameService(GameService gameService){
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
