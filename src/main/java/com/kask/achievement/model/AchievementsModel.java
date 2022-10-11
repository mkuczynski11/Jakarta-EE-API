package com.kask.achievement.model;

import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AchievementsModel implements Serializable {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class Achievement {
        private String name;
    }

    @Singular
    private List<Achievement> achievements;

    public static Function<Collection<com.kask.achievement.entity.Achievement>, AchievementsModel> entityToModelMapper() {
        return achievements -> {
            AchievementsModel.AchievementsModelBuilder builder = AchievementsModel.builder();
            achievements.stream()
                    .map(achievement -> Achievement.builder()
                            .name(achievement.getName())
                            .build())
                    .forEach(builder::achievement);
            return builder.build();
        };
    }
}
