package com.kask.game.model;

import com.kask.game.entity.Game;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GameModel {

    private String name;
    private String developer;
    private int price;

    public static Function<Game, GameModel> entityToModelMapper() {
        return game -> GameModel.builder()
                .name(game.getName())
                .developer(game.getDeveloper())
                .price(game.getPrice())
                .build();
    }
}
