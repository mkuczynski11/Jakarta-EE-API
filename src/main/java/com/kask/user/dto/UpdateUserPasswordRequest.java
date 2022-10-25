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
public class UpdateUserPasswordRequest {
    private String password;

    public static BiFunction<User, UpdateUserPasswordRequest, User> dtoToEntityMapper() {
        return (user, updateUserPasswordRequest) -> {
          user.setPassword(updateUserPasswordRequest.getPassword());
          return user;
        };
    }
}
