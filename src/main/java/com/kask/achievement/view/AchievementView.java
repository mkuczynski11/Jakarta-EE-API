package com.kask.achievement.view;

import com.kask.achievement.entity.Achievement;
import com.kask.achievement.model.AchievementModel;
import com.kask.achievement.service.AchievementService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequestScoped
@Named
@Slf4j
public class AchievementView {

    private AchievementService achievementService;

    @Getter
    @Setter
    private String achievementName;

    @Getter
    @Setter
    private AchievementModel achievementModel;

    @Inject
    public AchievementView(AchievementService achievementService) {this.achievementService = achievementService;}

    public void init() throws IOException {
        Optional<Achievement> achievement = achievementService.getAchievement(achievementName);
        if (achievement.isPresent()) {
            this.achievementModel = AchievementModel.entityToModelMapper().apply(achievement.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Achievement not found");
        }
    }

}
