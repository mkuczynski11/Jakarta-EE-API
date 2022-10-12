package com.kask.game.dto;

import com.kask.game.entity.Game;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GetGamesResponse {

    @Singular
    private List<String> games;

    public static Function<Collection<Game>, GetGamesResponse> entityToDtoMapper(){
        return games -> {
          GetGamesResponse.GetGamesResponseBuilder builder = GetGamesResponse.builder();
          games.stream()
                  .map(Game::getName)
                  .forEach(builder::game);
          return builder.build();
        };
    }
}
