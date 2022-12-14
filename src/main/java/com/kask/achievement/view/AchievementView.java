package com.kask.achievement.view;

import com.kask.achievement.entity.Achievement;
import com.kask.achievement.model.AchievementModel;
import com.kask.achievement.service.AchievementService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.EJB;
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
@NoArgsConstructor
public class AchievementView {

    private AchievementService achievementService;

    @Getter
    @Setter
    private Integer achievementId;

    @Getter
    @Setter
    private AchievementModel achievementModel;

    @EJB
    public void setAchievementService(AchievementService achievementService){
        this.achievementService = achievementService;
    }

    public void init() throws IOException {
        Optional<Achievement> achievement = achievementService.getAchievement(achievementId);
        if (achievement.isPresent()) {
            this.achievementModel = AchievementModel.entityToModelMapper().apply(achievement.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Achievement not found");
        }
    }

}
