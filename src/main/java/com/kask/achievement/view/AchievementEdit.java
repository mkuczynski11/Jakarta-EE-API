package com.kask.achievement.view;

import com.kask.achievement.entity.Achievement;
import com.kask.achievement.model.AchievementCreateModel;
import com.kask.achievement.service.AchievementService;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SessionScoped
@Named
@Slf4j
@NoArgsConstructor
public class AchievementEdit implements Serializable {

    private AchievementService achievementService;

    private GameService gameService;

    @Getter
    @Setter
    private AchievementCreateModel achievementCreateModel;

    @Getter
    @Setter
    private List<GameModel> gameModelList;

    @Getter
    @Setter
    private Integer achievementId;

    @EJB
    public void setAchievementService(AchievementService achievementService){
        this.achievementService = achievementService;
    }
    @EJB
    public void setGameService(GameService gameService){
        this.gameService = gameService;
    }

    public void init() throws IOException {
        Optional<Achievement> achievement = achievementService.getAchievement(achievementId);
        if (achievement.isPresent()) {
            gameModelList = gameService.getAllGames().stream()
                    .map(GameModel.entityToModelMapper())
                    .collect(Collectors.toList());

            achievementCreateModel = AchievementCreateModel.builder()
                    .id(achievement.get().getId())
                    .name(achievement.get().getName())
                    .ownedPercentage(achievement.get().getOwnedPercentage())
                    .reward(achievement.get().getReward())
                    .gameModel(GameModel.entityToModelMapper().apply(achievement.get().getGame()))
                    .build();
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Achievement with id: " + achievementId + "not found");
        }
    }

    public String editAchievement() {
        achievementService.updateAchievement(AchievementCreateModel.modelToEntityMapper(
                game -> gameService.getGame(game).orElseThrow()).apply(achievementCreateModel));
        return "/achievements/achievement_view.xhtml?faces-redirect=true&includeViewParams=true";
    }
}
