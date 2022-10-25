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
import java.util.UUID;
import java.util.stream.Collectors;

@SessionScoped
@Named
@NoArgsConstructor
@Slf4j
public class AchievementCreate implements Serializable {

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
    private String gameName;

    @EJB
    public void setAchievementService(AchievementService achievementService) {
        this.achievementService = achievementService;
    }
    @EJB
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    public void init() throws IOException {
        gameModelList = gameService.getAllGames().stream()
                .map(GameModel.entityToModelMapper())
                .collect(Collectors.toList());
        achievementCreateModel = AchievementCreateModel.builder()
                .id(achievementService.getAllAchievements().size()+1)
                .name(UUID.randomUUID().toString().split("-")[0])
                .ownedPercentage(0.6)
                .reward(100)
                .gameModel(gameModelList.stream()
                        .filter(x -> gameName.equals(x.getName()))
                        .findFirst().orElse(null))
                .build();
    }

    public String createAchievement() throws IOException {
        Optional<Achievement> achievement = achievementService.getAchievement(achievementCreateModel.getId());
        if (achievement.isPresent()) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Achievement with id: " + achievement.get().getId() + " already exists");
        } else {
            achievementService.createAchievement(AchievementCreateModel.modelToEntityMapper(
                    game -> gameService.getGame(game).orElseThrow()).apply(achievementCreateModel));
        }
        return "/games/game_view.xhtml?faces-redirect=true&includeViewParams=true";
    }
}
