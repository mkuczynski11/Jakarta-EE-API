package com.kask.game.repository;

import com.kask.datastore.DataStore;
import com.kask.game.entity.Game;
import com.kask.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class GameRepository implements Repository<Game, String> {
    private EntityManager entityManager;
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public Optional<Game> get(String gameName) {
        return Optional.ofNullable(entityManager.find(Game.class, gameName));
    }

    @Override
    public List<Game> getAll() {
        return entityManager.createQuery("select g from Game g", Game.class).getResultList();
    }

    @Override
    public void create(Game entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(Game entity) {
        entityManager.remove(entityManager.find(Game.class, entity.getName()));
    }

    @Override
    public void update(Game entity) {
        entityManager.merge(entity);
    }

    @Override
    public void detach(Game entity){
        entityManager.detach(entity);
    }

}
