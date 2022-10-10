package com.kask.game.repository;

import com.kask.datastore.DataStore;
import com.kask.game.entity.Game;
import com.kask.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class GameRepository implements Repository<Game, String> {
    private DataStore dataStore;

    @Inject
    public GameRepository(DataStore dataStore) { this.dataStore = dataStore;}

    @Override
    public Optional<Game> get(String gameName) {
        return dataStore.getGame(gameName);
    }

    @Override
    public List<Game> getAll() {
        return dataStore.getAllGames();
    }

    @Override
    public void create(Game entity) {
        dataStore.createGame(entity);
    }

    @Override
    public void delete(Game entity) {
        dataStore.deleteGame(entity.getName());
    }

    @Override
    public void update(Game entity) {
        dataStore.updateGame(entity);
    }

}
