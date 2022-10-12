package com.kask.game.dto;

import com.kask.game.entity.Game;
import lombok.*;

import java.util.function.BiFunction;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UpdateGameRequest {
    private String developer;
    private int price;

    public static BiFunction<Game, UpdateGameRequest, Game> dtoToEntityMapper() {
        return (game, updateGameRequest) -> {
          game.setDeveloper(updateGameRequest.getDeveloper());
          game.setPrice(updateGameRequest.getPrice());
          return game;
        };
    }
}
