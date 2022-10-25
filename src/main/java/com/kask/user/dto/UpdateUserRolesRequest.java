package com.kask.user.dto;

import com.kask.user.entity.User;
import lombok.*;

import java.util.List;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateUserRolesRequest {
    private List<String> roles;

    public static BiFunction<User, UpdateUserRolesRequest, User> dtoToEntityMapper() {
        return (user, updateUserRoleRequest) -> {
          user.setRoles(updateUserRoleRequest.getRoles());
          return user;
        };
    }
}
