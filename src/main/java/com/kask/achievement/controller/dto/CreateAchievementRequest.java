package com.kask.achievement.controller.dto;

import com.kask.achievement.entity.Achievement;
import com.kask.game.entity.Game;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class CreateAchievementRequest {
    private int id;
    private String name;
    private double ownedPercentage;
    private int reward;

    public static Function<CreateAchievementRequest, Achievement> dtoToEntityMapper(Function<String, Game> gameFunction, String pathGameName) {
        return createAchievementRequest -> Achievement.builder()
                .id(createAchievementRequest.getId())
                .name(createAchievementRequest.getName())
                .ownedPercentage(createAchievementRequest.getOwnedPercentage())
                .reward(createAchievementRequest.getReward())
                .game(gameFunction.apply(pathGameName))
                .build();
    }
}
