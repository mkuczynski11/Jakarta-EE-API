package com.kask.user.dto;

import com.kask.user.entity.User;
import lombok.*;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateUserRequest {
    private String name;
    private String surname;
    private String email;

    public static BiFunction<User, UpdateUserRequest, User> dtoToEntityMapper() {
        return (user, updateUserRequest) -> {
          user.setName(updateUserRequest.getName());
          user.setSurname(updateUserRequest.getSurname());
          user.setEmail(updateUserRequest.getEmail());
          return user;
        };
    }
}
