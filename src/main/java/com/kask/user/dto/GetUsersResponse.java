package com.kask.user.dto;

import com.kask.user.entity.User;
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
public class GetUsersResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class ListUser {
        private String name;
        private String surname;
    }

    @Singular
    private List<ListUser> users;

    public static Function<Collection<User>, GetUsersResponse> entityToDtoMapper() {
        return retUsers -> {
            GetUsersResponse.GetUsersResponseBuilder response = GetUsersResponse.builder();
            retUsers.stream()
                    .map(user -> ListUser.builder()
                            .name(user.getName())
                            .surname(user.getSurname())
                            .build())
                    .forEach(response::user);
            return response.build();
        };
    }
}
