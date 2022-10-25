package com.kask.user.dto;

import com.kask.user.entity.User;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateUserRequest {
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;

    public static Function<CreateUserRequest, User> dtoToEntityMapper() {
        return createUserRequest -> User.builder()
                    .username(createUserRequest.getUsername())
                    .name(createUserRequest.getName())
                    .surname(createUserRequest.getSurname())
                    .email(createUserRequest.getEmail())
                    .password(createUserRequest.getPassword())
                    .build();
    }
}
