package com.kask.game.model;

import lombok.*;

import java.io.Serializable;
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
public class GamesModel implements Serializable {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class Game{
        private String name;
    }

    @Singular
    private List<Game> games;

    public static Function<Collection<com.kask.game.entity.Game>, GamesModel> entityToModelMapper() {
        return games -> {
            GamesModel.GamesModelBuilder builder = GamesModel.builder();
            games.stream()
                    .map(game -> Game.builder()
                            .name(game.getName())
                            .build())
                    .forEach(builder::game);
            return builder.build();
        };
    }
}
