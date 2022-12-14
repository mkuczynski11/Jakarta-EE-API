package com.kask.achievement.model;

import com.kask.achievement.entity.Achievement;
import com.kask.game.entity.Game;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AchievementModel {
    private int id;
    private String name;
    private double ownedPercentage;
    private int reward;
    private String game;

    public static Function<Achievement, AchievementModel> entityToModelMapper() {
        return achievement -> AchievementModel.builder()
                .id(achievement.getId())
                .name(achievement.getName())
                .ownedPercentage(achievement.getOwnedPercentage())
                .reward(achievement.getReward())
                .game(achievement.getGame().getName())
                .build();
    }
}
