package com.kask.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CreateUserRequest {

    private String id;
    private String name;
    private String surname;
    private String email;
    private String role;
    private String password;
}
