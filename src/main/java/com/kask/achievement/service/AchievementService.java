package com.kask.achievement.service;

import com.kask.achievement.entity.Achievement;
import com.kask.achievement.repository.AchievementRepository;
import com.kask.game.repository.GameRepository;
import com.kask.user.repository.UserRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class AchievementService {

    private AchievementRepository achievementRepository;
    private GameRepository gameRepository;
    private UserRepository userRepository;

    @Inject
    public AchievementService(AchievementRepository achievementRepository, GameRepository gameRepository, UserRepository userRepository) {
        this.achievementRepository = achievementRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createAchievement(Achievement achievement) {
        achievementRepository.create(achievement);
        if (achievement.getGame() != null) {
            gameRepository.get(achievement.getGame().getName()).ifPresent(game -> game.getAchievements().add(achievement));
        }
        if (achievement.getUser() != null) {
            userRepository.get(achievement.getUser().getId()).ifPresent(user -> user.getAchievementList().add(achievement));
        }
    }

    public Optional<Achievement> getAchievement(int achievementId) {return achievementRepository.get(achievementId);}

    public List<Achievement> getAllAchievements() {return achievementRepository.getAll();}

    @Transactional
    public void updateAchievement(Achievement achievement) {
        Achievement org = achievementRepository.get(achievement.getId()).orElseThrow();
        achievementRepository.detach(achievement);
        if (!org.getGame().getName().equals(achievement.getGame().getName())) {
            org.getGame().getAchievements().removeIf(achievementToRemove -> achievementToRemove.getId() == achievement.getId());
            gameRepository.get(achievement.getGame().getName()).ifPresent(game -> game.getAchievements().add(achievement));
        }
        achievementRepository.update(achievement);
    }

    @Transactional
    public void deleteAchievement(Achievement achievement) {
        Achievement a = achievementRepository.get(achievement.getId()).orElseThrow();
        if (a.getGame() != null) {
            a.getGame().getAchievements().remove(a);
        }
        if (a.getUser() != null){
            a.getUser().getAchievementList().remove(a);
        }
        achievementRepository.delete(achievement);
    }
}
