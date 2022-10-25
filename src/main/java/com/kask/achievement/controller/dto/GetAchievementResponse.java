package com.kask.achievement.controller.dto;

import com.kask.achievement.entity.Achievement;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class GetAchievementResponse {
    private int id;
    private String name;
    private double ownedPercentage;
    private int reward;

    public static Function<Achievement, GetAchievementResponse> entityToDtoMapper() {
        return achievement -> GetAchievementResponse.builder()
                .id(achievement.getId())
                .name(achievement.getName())
                .ownedPercentage(achievement.getOwnedPercentage())
                .reward(achievement.getReward())
                .build();
    }
}
