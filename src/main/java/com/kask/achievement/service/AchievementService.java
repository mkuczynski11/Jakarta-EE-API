package com.kask.achievement.service;

import com.kask.achievement.entity.Achievement;
import com.kask.achievement.repository.AchievementRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class AchievementService {

    private AchievementRepository achievementRepository;

    @Inject
    public AchievementService(AchievementRepository achievementRepository) {this.achievementRepository = achievementRepository;}

    public void createAchievement(Achievement achievement) {achievementRepository.create(achievement);}

    public Optional<Achievement> getAchievement(Achievement achievement) {return achievementRepository.get(achievement.getName());}

    public List<Achievement> getAllAchievements() {return achievementRepository.getAll();}

    public void updateAchievement(Achievement achievement) {achievementRepository.update(achievement);}

    public void deleteAchievement(Achievement achievement) {achievementRepository.delete(achievement);}

}
