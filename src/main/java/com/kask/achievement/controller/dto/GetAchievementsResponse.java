package com.kask.achievement.controller.dto;

import com.kask.achievement.entity.Achievement;
import lombok.*;

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
public class GetAchievementsResponse {
    @Singular
    private List<String> achievements;

    public static Function<Collection<Achievement>, GetAchievementsResponse> entityToDtoMapper(){
        return achievements -> {
            GetAchievementsResponse.GetAchievementsResponseBuilder builder = GetAchievementsResponse.builder();
            achievements.stream()
                    .map(Achievement::getName)
                    .forEach(builder::achievement);
            return builder.build();
        };
    }
}
