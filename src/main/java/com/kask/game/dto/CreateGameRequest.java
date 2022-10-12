package com.kask.game.dto;

import com.kask.game.entity.Game;
import com.kask.game.service.GameService;
import lombok.*;

import javax.inject.Inject;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CreateGameRequest {

    private String name;
    private String developer;
    private int price;

    public static Function<CreateGameRequest, Game> dtoToEntityMapper() {
        return createGameRequest -> Game.builder()
                .name(createGameRequest.getName())
                .developer(createGameRequest.getDeveloper())
                .price(createGameRequest.getPrice())
                .build();
    }
}
