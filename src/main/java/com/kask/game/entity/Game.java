package com.kask.game.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class Game implements Serializable {
    private String name;
    private String developer;
    private int price;
}
