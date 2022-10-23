package com.kask.achievement.entity;

import com.kask.game.entity.Game;
import com.kask.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "achievements")
public class Achievement implements Serializable {
    @Id
    private int id;
    private String name;
    private double ownedPercentage;
    private int reward;

    @ManyToOne
    @JoinColumn(name = "game")
    private Game game;


    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
}
