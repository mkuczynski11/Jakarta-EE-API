package com.kask.achievement.model;

import com.kask.achievement.entity.Achievement;
import com.kask.game.entity.Game;
import com.kask.game.model.GameModel;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AchievementCreateModel {
    private int id;
    private String name;
    private double ownedPercentage;
    private int reward;
    private GameModel gameModel;

    public static Function<AchievementCreateModel, Achievement> modelToEntityMapper(Function<String, Game> gameFunction) {
        return model -> Achievement.builder()
                .id(model.getId())
                .name(model.getName())
                .ownedPercentage(model.getOwnedPercentage())
                .reward(model.getReward())
                .game(gameFunction.apply(model.getGameModel().getName()))
                .build();
    }
}
