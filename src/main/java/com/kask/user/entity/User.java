package com.kask.user.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    private String username;

    @ToString.Exclude
    @Column(nullable = false)
    private String password;

    private String name;

    private String surname;

    private String email;


    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] avatar;
}