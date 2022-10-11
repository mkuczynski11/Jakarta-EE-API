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
@NoArgsConstructor
@Slf4j
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
    private String achievementName;

    @Inject
    public AchievementEdit(AchievementService achievementService, GameService gameService){
        this.achievementService = achievementService;
        this.gameService = gameService;
    }

    public void init() throws IOException {
        Optional<Achievement> achievement = achievementService.getAchievement(achievementName);
        if (achievement.isPresent()) {
            gameModelList = gameService.getAllGames().stream()
                    .map(GameModel.entityToModelMapper())
                    .collect(Collectors.toList());

            achievementCreateModel = AchievementCreateModel.builder()
                    .name(achievement.get().getName())
                    .ownedPercentage(achievement.get().getOwnedPercentage())
                    .reward(achievement.get().getReward())
                    .gameModel(GameModel.entityToModelMapper().apply(achievement.get().getGame()))
                    .build();
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Achievement with name: " + achievementName + "not found");
        }
    }

    public String editAchievement() {
        achievementService.updateAchievement(AchievementCreateModel.modelToEntityMapper(
                game -> gameService.getGame(game).orElseThrow()).apply(achievementCreateModel));
        return "/achievements/achievement_view.xhtml?faces-redirect=true&includeViewParams=true";
    }
}