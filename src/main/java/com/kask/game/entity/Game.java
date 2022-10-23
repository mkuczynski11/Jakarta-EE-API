package com.kask.game.entity;

import com.kask.achievement.entity.Achievement;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "games")
public class Game implements Serializable {
    @Id
    private String name;
    private String developer;
    private int price;

    @OneToMany(mappedBy = "game", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private List<Achievement> achievements;
}
