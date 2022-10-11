package com.kask.game.model.converter;

import com.kask.game.entity.Game;
import com.kask.game.model.GameModel;
import com.kask.game.service.GameService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import java.util.Optional;

@FacesConverter(forClass = GameModel.class, managed = true)
public class GameModelConverter  implements Converter<GameModel> {

    private GameService gameService;

    @Inject
    public GameModelConverter(GameService gameService){
        this.gameService = gameService;
    }

    @Override
    public GameModel getAsObject(FacesContext facesContext, UIComponent uiComponent, String s){
        if (s == null || s.isBlank()){
            return null;
        }
        Optional<Game> game = gameService.getGame(s);
        return game.isEmpty() ? null : GameModel.entityToModelMapper().apply(game.get());
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, GameModel gameModel){
        return gameModel == null ? "" : gameModel.getName();
    }
}
