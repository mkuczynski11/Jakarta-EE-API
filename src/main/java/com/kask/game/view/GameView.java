package com.kask.game.view;

import com.kask.achievement.model.AchievementsModel;
import com.kask.achievement.service.AchievementService;
import com.kask.game.entity.Game;
import com.kask.game.model.GameModel;
import com.kask.game.service.GameService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

@SessionScoped
@Named
@Slf4j
@NoArgsConstructor
public class GameView implements Serializable {

    private GameService gameService;

    private AchievementService achievementService;

    @Getter
    @Setter
    private String gameName;

    @Getter
    @Setter
    private GameModel gameModel;

    @Getter
    @Setter
    private AchievementsModel achievementsModel;

    @EJB
    public void setGameService(GameService gameService){
        this.gameService = gameService;
    }
    @EJB
    public void setAchievementService(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    public void init() throws IOException {
        Optional<Game> game = gameService.getGame(gameName);
        if (game.isPresent()) {
            this.gameModel = GameModel.entityToModelMapper().apply(game.get());
            this.achievementsModel = AchievementsModel.entityToModelMapper().apply(game.get().getAchievements());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Game not found");
        }
    }

    public String deleteAchievement(AchievementsModel.Achievement achievement) {
        log.info("Deleting achievement with id: " + achievement.getId());
        achievementService.deleteAchievement(achievementService.getAchievement(achievement.getId()).orElseThrow());
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}
