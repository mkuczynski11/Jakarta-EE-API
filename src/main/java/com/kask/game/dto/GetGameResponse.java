package com.kask.game.dto;

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
public class GetGameResponse {

    private String name;
    private String developer;
    private int price;

    public static Function<Game, GetGameResponse> entityToDtoMapper(){
        return game -> GetGameResponse.builder()
                .name(game.getName())
                .developer(game.getDeveloper())
                .price(game.getPrice())
                .build();
    }
}
