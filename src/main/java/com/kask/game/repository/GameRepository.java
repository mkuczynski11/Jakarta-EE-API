package com.kask.game.repository;

import com.kask.game.entity.Game;
import com.kask.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Dependent
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
