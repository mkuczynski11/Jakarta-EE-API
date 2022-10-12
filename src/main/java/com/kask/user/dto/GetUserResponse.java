package com.kask.user.dto;

import com.kask.user.entity.User;
import lombok.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GetUserResponse {

    private int id;
    private String name;
    private String surname;
    private String email;
    private User.Role role;
    private List<UserAchievements> userAchievements;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class UserAchievements {
        private String name;
    }

    public static Function<User, GetUserResponse> entityToDtoMapper() {
        return user -> GetUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .role(user.getRole())
                .userAchievements(user.getAchievementList().stream()
                        .map(a -> UserAchievements.builder()
                                .name(a.getName())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
