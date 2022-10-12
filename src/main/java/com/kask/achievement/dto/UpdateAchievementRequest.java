package com.kask.achievement.dto;

import com.kask.achievement.entity.Achievement;
import lombok.*;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UpdateAchievementRequest {
    private String name;
    private double ownedPercentage;
    private int reward;

    public static BiFunction<Achievement, UpdateAchievementRequest, Achievement> dtoToEntityMapper(){
        return (achievement, request) -> {
            achievement.setName(request.getName());
            achievement.setOwnedPercentage(request.getOwnedPercentage());
            achievement.setReward(request.getReward());
            return achievement;
        };
    }
}
