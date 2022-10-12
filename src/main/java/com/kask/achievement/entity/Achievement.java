package com.kask.achievement.entity;

import com.kask.game.entity.Game;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class Achievement implements Serializable {
    private int id;
    private String name;
    private double ownedPercentage;
    private int reward;
    private Game game;

}
