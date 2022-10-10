package com.kask.achievement.entity;

import com.kask.game.entity.Game;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Achievement implements Serializable {
    private String name;
    private float ownedPercentage;
    private int reward;
    private Game game;

}
